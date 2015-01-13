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
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.scout.commons.holders.ITableHolder;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;
import org.eclipselabs.mcqs.shared.services.process.AnswerFormData;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData.Choices;

public final class DataStore {
  private static final DataStore INSTANCE = new DataStore();

  private static final int QUESTION_NR_TRAVEL = 1;
  private static final int QUESTION_NR_DAY = 2;

  private static final int CHOICE_NR_TRAVEL_ASIA = 1;
  private static final int CHOICE_NR_TRAVEL_AFRICA = 2;
  private static final int CHOICE_NR_TRAVEL_NORTH_AMERICA = 3;
  private static final int CHOICE_NR_TRAVEL_SOUTH_AMERICA = 4;
  private static final int CHOICE_NR_TRAVEL_ANTARCTICA = 5;
  private static final int CHOICE_NR_TRAVEL_EUROPE = 6;
  private static final int CHOICE_NR_TRAVEL_AUSTRALIA = 7;
  private static final int CHOICE_NR_DAY_MONDAY = 8;
  private static final int CHOICE_NR_DAY_TUESDAY = 9;
  private static final int CHOICE_NR_DAY_WEDNESDAY = 10;
  private static final int CHOICE_NR_DAY_THURSDAY = 11;
  private static final int CHOICE_NR_DAY_FRIDAY = 12;
  private static final int CHOICE_NR_DAY_SATURDAY = 13;
  private static final int CHOICE_NR_DAY_SUNDAY = 14;

  private final Map<Integer, QuestionFormData> m_questions;
  private final Map<Long, AnswerFormData> m_answers;

  private int m_questionNrSequence = QUESTION_NR_DAY; //max(QUESTION_NR_*)
  private int m_answerNrSequence = 0;
  private int m_choiceNrSequence = CHOICE_NR_DAY_SUNDAY; //max(CHOICE_NR_*)
  private FirstNameGenerator m_nameGenerator = new FirstNameGenerator();

