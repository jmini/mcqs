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
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldData;

public class QuestionsListFormData extends AbstractFormData {
  private static final long serialVersionUID = 1L;

  public QuestionsListFormData() {
  }

  public Questions getQuestions() {
    return getFieldByClass(Questions.class);
  }

  public static class Questions extends AbstractTableFieldData {
    private static final long serialVersionUID = 1L;

    public Questions() {
    }

    public static final int QUESTION_NR_COLUMN_ID = 0;
    public static final int QUESTION_COLUMN_ID = 1;

    public void setQuestionNr(int row, Integer questionNr) {
      setValueInternal(row, QUESTION_NR_COLUMN_ID, questionNr);
    }

    public Integer getQuestionNr(int row) {
      return (Integer) getValueInternal(row, QUESTION_NR_COLUMN_ID);
    }

    public void setQuestion(int row, String question) {
      setValueInternal(row, QUESTION_COLUMN_ID, question);
    }

    public String getQuestion(int row) {
      return (String) getValueInternal(row, QUESTION_COLUMN_ID);
    }

    @Override
    public int getColumnCount() {
      return 2;
    }

    @Override
    public Object getValueAt(int row, int column) {
      switch (column) {
        case QUESTION_NR_COLUMN_ID:
          return getQuestionNr(row);
        case QUESTION_COLUMN_ID:
          return getQuestion(row);
        default:
          return null;
      }
    }

    @Override
    public void setValueAt(int row, int column, Object value) {
      switch (column) {
        case QUESTION_NR_COLUMN_ID:
          setQuestionNr(row, (Integer) value);
          break;
        case QUESTION_COLUMN_ID:
          setQuestion(row, (String) value);
          break;
      }
    }
  }
}
