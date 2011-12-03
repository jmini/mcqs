/*******************************************************************************
 * Copyright (c) 2010 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipselabs.mcqs.ui.swt.application;

import org.eclipse.jface.action.Action;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipselabs.mcqs.ui.swt.Activator;

/**
 * 
 */
public class SwtScoutAction extends Action {

  private final Class<? extends AbstractMenu> m_menuClass;

  /**
   * 
   */
  public SwtScoutAction(Class<? extends AbstractMenu> menuClass) {
    m_menuClass = menuClass;
    setId(menuClass.getCanonicalName());
  }

  @Override
  public void run() {
    ISwtEnvironment env = Activator.getDefault().getEnvironment();
    final IDesktop desktop = env.getScoutDesktop();
    Runnable t = new Runnable() {
      @Override
      public void run() {
        AbstractMenu scoutMenu = desktop.getMenu(m_menuClass);
        try {
          scoutMenu.doAction();
        }
        catch (ProcessingException e) {
          //TODO: Log this stacktrace.
          e.printStackTrace();
        }
      }
    };
    env.invokeScoutLater(t, 0);
  }

}
