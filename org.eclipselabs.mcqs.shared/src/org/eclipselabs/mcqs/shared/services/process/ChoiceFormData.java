package org.eclipselabs.mcqs.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import java.util.Map;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
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

  public static class Choice extends AbstractValueFieldData<String> {
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
