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

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.form.AbstractFormData;
import org.eclipse.scout.rt.shared.data.form.fields.tablefield.AbstractTableFieldBeanData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications recommended.
 * 
 * @generated
 */
@Generated(value = "org.eclipse.scout.sdk.workspace.dto.formdata.FormDataDtoUpdateOperation", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class QuestionsListFormData extends AbstractFormData {

  private static final long serialVersionUID = 1L;

  public QuestionsListFormData() {
  }

  public Questions getQuestions() {
    return getFieldByClass(Questions.class);
  }

  public static class Questions extends AbstractTableFieldBeanData {

    private static final long serialVersionUID = 1L;

    public Questions() {
    }

    @Override
    public QuestionsRowData addRow() {
      return (QuestionsRowData) super.addRow();
    }

    @Override
    public QuestionsRowData addRow(int rowState) {
      return (QuestionsRowData) super.addRow(rowState);
    }

    @Override
    public QuestionsRowData createRow() {
      return new QuestionsRowData();
    }

    @Override
    public Class<? extends AbstractTableRowData> getRowType() {
      return QuestionsRowData.class;
    }

    @Override
    public QuestionsRowData[] getRows() {
      return (QuestionsRowData[]) super.getRows();
    }

    @Override
    public QuestionsRowData rowAt(int index) {
      return (QuestionsRowData) super.rowAt(index);
    }

    public void setRows(QuestionsRowData[] rows) {
      super.setRows(rows);
    }

    public static class QuestionsRowData extends AbstractTableRowData {

      private static final long serialVersionUID = 1L;
      public static final String questionNr = "questionNr";
      public static final String question = "question";
      private Integer m_questionNr;
      private String m_question;

      public QuestionsRowData() {
      }

      /**
       * @return the QuestionNr
       */
      public Integer getQuestionNr() {
        return m_questionNr;
      }

      /**
       * @param questionNr
       *          the QuestionNr to set
       */
      public void setQuestionNr(Integer questionNr) {
        m_questionNr = questionNr;
      }

      /**
       * @return the Question
       */
      public String getQuestion() {
        return m_question;
      }

      /**
       * @param question
       *          the Question to set
       */
      public void setQuestion(String question) {
        m_question = question;
      }
    }
  }
}
