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

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;
import org.eclipselabs.mcqs.client.ui.forms.ChoiceForm.MainBox.ContentBox.ChoiceField;
import org.eclipselabs.mcqs.client.ui.forms.QuestionForm.MainBox.CancelButton;
import org.eclipselabs.mcqs.client.ui.forms.QuestionForm.MainBox.ContentBox.MultipleChoicesField;
import org.eclipselabs.mcqs.client.ui.forms.QuestionForm.MainBox.OkButton;
import org.eclipselabs.mcqs.shared.security.UpdateQuestionPermission;
import org.eclipselabs.mcqs.shared.services.process.IQuestionProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;

@FormData(value = QuestionFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class QuestionForm extends AbstractForm {

  private Integer m_questionNr;

  public QuestionForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Question");
  }

  public void startModify() throws ProcessingException {
    startInternal(new QuestionForm.ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new QuestionForm.NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ChoiceField getChoiceField() {
    return getFieldByClass(ChoiceField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MultipleChoicesField getMultipleChoicesField() {
    return getFieldByClass(MultipleChoicesField.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ContentBox extends AbstractGroupBox {

      @Order(10.0)
      public class QuestionTextField extends AbstractStringField {

        @Override
        protected int getConfiguredGridH() {
          return 2;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Question");
        }

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected boolean getConfiguredWrapText() {
          return true;
        }
      }

      @Order(20.0)
      public class MultipleChoicesField extends AbstractCheckBox {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("MultipleChoices");
        }
      }

      @Order(30.0)
      public class ChoicesField extends AbstractTableField<ChoicesField.Table> {

        @Override
        protected int getConfiguredGridH() {
          return 6;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Choices");
        }

        @Order(10.0)
        public class Table extends AbstractTable {

          @Override
          protected boolean getConfiguredAutoResizeColumns() {
            return true;
          }

          public ChoiceNrColumn getChoiceNrColumn() {
            return getColumnSet().getColumnByClass(ChoiceNrColumn.class);
          }

          public ChoiceTextColumn getChoiceTextColumn() {
            return getColumnSet().getColumnByClass(ChoiceTextColumn.class);
          }

          @Order(10.0)
          public class ChoiceNrColumn extends AbstractIntegerColumn {

            @Override
            protected boolean getConfiguredDisplayable() {
              return false;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Nr");
            }
          }

          @Order(20.0)
          public class ChoiceTextColumn extends AbstractStringColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Choice");
            }
          }

          @Order(10.0)
          public class AddChoiceMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("AddChoice");
            }

            @Override
            protected void execAction() throws ProcessingException {
              ChoiceForm form = new ChoiceForm();
              form.startEdit();
              form.waitFor();
              if (form.isFormStored()) {
                ITableRow r = createRow();
                getChoiceTextColumn().setValue(r, form.getChoiceField().getValue());
                addRow(r, true);
              }
            }
          }

          @Order(20.0)
          public class EditChoiceMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("EditChoice");
            }

            @Override
            protected void execAction() throws ProcessingException {
              ITableRow r = getSelectedRow();

              ChoiceForm form = new ChoiceForm();
              form.setChoiceNr(getChoiceNrColumn().getValue(r));
              form.getChoiceField().setValue(getChoiceTextColumn().getValue(r));

              form.startEdit();
              form.waitFor();
              if (form.isFormStored()) {
                getChoiceTextColumn().setValue(r, form.getChoiceField().getValue());
              }
            }
          }

          @Order(30.0)
          public class RemoveChoiceMenu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("RemoveChoice");
            }

            @Override
            protected void execAction() throws ProcessingException {
              ITableRow r = getSelectedRow();
              if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("Choice"), getChoiceTextColumn().getValue(r))) {
                deleteRow(r);
              }
            }
          }
        }
      }
    }

    @Order(30.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(40.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IQuestionProcessService service = SERVICES.getService(IQuestionProcessService.class);
      QuestionFormData formData = new QuestionFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateQuestionPermission());
      getMultipleChoicesField().setEnabled(false);
    }

    @Override
    public void execStore() throws ProcessingException {
      IQuestionProcessService service = SERVICES.getService(IQuestionProcessService.class);
      QuestionFormData formData = new QuestionFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IQuestionProcessService service = SERVICES.getService(IQuestionProcessService.class);
      QuestionFormData formData = new QuestionFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IQuestionProcessService service = SERVICES.getService(IQuestionProcessService.class);
      QuestionFormData formData = new QuestionFormData();
      exportFormData(formData);
      formData = service.create(formData);
    }
  }

  @FormData
  public Integer getQuestionNr() {
    return m_questionNr;
  }

  @FormData
  public void setQuestionNr(Integer questionNr) {
    m_questionNr = questionNr;
  }
}
