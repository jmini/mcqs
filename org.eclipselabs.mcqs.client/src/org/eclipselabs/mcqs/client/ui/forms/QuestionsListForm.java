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
package org.eclipselabs.mcqs.client.ui.forms;

import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.ContentBox.QuestionsField;
import org.eclipselabs.mcqs.shared.services.process.IQuestionProcessService;
import org.eclipselabs.mcqs.shared.services.process.IQuestionsListProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionsListFormData;

@FormData(value = QuestionsListFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class QuestionsListForm extends AbstractForm {

  public QuestionsListForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @Override
  protected String getConfiguredDisplayViewId() {
    return VIEW_ID_CENTER;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Questions");
  }

  public void startDisplay() throws ProcessingException {
    startInternal(new QuestionsListForm.DisplayHandler());
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public QuestionsField getQuestionsField() {
    return getFieldByClass(QuestionsField.class);
  }

  private void reloadForm() throws ProcessingException {
    IQuestionsListProcessService service = SERVICES.getService(IQuestionsListProcessService.class);
    QuestionsListFormData formData = new QuestionsListFormData();
    exportFormData(formData);
    formData = service.load(formData);
    importFormData(formData);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ContentBox extends AbstractGroupBox {

      @Order(10.0)
      public class QuestionsField extends AbstractTableField<QuestionsField.Table> {

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Questions");
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Order(10.0)
        public class Table extends AbstractTable {

          public QuestionColumn getQuestionColumn() {
            return getColumnSet().getColumnByClass(QuestionColumn.class);
          }

          public QuestionNrColumn getQuestionNrColumn() {
            return getColumnSet().getColumnByClass(QuestionNrColumn.class);
          }

          @Order(10.0)
          public class QuestionNrColumn extends AbstractIntegerColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Nr");
            }

            @Override
            protected boolean getConfiguredPrimaryKey() {
              return true;
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }
          }

          @Order(20.0)
          public class QuestionColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Question");
            }

            @Override
            protected int getConfiguredWidth() {
              return 380;
            }
          }

          @Order(10.0)
          public class AddAnAnswerMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("AddAnAnswer");
            }

            @Override
            protected void execAction() throws ProcessingException {
              AnswerForm form = new AnswerForm();
              form.getQuestionNrField().setValue(getQuestionNrColumn().getSelectedValue());
              form.startNew();
            }
          }

          @Order(20.0)
          public class DisplayAllAnswersMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DisplayAllAnswers");
            }

            @Override
            protected void execAction() throws ProcessingException {
              AnswersListForm form = new AnswersListForm();
              form.getQuestionNrField().setValue(getQuestionNrColumn().getSelectedValue());
              form.startDisplay();
            }
          }

          @Order(30.0)
          public class SeparatorMenu extends AbstractMenu {

            @Override
            protected boolean getConfiguredSeparator() {
              return true;
            }
          }

          @Order(40.0)
          public class CreateQuestionMenu extends AbstractMenu {

            @Override
            protected boolean getConfiguredEmptySpaceAction() {
              return true;
            }

            @Override
            protected boolean getConfiguredSingleSelectionAction() {
              return false;
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("CreateQuestion");
            }

            @Override
            protected void execAction() throws ProcessingException {
              QuestionForm form = new QuestionForm();
              form.startNew();
              form.waitFor();
              if (form.isFormStored()) {
                reloadForm();
              }
            }
          }

          @Order(50.0)
          public class EditQuestionMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("EditQuestion");
            }

            @Override
            protected void execAction() throws ProcessingException {
              QuestionForm form = new QuestionForm();
              form.setQuestionNr(getQuestionNrColumn().getSelectedValue());
              form.startModify();
              form.waitFor();
              if (form.isFormStored()) {
                reloadForm();
              }
            }
          }

          @Order(60.0)
          public class DeleteQuestionMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DeleteQuestion");
            }

            @Override
            protected void execAction() throws ProcessingException {
              ITableRow r = getSelectedRow();
              if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("Questions"), getQuestionColumn().getValue(r))) {
                SERVICES.getService(IQuestionProcessService.class).delete(getQuestionNrColumn().getValue(r));
                reloadForm();
              }
            }
          }
        }
      }
    }
  }

  public class DisplayHandler extends AbstractFormHandler {

    @Override
    protected void execLoad() throws ProcessingException {
      reloadForm();
    }
  }
}
