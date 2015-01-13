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
package org.eclipselabs.mcqs.ui.swt.application.menu;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.action.menu.MenuUtility;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.action.menu.SwtScoutMenuContributionItem;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipselabs.mcqs.ui.swt.Activator;

public class DesktopMenuBar extends CompoundContributionItem {

  @Override
  protected IContributionItem[] getContributionItems() {
    ISwtEnvironment env = Activator.getDefault().getEnvironment();
    if (env != null && env.isInitialized()) {
      if (env.getClientSession() != null && env.getClientSession().getDesktop() != null) {
        List<IMenu> menus = env.getClientSession().getDesktop().getMenus();
        List<IMenu> consolidatedMenus = MenuUtility.consolidateMenus(menus);
        List<IContributionItem> swtContributionItems = new ArrayList<IContributionItem>();
        for (IMenu menu : consolidatedMenus) {
          swtContributionItems.add(new SwtScoutMenuContributionItem(menu, env));
        }
        return swtContributionItems.toArray(new IContributionItem[swtContributionItems.size()]);
      }
    }
    return new IContributionItem[0];
  }

  @Override
  public boolean isDirty() {
    boolean isDirty = super.isDirty();
    if (!isDirty && getParent() instanceof IMenuManager) {
      isDirty = ((IMenuManager) getParent()).isDirty();
    }
    return isDirty;
  }
}
