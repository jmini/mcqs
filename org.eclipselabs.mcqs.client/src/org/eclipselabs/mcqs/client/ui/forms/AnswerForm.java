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
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.listbox.AbstractListBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.service.SERVICES;
import org.eclipselabs.mcqs.client.ui.forms.AnswerForm.MainBox.CancelButton;
import org.eclipselabs.mcqs.client.ui.forms.AnswerForm.MainBox.ChoicesField;
import org.eclipselabs.mcqs.client.ui.forms.AnswerForm.MainBox.OkButton;
import org.eclipselabs.mcqs.client.ui.forms.AnswerForm.MainBox.QuestionNrField;
import org.eclipselabs.mcqs.client.ui.forms.AnswerForm.MainBox.QuestionTextField;
import org.eclipselabs.mcqs.client.ui.forms.AnswerForm.MainBox.YourNameField;
import org.eclipselabs.mcqs.shared.security.UpdateAnswerPermission;
import org.eclipselabs.mcqs.shared.services.lookup.ChoicesLookupCall;
import org.eclipselabs.mcqs.shared.services.process.AnswerFormData;
import org.eclipselabs.mcqs.shared.services.process.IAnswerProcessService;

@FormData(value = AnswerFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class AnswerForm extends AbstractForm {

  private Long answerNr;

  public AnswerForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Answer");
  }

  @FormData
  public Long getAnswerNr() {
    return answerNr;
  }

  @FormData
  public void setAnswerNr(Long answerNr) {
    this.answerNr = answerNr;
  }

  public void startModify() throws ProcessingException {
    startInternal(new AnswerForm.ModifyHandler());
  }

  public void startNew() throws ProcessingException {
    startInternal(new AnswerForm.NewHandler());
  }

  public CancelButton getCancelButton() {
    return getFieldByClass(CancelButton.class);
  }

  public ChoicesField getChoicesField() {
    return getFieldByClass(ChoicesField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  public QuestionNrField getQuestionNrField() {
    return getFieldByClass(QuestionNrField.class);
  }

  public QuestionTextField getQuestionTextField() {
    return getFieldByClass(QuestionTextField.class);
  }

  public YourNameField getYourNameField() {
    return getFieldByClass(YourNameField.class);
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
        return TEXTS.get("QuestionNr");
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

    @Order(30.0)
    public class YourNameField extends AbstractStringField {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("YourName");
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }
    }

    @Order(40.0)
    public class ChoicesField extends AbstractListBox<Long> {

      @Override
      protected int getConfiguredGridH() {
        return 6;
      }

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Choices");
      }

      @Override
      protected Class<? extends LookupCall> getConfiguredLookupCall() {
        return ChoicesLookupCall.class;
      }

      @Override
      protected Class<? extends IValueField> getConfiguredMasterField() {
        return AnswerForm.MainBox.QuestionNrField.class;
      }
    }

    @Order(50.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(60.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class ModifyHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IAnswerProcessService service = SERVICES.getService(IAnswerProcessService.class);
      AnswerFormData formData = new AnswerFormData();
      exportFormData(formData);
      formData = service.load(formData);
      importFormData(formData);
      setEnabledPermission(new UpdateAnswerPermission());
    }

    @Override
    public void execStore() throws ProcessingException {
      IAnswerProcessService service = SERVICES.getService(IAnswerProcessService.class);
      AnswerFormData formData = new AnswerFormData();
      exportFormData(formData);
      formData = service.store(formData);
    }
  }

  public class NewHandler extends AbstractFormHandler {

    @Override
    public void execLoad() throws ProcessingException {
      IAnswerProcessService service = SERVICES.getService(IAnswerProcessService.class);
      AnswerFormData formData = new AnswerFormData();
      exportFormData(formData);
      formData = service.prepareCreate(formData);
      importFormData(formData);
    }

    @Override
    public void execStore() throws ProcessingException {
      IAnswerProcessService service = SERVICES.getService(IAnswerProcessService.class);
      AnswerFormData formData = new AnswerFormData();
      exportFormData(formData);
      formData = service.create(formData);
      importFormData(formData);
    }
  }
}
