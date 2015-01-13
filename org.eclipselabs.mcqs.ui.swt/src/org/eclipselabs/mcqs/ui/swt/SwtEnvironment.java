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
package org.eclipselabs.mcqs.ui.swt;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.ui.swt.AbstractSwtEnvironment;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironmentListener;
import org.eclipse.scout.rt.ui.swt.SwtEnvironmentEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipselabs.mcqs.ui.swt.editor.ScoutEditorPart;
import org.eclipselabs.mcqs.ui.swt.views.CenterView;
import org.eclipselabs.mcqs.ui.swt.views.EastView;
import org.eclipselabs.mcqs.ui.swt.views.NorthEastView;
import org.eclipselabs.mcqs.ui.swt.views.NorthView;
import org.eclipselabs.mcqs.ui.swt.views.NorthWestView;
import org.eclipselabs.mcqs.ui.swt.views.SouthEastView;
import org.eclipselabs.mcqs.ui.swt.views.SouthView;
import org.eclipselabs.mcqs.ui.swt.views.SouthWestView;
import org.eclipselabs.mcqs.ui.swt.views.WestView;
import org.osgi.framework.Bundle;

public class SwtEnvironment extends AbstractSwtEnvironment {

  public SwtEnvironment(Bundle bundle, String perspectiveId, Class<? extends AbstractClientSession> clientSessionClazz) {
    super(bundle, perspectiveId, clientSessionClazz);

    registerPart(IForm.VIEW_ID_OUTLINE, NorthWestView.class.getName());
    registerPart(IForm.VIEW_ID_OUTLINE_SELECTOR, SouthWestView.class.getName());
    registerPart(IForm.VIEW_ID_CENTER, CenterView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_TABLE, CenterView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_DETAIL, NorthView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_SEARCH, SouthView.class.getName());
    registerPart(IForm.VIEW_ID_N, NorthView.class.getName());
    registerPart(IForm.VIEW_ID_NW, NorthWestView.class.getName());
    registerPart(IForm.VIEW_ID_W, WestView.class.getName());
    registerPart(IForm.VIEW_ID_SW, SouthWestView.class.getName());
    registerPart(IForm.VIEW_ID_S, SouthView.class.getName());
    registerPart(IForm.VIEW_ID_SE, SouthEastView.class.getName());
    registerPart(IForm.VIEW_ID_E, EastView.class.getName());
    registerPart(IForm.VIEW_ID_NE, NorthEastView.class.getName());

    registerPart(IForm.EDITOR_ID, ScoutEditorPart.class.getName());

    addEnvironmentListener(new ISwtEnvironmentListener() {
      @Override
      public void environmentChanged(SwtEnvironmentEvent e) {
        if (e.getType() == SwtEnvironmentEvent.STOPPED) {
          if (!PlatformUI.getWorkbench().isClosing()) {
            PlatformUI.getWorkbench().close();
          }
        }
      }
    });

  }
}
