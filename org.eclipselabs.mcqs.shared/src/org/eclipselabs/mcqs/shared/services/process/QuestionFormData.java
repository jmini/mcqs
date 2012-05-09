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

public class QuestionFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public QuestionFormData() {
  }

  public QuestionNrProperty getQuestionNrProperty() {
    return getPropertyByClass(QuestionNrProperty.class);
  }

  /**
   * access method for property QuestionNr.
   */
  public Integer getQuestionNr() {
    return getQuestionNrProperty().getValue();
  }

  /**
   * access method for property QuestionNr.
   */
  public void setQuestionNr(Integer questionNr) {
    getQuestionNrProperty().setValue(questionNr);
  }

  public Choices getChoices() {
    return getFieldByClass(Choices.class);
  }

  public QuestionText getQuestionText() {
    return getFieldByClass(QuestionText.class);
  }

  public class QuestionNrProperty extends AbstractPropertyData<Integer> {
    private static final long serialVersionUID = 1L;

    public QuestionNrProperty() {
    }
  }

  public static class Choices extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Choices() {
    }

    public static final int CHOICE_NR_COLUMN_ID = 0;
    public static final int CHOICE_TEXT_COLUMN_ID = 1;

    public void setChoiceNr(int row, Integer choiceNr) {
      setValueInternal(row, CHOICE_NR_COLUMN_ID, choiceNr);
    }

    public Integer getChoiceNr(int row) {
      return (Integer) getValueInternal(row, CHOICE_NR_COLUMN_ID);
    }

    public void setChoiceText(int row, String choiceText) {
      setValueInternal(row, CHOICE_TEXT_COLUMN_ID, choiceText);
    }

    public String getChoiceText(int row) {
      return (String) getValueInternal(row, CHOICE_TEXT_COLUMN_ID);
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case CHOICE_NR_COLUMN_ID:
          return getChoiceNr(row);
        case CHOICE_TEXT_COLUMN_ID:
          return getChoiceText(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case CHOICE_NR_COLUMN_ID:
          setChoiceNr(row, (Integer) value);
          break;
        case CHOICE_TEXT_COLUMN_ID:
          setChoiceText(row, (String) value);
          break;
      }
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
}
