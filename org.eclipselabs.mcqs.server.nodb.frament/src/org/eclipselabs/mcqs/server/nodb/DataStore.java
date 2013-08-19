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
package org.eclipselabs.mcqs.server.nodb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.scout.commons.holders.ITableHolder;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;
import org.eclipselabs.mcqs.shared.services.process.AnswerFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData.Choices;

public final class DataStore {
  private static final DataStore INSTANCE = new DataStore();

  private final Map<Integer, QuestionFormData> m_questions;
  private final Map<Long, AnswerFormData> m_answers;

  private int m_questionNrSequence;
  private int m_answerNrSequence;
  private int m_choiceNrSequence;

  private DataStore() {
    m_questions = new ConcurrentHashMap<Integer, QuestionFormData>();
    storeQuestion(createQuestion1());
    storeQuestion(createQuestion2());
    m_answers = new ConcurrentHashMap<Long, AnswerFormData>();
  }

  private QuestionFormData createQuestion1() {
    QuestionFormData formData = new QuestionFormData();
    formData.setQuestionNr(createQuestionNr());
    formData.getQuestionText().setValue("Where did you travel last year?");
    formData.getMultipleChoices().setValue(true);
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Asia"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Africa"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "North America"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "South America"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Antarctica"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Europe"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Australia"});
    return formData;
  }

  private QuestionFormData createQuestion2() {
    QuestionFormData formData = new QuestionFormData();
    formData.setQuestionNr(createQuestionNr());
    formData.getQuestionText().setValue("What is your favorite day in the week?");
    formData.getMultipleChoices().setValue(false);
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Monday"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Tuesday"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Wednesday"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Thursday"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Friday"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Saturday"});
    formData.getChoices().addRow(new Object[]{createChoiceNr(), "Sunday"});
    return formData;
  }

  public static DataStore getInstance() {
    return INSTANCE;
  }

  public Object[][] getQuestions() {
    List<Object[]> data = new ArrayList<Object[]>();

    for (QuestionFormData q : m_questions.values()) {
      data.add(new Object[]{q.getQuestionNr(),
          q.getQuestionText().getValue()});
    }
    return data.toArray(new Object[data.size()][]);
  }

  public void storeQuestion(QuestionFormData formData) {
    if (formData.getQuestionNr() == null) {
      formData.setQuestionNr(createQuestionNr());
    }

    //Clean Choices table:
    Choices choices = formData.getChoices();
    for (int i = 0; i < choices.getRowCount(); i++) {
      switch (choices.getRowState(i)) {
        case ITableHolder.STATUS_INSERTED:
          choices.setChoiceNr(i, createChoiceNr());
          choices.setRowState(i, ITableHolder.STATUS_NON_CHANGED);
          break;
        case ITableHolder.STATUS_DELETED:
          choices.removeRow(i);
          break;
        default:
          choices.setRowState(i, ITableHolder.STATUS_NON_CHANGED);
          break;
      }
    }

    m_questions.put(formData.getQuestionNr(), formData);
  }

  public QuestionFormData getQuestion(Integer questionNr) {
    return m_questions.get(questionNr);
  }

  public void deleteQuestion(Integer questionNr) {
    m_questions.remove(questionNr);
  }

  public QuestionFormData updateQuestionText(Integer questionNr, AbstractValueFieldData<String> questionText, AbstractPropertyData<Boolean> multipleChoices) {
    QuestionFormData question = DataStore.getInstance().getQuestion(questionNr);
    questionText.setValue(question.getQuestionText().getValue());
    multipleChoices.setValue(question.getMultipleChoices().getValue());
    return question;
  }

  public Collection<AnswerFormData> getAnswers() {
    return m_answers.values();
  }

  public Collection<AnswerFormData> getAnswersOfQuestion(Integer questionNr) {
    Collection<AnswerFormData> list = new ArrayList<AnswerFormData>();
    for (AnswerFormData a : getAnswers()) {
      if (questionNr.equals(a.getQuestionNr().getValue())) {
        list.add(a);
      }
    }
    return list;
  }

  public void storeAnswer(AnswerFormData formData) {
    if (formData.getAnswerNr() == null) {
      formData.setAnswerNr(createAnswerNr());
    }
    m_answers.put(formData.getAnswerNr(), formData);
  }

  public AnswerFormData getAnswer(Long answerNr) {
    return m_answers.get(answerNr);
  }

  public void deleteAnswer(Long answerNr) {
    m_answers.remove(answerNr);
  }

  private Integer createQuestionNr() {
    m_questionNrSequence++;
    return Integer.valueOf(m_questionNrSequence);
  }

  private Long createAnswerNr() {
    m_answerNrSequence++;
    return Long.valueOf(m_answerNrSequence);
  }

  private Integer createChoiceNr() {
    m_choiceNrSequence++;
    return Integer.valueOf(m_choiceNrSequence);
  }

}
