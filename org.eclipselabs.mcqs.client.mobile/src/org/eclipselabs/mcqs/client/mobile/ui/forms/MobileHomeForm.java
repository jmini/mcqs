/*******************************************************************************
 * Copyright 2013 Jeremie Bresson
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
package org.eclipselabs.mcqs.client.mobile.ui.forms;

import org.eclipse.scout.commons.annotations.InjectFieldTo;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.rt.extension.client.ui.form.fields.button.AbstractExtensibleButton;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm;

public class MobileHomeForm extends QuestionsListForm {

  public MobileHomeForm() throws ProcessingException {
    super();
  }

  public LogoutButton getLogoutButton() {
    return getFieldByClass(LogoutButton.class);
  }

  @InjectFieldTo(QuestionsListForm.MainBox.class)
  @Order(10.0)
  public class LogoutButton extends AbstractExtensibleButton {

    @Override
    protected String getConfiguredLabel() {
      return TEXTS.get("Logoff");
    }

    @Override
    protected void execClickAction() throws ProcessingException {
      ClientJob.getCurrentSession().stopSession();
    }
  }
}
