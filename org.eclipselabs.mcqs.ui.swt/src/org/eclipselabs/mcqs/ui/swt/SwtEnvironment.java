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
package org.eclipselabs.mcqs.ui.swt;

import org.eclipse.jface.util.Util;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.ui.swt.AbstractSwtEnvironment;
import org.eclipse.swt.SWT;
import org.eclipselabs.mcqs.ui.swt.window.messagebox.SwtMacScoutMessageBoxDialog;
import org.osgi.framework.Bundle;

/**
 * <h3>SwtEnvironment</h3> This class provides the possibility to write a own representation of any scout field.
 * Furthermore the scout view id to swt view id mapping is done here. Ensure that each
 * swt view id you are mapping to a certain scout view id is defined in the plugin.xml
 * as a view extension. <br>
 * e.g.
 * 
 * <pre>
 *  public ISwtScoutSmartField createSmartField(Composite parent, ISmartField<?> model) {
 *    // create your own component
 *    ISwtScoutSmartField sf = ...
 *    return sf;
 *  }
 * </pre>
 */
public class SwtEnvironment extends AbstractSwtEnvironment {

  public static final String DEFAULT_STACK_VIEW_ID = "com.bsiag.crm.ui.swt.views.defaultStackView";

  public SwtEnvironment(Bundle bundle, String perspectiveId, Class<? extends AbstractClientSession> clientSessionClazz) {
    super(bundle, perspectiveId, clientSessionClazz);
    registerPart(IForm.VIEW_ID_CENTER, Activator.CENTER_VIEW_ID);
    registerPart(IForm.VIEW_ID_OUTLINE, Activator.OUTLINE_VIEW_ID);
    registerPart(IForm.VIEW_ID_PAGE_TABLE, Activator.TABLE_PAGE_VIEW_ID);
    registerPart(IForm.VIEW_ID_PAGE_SEARCH, Activator.SEAECH_VIEW_ID);
  }

  @Override
  public void showMessageBoxFromScout(IMessageBox messageBox) {
    if (Util.isMac()) {
      SwtMacScoutMessageBoxDialog box = new SwtMacScoutMessageBoxDialog(getParentShellIgnoringPopups(SWT.SYSTEM_MODAL | SWT.APPLICATION_MODAL | SWT.MODELESS), messageBox, this);
      box.open();
    }
    else {
      super.showMessageBoxFromScout(messageBox);
    }
  }

}