  private DataStore() {
    m_questions = new ConcurrentHashMap<Integer, QuestionFormData>();
    storeQuestion(createQuestion1());
    storeQuestion(createQuestion2());
    m_answers = new ConcurrentHashMap<Long, AnswerFormData>();
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_ASIA, CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_AFRICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_ASIA, CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_SOUTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_SOUTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_NORTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_ASIA, CHOICE_NR_TRAVEL_EUROPE));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_SOUTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_NORTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_ASIA, CHOICE_NR_TRAVEL_NORTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_NORTH_AMERICA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_AFRICA, CHOICE_NR_TRAVEL_ASIA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_ANTARCTICA, CHOICE_NR_TRAVEL_ASIA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_AUSTRALIA, CHOICE_NR_TRAVEL_ASIA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_AFRICA, CHOICE_NR_TRAVEL_ANTARCTICA, CHOICE_NR_TRAVEL_AUSTRALIA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_NORTH_AMERICA, CHOICE_NR_TRAVEL_AFRICA, CHOICE_NR_TRAVEL_AUSTRALIA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_AFRICA, CHOICE_NR_TRAVEL_AUSTRALIA));
    storeAnswer(creatateAnswer1(CHOICE_NR_TRAVEL_EUROPE, CHOICE_NR_TRAVEL_AUSTRALIA));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_MONDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_MONDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_MONDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_MONDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_TUESDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_WEDNESDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_WEDNESDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_WEDNESDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_THURSDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_THURSDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_FRIDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_FRIDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_FRIDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_FRIDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_FRIDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SATURDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SATURDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SATURDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SATURDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SATURDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SATURDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
    storeAnswer(creatateAnswer2(CHOICE_NR_DAY_SUNDAY));
  }

  private QuestionFormData createQuestion1() {
    QuestionFormData formData = new QuestionFormData();
    formData.setQuestionNr(QUESTION_NR_TRAVEL);
    formData.getQuestionText().setValue("Where did you travel last year?");
    formData.getMultipleChoices().setValue(true);
    addChoice(formData, CHOICE_NR_TRAVEL_ASIA, "Asia");
    addChoice(formData, CHOICE_NR_TRAVEL_AFRICA, "Africa");
    addChoice(formData, CHOICE_NR_TRAVEL_NORTH_AMERICA, "North America");
    addChoice(formData, CHOICE_NR_TRAVEL_SOUTH_AMERICA, "South America");
    addChoice(formData, CHOICE_NR_TRAVEL_ANTARCTICA, "Antarctica");
    addChoice(formData, CHOICE_NR_TRAVEL_EUROPE, "Europe");
    addChoice(formData, CHOICE_NR_TRAVEL_AUSTRALIA, "Australia");
    return formData;
  }

  private QuestionFormData createQuestion2() {
    QuestionFormData formData = new QuestionFormData();
    formData.setQuestionNr(QUESTION_NR_DAY);
    formData.getQuestionText().setValue("What is your favorite day in the week?");
    formData.getMultipleChoices().setValue(false);
    addChoice(formData, CHOICE_NR_DAY_MONDAY, "Monday");
    addChoice(formData, CHOICE_NR_DAY_TUESDAY, "Tuesday");
    addChoice(formData, CHOICE_NR_DAY_WEDNESDAY, "Wednesday");
    addChoice(formData, CHOICE_NR_DAY_THURSDAY, "Thursday");
    addChoice(formData, CHOICE_NR_DAY_FRIDAY, "Friday");
    addChoice(formData, CHOICE_NR_DAY_SATURDAY, "Saturday");
    addChoice(formData, CHOICE_NR_DAY_SUNDAY, "Sunday");
    return formData;
  }

  private static void addChoice(QuestionFormData formData, Integer choiceNr, String choiceText) {
    Choices tableData = formData.getChoices();
    int r = tableData.addRow();
    tableData.setChoiceNr(r, choiceNr);
    tableData.setChoiceText(r, choiceText);
  }

  private AnswerFormData creatateAnswer1(int... choices) {
    AnswerFormData formData = new AnswerFormData();
    formData.getQuestionNr().setValue(QUESTION_NR_TRAVEL);
    updateQuestionText(formData);
    formData.getYourName().setValue(m_nameGenerator.generate());
    formData.getChoices().setValue(toValue(choices));
    return formData;
  }

  private static Set<Long> toValue(int... choices) {
    if (choices == null) {
      return Collections.emptySet();
    }
    Set<Long> value = new HashSet<Long>();
    for (int i = 0; i < choices.length; i++) {
      value.add(Long.valueOf(choices[i]));
    }
    return value;
  }

  private AnswerFormData creatateAnswer2(int choice) {
    AnswerFormData formData = new AnswerFormData();
    formData.getQuestionNr().setValue(QUESTION_NR_DAY);
    updateQuestionText(formData);
    formData.getYourName().setValue(m_nameGenerator.generate());
    formData.getChoice().setValue(Long.valueOf(choice));
    return formData;
  }

  public static DataStore getInstance() {
    return INSTANCE;
  }

  public Collection<QuestionFormData> getQuestions() {
    return m_questions.values();
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

  public QuestionFormData updateQuestionText(AnswerFormData formData) {
    return updateQuestionTextInternal(formData.getQuestionNr().getValue(), formData.getQuestionText(), formData.getMultipleChoicesProperty());
  }

  public QuestionFormData updateQuestionText(AnswersListFormData formData) {
    return updateQuestionTextInternal(formData.getQuestionNr().getValue(), formData.getQuestionText(), formData.getMultipleChoicesProperty());
  }

  private QuestionFormData updateQuestionTextInternal(Integer questionNr, AbstractValueFieldData<String> questionText, AbstractPropertyData<Boolean> multipleChoices) {
    QuestionFormData question = getQuestion(questionNr);
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
