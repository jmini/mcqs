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
package org.eclipselabs.mcqs.ui.swt.application;

import java.security.PrivilegedExceptionAction;

import javax.security.auth.Subject;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.security.SimplePrincipal;
import org.eclipse.scout.net.NetActivator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * <h3>Activator</h3> This class controls all aspects of the application's execution
 */
public class Application implements IApplication {
  private Display m_display;

  @Override
  public Object start(final IApplicationContext context) throws Exception {
    m_display = getApplicationDisplay();
    try {
      Subject subject = new Subject();
      subject.getPrincipals().add(new SimplePrincipal(System.getProperty("user.name")));
      return Subject.doAs(subject, new PrivilegedExceptionAction<Object>() {
        @Override
        public Object run() throws Exception {
          return startSecure(context);
        }
      });
    }
    finally {
      if (m_display != null) {
        m_display.dispose();
      }
    }
  }

  public Integer startSecure(final IApplicationContext context) throws Exception {
    Display display = PlatformUI.createDisplay();
    NetActivator.install();
    if (PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor()) == PlatformUI.RETURN_RESTART) {
      return EXIT_RESTART;
    }
    return EXIT_OK;
  }

  @Override
  public void stop() {
    final IWorkbench workbench = PlatformUI.getWorkbench();
    if (workbench == null) return;
    final Display display = workbench.getDisplay();
    display.syncExec(new Runnable() {
      @Override
      public void run() {
        if (!display.isDisposed())
          workbench.close();
      }
    });
  }

  public Display getApplicationDisplay() {
    if (m_display == null) {
      m_display = Display.getDefault();
    }
    return m_display;
  }
}
