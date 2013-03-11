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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.ui.swt.AbstractSwtEnvironment;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironmentListener;
import org.eclipse.scout.rt.ui.swt.SwtEnvironmentEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipselabs.mcqs.ui.swt.editor.ScoutEditorPart;
import org.eclipselabs.mcqs.ui.swt.views.CenterView;
import org.eclipselabs.mcqs.ui.swt.views.DetailView;
import org.eclipselabs.mcqs.ui.swt.views.EastView;
import org.eclipselabs.mcqs.ui.swt.views.OutlineView;
import org.eclipselabs.mcqs.ui.swt.views.SearchView;
import org.eclipselabs.mcqs.ui.swt.views.TableView;
import org.osgi.framework.Bundle;

public class SwtEnvironment extends AbstractSwtEnvironment {

  public SwtEnvironment(Bundle bundle, String perspectiveId, Class<? extends AbstractClientSession> clientSessionClazz) {
    super(bundle, perspectiveId, clientSessionClazz);

    registerPart(IForm.VIEW_ID_OUTLINE, OutlineView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_DETAIL, DetailView.class.getName());
    registerPart(IForm.VIEW_ID_CENTER, CenterView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_TABLE, TableView.class.getName());
    registerPart(IForm.VIEW_ID_PAGE_SEARCH, SearchView.class.getName());
    registerPart(IForm.VIEW_ID_E, EastView.class.getName());
    registerPart(IForm.EDITOR_ID, ScoutEditorPart.class.getName());

    addEnvironmentListener(new ISwtEnvironmentListener() {
      @Override
      public void environmentChanged(SwtEnvironmentEvent e) {
        if (e.getType() == SwtEnvironmentEvent.STOPPED) {
          PlatformUI.getWorkbench().close();
        }
      }
    });
    addEnvironmentListener(new ISwtEnvironmentListener() {
      @Override
      public void environmentChanged(SwtEnvironmentEvent e) {
        if (e.getType() == SwtEnvironmentEvent.STARTED) {
          removeEnvironmentListener(this);
          IDesktop d = getClientSession().getDesktop();
          if (d != null) {
            setWindowTitle(d.getTitle());
            d.addPropertyChangeListener(IDesktop.PROP_TITLE, new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                setWindowTitle((String) evt.getNewValue());
              }
            });
          }
        }
      }
    });
  }

  private void setWindowTitle(final String title) {
    for (IWorkbenchWindow w : PlatformUI.getWorkbench().getWorkbenchWindows()) {
      final Shell s = w.getShell();
      if (!s.isDisposed()) {
        s.getDisplay().asyncExec(new Runnable() {
          @Override
          public void run() {
            s.setText(title);
          }
        });
      }
    }
  }
}
