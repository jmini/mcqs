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
package org.eclipselabs.mcqs.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

public class AnswersListFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public AnswersListFormData() {
  }

  public MultipleChoicesProperty getMultipleChoicesProperty() {
    return getPropertyByClass(MultipleChoicesProperty.class);
  }

  /**
   * access method for property MultipleChoices.
   */
  public Boolean getMultipleChoices() {
    return getMultipleChoicesProperty().getValue();
  }

  /**
   * access method for property MultipleChoices.
   */
  public void setMultipleChoices(Boolean multipleChoices) {
    getMultipleChoicesProperty().setValue(multipleChoices);
  }

  public Answers getAnswers() {
    return getFieldByClass(Answers.class);
  }

  public QuestionNr getQuestionNr() {
    return getFieldByClass(QuestionNr.class);
  }

  public QuestionText getQuestionText() {
    return getFieldByClass(QuestionText.class);
  }

  public Statistics getStatistics() {
    return getFieldByClass(Statistics.class);
  }

  public class MultipleChoicesProperty extends AbstractPropertyData<Boolean> {
    private static final long serialVersionUID = 1L;

    public MultipleChoicesProperty() {
    }
  }

  public static class Answers extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Answers() {
    }

    public static final int ANSWER_NR_COLUMN_ID = 0;
    public static final int NAME_COLUMN_ID = 1;

    public void setAnswerNr(int row, Long answerNr) {
      setValueInternal(row, ANSWER_NR_COLUMN_ID, answerNr);
    }

    public Long getAnswerNr(int row) {
      return (Long) getValueInternal(row, ANSWER_NR_COLUMN_ID);
    }

    public void setName(int row, String name) {
      setValueInternal(row, NAME_COLUMN_ID, name);
    }

    public String getName(int row) {
      return (String) getValueInternal(row, NAME_COLUMN_ID);
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case ANSWER_NR_COLUMN_ID:
          return getAnswerNr(row);
        case NAME_COLUMN_ID:
          return getName(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case ANSWER_NR_COLUMN_ID:
          setAnswerNr(row, (Long) value);
          break;
        case NAME_COLUMN_ID:
          setName(row, (String) value);
          break;
      }
    }
  }

  public static class QuestionNr extends AbstractValueFieldData<Integer> {
    private static final long serialVersionUID = 1L;

    public QuestionNr() {
    }
  }

  public static class QuestionText extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public QuestionText() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(java.util.Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }

  public static class Statistics extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Statistics() {
    }

    public static final int CHOICE_COLUMN_ID = 0;
    public static final int RESULT_COLUMN_ID = 1;
    public static final int RESULT_YES_COLUMN_ID = 2;
    public static final int RESULT_NO_COLUMN_ID = 3;

    public void setChoice(int row, String choice) {
      setValueInternal(row, CHOICE_COLUMN_ID, choice);
    }

    public String getChoice(int row) {
      return (String) getValueInternal(row, CHOICE_COLUMN_ID);
    }

    public void setResult(int row, Double result) {
      setValueInternal(row, RESULT_COLUMN_ID, result);
    }

    public Double getResult(int row) {
      return (Double) getValueInternal(row, RESULT_COLUMN_ID);
    }

    public void setResultYes(int row, Double resultYes) {
      setValueInternal(row, RESULT_YES_COLUMN_ID, resultYes);
    }

    public Double getResultYes(int row) {
      return (Double) getValueInternal(row, RESULT_YES_COLUMN_ID);
    }

    public void setResultNo(int row, Double resultNo) {
      setValueInternal(row, RESULT_NO_COLUMN_ID, resultNo);
    }

    public Double getResultNo(int row) {
      return (Double) getValueInternal(row, RESULT_NO_COLUMN_ID);
    }

    @Override
    public int getColumnCount() {
      return 4;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case CHOICE_COLUMN_ID:
          return getChoice(row);
        case RESULT_COLUMN_ID:
          return getResult(row);
        case RESULT_YES_COLUMN_ID:
          return getResultYes(row);
        case RESULT_NO_COLUMN_ID:
          return getResultNo(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case CHOICE_COLUMN_ID:
          setChoice(row, (String) value);
          break;
        case RESULT_COLUMN_ID:
          setResult(row, (Double) value);
          break;
        case RESULT_YES_COLUMN_ID:
          setResultYes(row, (Double) value);
          break;
        case RESULT_NO_COLUMN_ID:
          setResultNo(row, (Double) value);
          break;
      }
    }
  }
}
