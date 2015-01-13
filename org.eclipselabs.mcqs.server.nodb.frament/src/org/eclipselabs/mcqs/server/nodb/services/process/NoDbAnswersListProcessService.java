/*******************************************************************************
 * Copyright 2012 Jeremie Bresson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.eclipselabs.mcqs.server.nodb.services.process;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.scout.commons.BooleanUtility;
import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.server.nodb.DataStore;
import org.eclipselabs.mcqs.shared.services.process.AnswerFormData;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData.Answers;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData.Answers.AnswersRowData;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData.Statistics;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData.Statistics.StatisticsRowData;
import org.eclipselabs.mcqs.shared.services.process.IAnswersListProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData.Choices;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData.Choices.ChoicesRowData;

@Priority(100)
public class NoDbAnswersListProcessService extends AbstractService implements IAnswersListProcessService {

  @Override
  public AnswersListFormData load(AnswersListFormData formData) throws ProcessingException {
    DataStore dataStore = DataStore.getInstance();
    Integer questionNr = formData.getQuestionNr().getValue();

    if (questionNr == null) {
      throw new ProcessingException("QuestionNr can not be null");
    }
    QuestionFormData question = dataStore.updateQuestionText(formData);

    //load answers list:
    Answers answersTable = formData.getAnswers();
    answersTable.clearRows();
    Collection<AnswerFormData> answers = dataStore.getAnswersOfQuestion(questionNr);
    for (AnswerFormData answer : answers) {
      AnswersRowData row = answersTable.addRow();
      row.setAnswerNr(answer.getAnswerNr());
      row.setName(answer.getYourName().getValue());
    }

    //load statistics list:
    loadStatisticsInternal(formData, question, answers);
    return formData;
  }

  @Override
  public AnswersListFormData loadStatistics(AnswersListFormData formData) throws ProcessingException {
    DataStore dataStore = DataStore.getInstance();
    Integer questionNr = formData.getQuestionNr().getValue();

    if (questionNr == null) {
      throw new ProcessingException("QuestionNr can not be null");
    }

    QuestionFormData question = dataStore.getQuestion(questionNr);

    loadStatisticsInternal(formData, question, dataStore.getAnswersOfQuestion(questionNr));
    return formData;
  }

  private void loadStatisticsInternal(AnswersListFormData formData, QuestionFormData question, Collection<AnswerFormData> answers) {
    Choices choices = question.getChoices();
    Map<Integer, P_Choice> choicesMap = new HashMap<Integer, P_Choice>();
    for (ChoicesRowData row : choices.getRows()) {
      if (row.getChoiceNr() != null) {
        P_Choice choice = new P_Choice(row.getChoiceNr(), row.getChoiceText());
        choicesMap.put(choice.getChoiceId(), choice);
      }
    }
    for (AnswerFormData answer : answers) {
      Set<Long> answerChoices;
      if (BooleanUtility.nvl(answer.getMultipleChoices())) {
        answerChoices = answer.getChoices().getValue();
      }
      else {
        answerChoices = Collections.singleton(answer.getChoice().getValue());
      }
      for (Long answerChoice : answerChoices) {
        Integer answerChoiceKey = Integer.valueOf(answerChoice.intValue());
        if (choicesMap.containsKey(answerChoiceKey)) {
          choicesMap.get(answerChoiceKey).incrementResult();
        }
      }
    }

    int answerSize = answers.size();
    if (answerSize > 0) {
      for (P_Choice choice : choicesMap.values()) {
        choice.divideResult(answerSize);
      }
    }

    Statistics statistics = formData.getStatistics();
    statistics.clearRows();
    for (P_Choice choice : choicesMap.values()) {
      StatisticsRowData row = statistics.addRow();
      row.setChoice(choice.getChoiceText());
      if (BooleanUtility.nvl(question.getMultipleChoices().getValue())) {
        row.setResultYes(choice.getResult());
        row.setResultNo(1 - choice.getResult());
      }
      else {
        row.setResult(choice.getResult());
      }
    }
  }

  private class P_Choice {
    private Integer m_choiceId;
    private String m_choiceText;
    private double m_result;

    public P_Choice(Integer choiceId, String choiceText) {
      super();
      m_choiceId = choiceId;
      m_choiceText = choiceText;
      m_result = 0.0;
    }

    public Integer getChoiceId() {
      return m_choiceId;
    }

    public String getChoiceText() {
      return m_choiceText;
    }

    public Double getResult() {
      return m_result;
    }

    public void incrementResult() {
      m_result = m_result + 1.0;
    }

    public void divideResult(int nb) {
      m_result = m_result / nb;
    }
  }
}
