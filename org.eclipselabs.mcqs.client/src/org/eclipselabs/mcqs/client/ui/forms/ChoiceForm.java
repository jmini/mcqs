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
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCancelButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipselabs.mcqs.client.ui.forms.ChoiceForm.MainBox.CancelButton;
import org.eclipselabs.mcqs.client.ui.forms.ChoiceForm.MainBox.ContentBox.ChoiceField;
import org.eclipselabs.mcqs.client.ui.forms.ChoiceForm.MainBox.OkButton;
import org.eclipselabs.mcqs.shared.services.process.ChoiceFormData;

@FormData(value = ChoiceFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class ChoiceForm extends AbstractForm {

  private Integer m_choiceNr;

  public ChoiceForm() throws ProcessingException {
    super();
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Choice");
  }

  public void startEdit() throws ProcessingException {
    startInternal(new ChoiceForm.EditHandler());
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

  public OkButton getOkButton() {
    return getFieldByClass(OkButton.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ContentBox extends AbstractGroupBox {

      @Order(10.0)
      public class ChoiceField extends AbstractStringField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Choice");
        }

        @Override
        protected boolean getConfiguredMandatory() {
          return true;
        }
      }
    }

    @Order(20.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(30.0)
    public class CancelButton extends AbstractCancelButton {
    }
  }

  public class EditHandler extends AbstractFormHandler {
  }

  @FormData
  public Integer getChoiceNr() {
    return m_choiceNr;
  }

  @FormData
  public void setChoiceNr(Integer choiceNr) {
    m_choiceNr = choiceNr;
  }
}
