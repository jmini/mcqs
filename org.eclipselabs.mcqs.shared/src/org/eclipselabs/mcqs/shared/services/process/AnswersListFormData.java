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

public class AnswersListFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public AnswersListFormData() {
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

  public class Answers extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Answers() {
    }

    public void setAnswerNr(int row, Long answerNr) {
      setValueInternal(row, 0, answerNr);
    }

    public Long getAnswerNr(int row) {
      return (Long) getValueInternal(row, 0);
    }

    public void setName(int row, String name) {
      setValueInternal(row, 1, name);
    }

    public String getName(int row) {
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
          return getAnswerNr(row);
        case 1:
          return getName(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case 0:
          setAnswerNr(row, (Long) value);
          break;
        case 1:
          setName(row, (String) value);
          break;
      }
    }
  }

  public class QuestionNr extends AbstractValueFieldData<Integer> {
    private static final long serialVersionUID = 1L;

    public QuestionNr() {
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
