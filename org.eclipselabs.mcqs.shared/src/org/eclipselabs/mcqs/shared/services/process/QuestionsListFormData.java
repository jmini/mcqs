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
