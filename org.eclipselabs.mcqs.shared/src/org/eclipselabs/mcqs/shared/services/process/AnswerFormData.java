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
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;
import org.eclipselabs.mcqs.shared.services.lookup.ChoicesLookupCall;

public class AnswerFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public AnswerFormData() {
  }

  public AnswerNrProperty getAnswerNrProperty() {
    return getPropertyByClass(AnswerNrProperty.class);
  }

  /**
   * access method for property AnswerNr.
   */
  public Long getAnswerNr() {
    return getAnswerNrProperty().getValue();
  }

  /**
   * access method for property AnswerNr.
   */
  public void setAnswerNr(Long answerNr) {
    getAnswerNrProperty().setValue(answerNr);
  }

  public Choices getChoices() {
    return getFieldByClass(Choices.class);
  }

  public QuestionNr getQuestionNr() {
    return getFieldByClass(QuestionNr.class);
  }

  public QuestionText getQuestionText() {
    return getFieldByClass(QuestionText.class);
  }

  public YourName getYourName() {
    return getFieldByClass(YourName.class);
  }

  public class AnswerNrProperty extends AbstractPropertyData<Long> {
    private static final long serialVersionUID = 1L;

    public AnswerNrProperty() {
    }
  }

  public class Choices extends AbstractValueFieldData<Long[]> {
    private static final long serialVersionUID = 1L;

    public Choices() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.LOOKUP_CALL, ChoicesLookupCall.class);
      ruleMap.put(ValidationRule.MASTER_VALUE_FIELD, QuestionNr.class);
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

  public class YourName extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public YourName() {
    }

    /**
     * list of derived validation rules.
     */
    @Override
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MANDATORY, true);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }
}
