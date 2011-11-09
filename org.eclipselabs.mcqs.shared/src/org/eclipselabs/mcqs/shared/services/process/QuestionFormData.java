/*******************************************************************************
 * Copyright 2011 Jeremie Bresson
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

import java.util.Map;

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

  public class Choices extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Choices() {
    }

    public void setChoiceNr(int row, Integer choiceNr) {
      setValueInternal(row, 0, choiceNr);
    }

    public Integer getChoiceNr(int row) {
      return (Integer) getValueInternal(row, 0);
    }

    public void setChoiceText(int row, String choiceText) {
      setValueInternal(row, 1, choiceText);
    }

    public String getChoiceText(int row) {
      return (String) getValueInternal(row, 1);
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case 0:
          return getChoiceNr(row);
        case 1:
          return getChoiceText(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case 0:
          setChoiceNr(row, (Integer) value);
          break;
        case 1:
          setChoiceText(row, (String) value);
          break;
      }
    }
  }

  public class QuestionText extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public QuestionText() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }
}
