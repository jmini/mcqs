package org.eclipselabs.mcqs.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import java.util.Map;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipselabs.mcqs.shared.services.lookup.ChoicesLookupCall;
import org.eclipse.scout.rt.shared.data.form.properties.AbstractPropertyData;

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

  public static class Choices extends AbstractValueFieldData<Long[]> {
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
    protected void initValidationRules(Map<String, Object> ruleMap) {
      super.initValidationRules(ruleMap);
      ruleMap.put(ValidationRule.MAX_LENGTH, 4000);
    }
  }

  public static class YourName extends AbstractValueFieldData<String> {
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
