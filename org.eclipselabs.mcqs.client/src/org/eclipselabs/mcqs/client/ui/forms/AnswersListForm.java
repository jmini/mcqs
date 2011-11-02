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
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.shared.ScoutTexts;
import org.eclipse.scout.service.SERVICES;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.OkButton;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.QuestionNrField;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.QuestionTextField;
import org.eclipselabs.mcqs.shared.Texts;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData;
import org.eclipselabs.mcqs.shared.services.process.IAnswersListProcessService;

@FormData(value = AnswersListFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AnswersListForm extends AbstractForm {

  public AnswersListForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return Texts.get("Answers");
  }

  public void startDisplay() throws ProcessingException {
    startInternal(new AnswersListForm.DisplayHandler());
  }

  public AnswersField getAnswersField() {
    return getFieldByClass(AnswersField.class);
  }

  public QuestionNrField getQuestionNrField() {
    return getFieldByClass(QuestionNrField.class);
  }

  public QuestionTextField getQuestionTextField() {
    return getFieldByClass(QuestionTextField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Order(10.0)
    public class QuestionNrField extends AbstractIntegerField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected String getConfiguredLabel() {
        return Texts.get("Question");
      }
    }

    @Order(20.0)
    public class QuestionTextField extends AbstractStringField {

      @Override
      protected boolean getConfiguredEnabled() {
        return false;
      }

      @Override
      protected int getConfiguredGridH() {
        return 2;
      }

      @Override
      protected String getConfiguredLabel() {
        return Texts.get("Question");
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

    @Order(30.0)
    public class AnswersField extends AbstractTableField<AnswersField.Table> {

      @Override
      protected int getConfiguredGridH() {
        return 6;
      }

      @Override
      protected String getConfiguredLabel() {
        return Texts.get("Answers");
      }

      @Order(10.0)
      public class Table extends AbstractTable {

        public NameColumn getNameColumn() {
          return getColumnSet().getColumnByClass(NameColumn.class);
        }

        public AnswerNrColumn getAnswerNrColumn() {
          return getColumnSet().getColumnByClass(AnswerNrColumn.class);
        }

        @Order(10.0)
        public class AnswerNrColumn extends AbstractLongColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return Texts.get("No_");
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
        public class NameColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return ScoutTexts.get("Name");
          }
        }

        @Order(10.0)
        public class EditAnswerMenu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return Texts.get("EditAnswer");
          }

          @Override
          protected void execAction() throws ProcessingException {
            AnswerForm form = new AnswerForm();
            form.setAnswerNr(getAnswerNrColumn().getSelectedValue());
            form.startModify();
            form.waitFor();
            if (form.isFormStored()) {
              getNameColumn().setValue(getSelectedRow(), form.getYourNameField().getValue());
            }
          }
        }
      }
    }

    @Order(40.0)
    public class OkButton extends AbstractOkButton {
    }
  }

  public class DisplayHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IAnswersListProcessService service = SERVICES.getService(IAnswersListProcessService.class);
      AnswersListFormData formData = new AnswersListFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
    }
  }
}
