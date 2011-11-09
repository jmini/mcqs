package org.eclipselabs.mcqs.shared.services.process;

import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;

public class QuestionsListFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public QuestionsListFormData() {
  }

  public Questions getQuestions() {
    return getFieldByClass(Questions.class);
  }

  public class Questions extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Questions() {
    }

    public void setQuestionNr(int row, Integer questionNr) {
      setValueInternal(row, 0, questionNr);
    }

    public Integer getQuestionNr(int row) {
      return (Integer) getValueInternal(row, 0);
    }

    public void setQuestion(int row, String question) {
      setValueInternal(row, 1, question);
    }

    public String getQuestion(int row) {
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
          return getQuestionNr(row);
        case 1:
          return getQuestion(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case 0:
          setQuestionNr(row, (Integer) value);
          break;
        case 1:
          setQuestion(row, (String) value);
          break;
      }
    }
  }
}
