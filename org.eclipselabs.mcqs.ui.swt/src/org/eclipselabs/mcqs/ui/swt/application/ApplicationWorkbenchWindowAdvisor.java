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

import org.eclipse.scout.rt.ui.swt.basic.application.ApplicationActionBarAdvisor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipselabs.mcqs.ui.swt.Activator;

/**
 * <h3>ApplicationWorkbenchWindowAdvisor</h3> Used for workbench window behaviors.
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

  public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    super(configurer);
  }

  @Override
  public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
    return new ApplicationActionBarAdvisor(configurer, Activator.getDefault().getEnvironment());
  }

  @Override
  public void preWindowOpen() {
    IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    configurer.setInitialSize(new Point(1024, 768));
    configurer.setShowCoolBar(false);
    configurer.setShowStatusLine(true);
    configurer.setShowProgressIndicator(true);
    configurer.setShowMenuBar(true);
    configurer.setShowPerspectiveBar(false);
    configurer.setShowFastViewBars(false);
  }
}
