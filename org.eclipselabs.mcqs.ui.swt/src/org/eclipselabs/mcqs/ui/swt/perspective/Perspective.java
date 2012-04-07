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
package org.eclipselabs.mcqs.ui.swt.perspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;
import org.eclipselabs.mcqs.ui.swt.Activator;

/**
 * <h3>Activator</h3> ...
 */
public class Perspective implements IPerspectiveFactory {

  @Override
  public void createInitialLayout(IPageLayout layout) {

    layout.setEditorAreaVisible(false);

    layout.setFixed(false);

    layout.addStandaloneViewPlaceholder(Activator.OUTLINE_VIEW_ID, IPageLayout.LEFT, 0.2f, IPageLayout.ID_EDITOR_AREA, true);

    String folderId = "org.eclipselabs.mcqs.ui.swt.viewStack";
    IFolderLayout folderLayout = layout.createFolder(folderId, IPageLayout.RIGHT, 0.3f, Activator.TABLE_PAGE_VIEW_ID);
    folderLayout.addPlaceholder(Activator.TABLE_PAGE_VIEW_ID);
    folderLayout.addPlaceholder(Activator.CENTER_VIEW_ID);

    layout.addStandaloneViewPlaceholder(Activator.SEAECH_VIEW_ID, IPageLayout.BOTTOM, 0.7f, folderId, true);
    IViewLayout outlineSelectorLayout = layout.getViewLayout(Activator.OUTLINE_VIEW_ID);
    outlineSelectorLayout.setCloseable(false);
    outlineSelectorLayout.setMoveable(false);

    IViewLayout tablePageLayout = layout.getViewLayout(Activator.TABLE_PAGE_VIEW_ID);
    tablePageLayout.setCloseable(false);
    tablePageLayout.setMoveable(false);

    IViewLayout searchLayout = layout.getViewLayout(Activator.SEAECH_VIEW_ID);
    searchLayout.setCloseable(false);
    searchLayout.setMoveable(false);
  }

}
