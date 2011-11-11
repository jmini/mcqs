package org.eclipselabs.mcqs.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.ValidationRule;
import java.util.Map;
import org.eclipse.scout.rt.shared.data.form.fields.AbstractValueFieldData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
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

  public Statistics getStatistics() {
    return getFieldByClass(Statistics.class);
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
    protected void initValidationRules(Map<String, Object> ruleMap) {
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

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case CHOICE_COLUMN_ID:
          return getChoice(row);
        case RESULT_COLUMN_ID:
          return getResult(row);
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
      }
    }
  }
}
