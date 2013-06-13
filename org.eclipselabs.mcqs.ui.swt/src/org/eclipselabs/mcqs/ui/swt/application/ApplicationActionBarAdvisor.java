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

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ui.action.tool.IToolButton;
import org.eclipse.scout.rt.client.ui.action.view.IViewButton;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipselabs.mcqs.ui.swt.Activator;
import org.eclipselabs.mcqs.ui.swt.SwtEnvironment;
import org.eclipselabs.mcqs.ui.swt.application.button.CoolbarButton;

/**
 * <h3>ApplicationActionBarAdvisor</h3> Used for menu contributions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

  private static IScoutLogger logger = ScoutLogManager.getLogger(ApplicationActionBarAdvisor.class);

  final static int NUM_OUTLINE_BUTTONS = 0;
  private CoolbarButton[] m_coolbarButton = new CoolbarButton[NUM_OUTLINE_BUTTONS];

  public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
    super(configurer);
    ((SwtEnvironment) Activator.getDefault().getEnvironment()).setAdvisor(this);
  }

  @Override
  protected void fillMenuBar(IMenuManager menuBar) {
    menuBar.add(new MenuManager("", IWorkbenchActionConstants.M_FILE));
  }

  @Override
  protected void makeActions(IWorkbenchWindow window) {
    for (int i = 0; i < NUM_OUTLINE_BUTTONS; i++) {
      m_coolbarButton[i] = new CoolbarButton();
    }
  }

  public void initViewButtons(IDesktop d) {
    IViewButton[] viewButtons = d.getViewButtons();
    int start = 0;
    int end = Math.min(m_coolbarButton.length, viewButtons.length);
    for (int i = start; i < end; i++) {
      CoolbarButton b = m_coolbarButton[i];
      IViewButton v = viewButtons[i];

      b.setEnabled(v.isEnabled() && v.isEnabledGranted());
      if (v.isVisible() && v.isVisibleGranted()) {
        b.init(v);
        register(b);
      }
      else {
        b.setEnabled(false);
      }
    }

    IToolButton[] toolButtons = d.getToolButtons();
    start = end + 1;
    end = Math.min(m_coolbarButton.length, start + toolButtons.length);
    for (int i = start; i < end; i++) {
      CoolbarButton b = m_coolbarButton[i];
      IToolButton v = toolButtons[i - start];

      b.setEnabled(v.isEnabled() && v.isEnabledGranted());
      if (v.isVisible() && v.isVisibleGranted()) {
        b.init(v);
        register(b);
      }
      else {
        b.setEnabled(false);
      }
    }

    if (viewButtons.length + toolButtons.length > NUM_OUTLINE_BUTTONS) {
      logger.warn("There are more buttons configured in the desktop model than prepared in the SWT UI. Consider to increase 'NUM_OUTLINE_BUTTONS' in class '" + getClass().getName() + "'.");
    }
  }

  @Override
  protected void fillCoolBar(ICoolBarManager coolBar) {
    IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
    coolBar.add(new ToolBarContributionItem(toolbar, "main"));
    for (CoolbarButton a : m_coolbarButton) {
      toolbar.add(a);
    }
  }
}
