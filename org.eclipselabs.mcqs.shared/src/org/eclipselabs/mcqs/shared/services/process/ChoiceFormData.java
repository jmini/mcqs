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

public class ChoiceFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public ChoiceFormData() {
  }

  public ChoiceNrProperty getChoiceNrProperty() {
    return getPropertyByClass(ChoiceNrProperty.class);
  }

  /**
   * access method for property ChoiceNr.
   */
  public Integer getChoiceNr() {
    return getChoiceNrProperty().getValue();
  }

  /**
   * access method for property ChoiceNr.
   */
  public void setChoiceNr(Integer choiceNr) {
    getChoiceNrProperty().setValue(choiceNr);
  }

  public Choice getChoice() {
    return getFieldByClass(Choice.class);
  }

  public class ChoiceNrProperty extends AbstractPropertyData<Integer> {
    private static final long serialVersionUID = 1L;

    public ChoiceNrProperty() {
    }
  }

  public class Choice extends AbstractValueFieldData<String> {
    private static final long serialVersionUID = 1L;

    public Choice() {
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
