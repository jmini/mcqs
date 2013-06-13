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

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.IViewLayout;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipselabs.mcqs.ui.swt.views.CenterView;
import org.eclipselabs.mcqs.ui.swt.views.EastView;
import org.eclipselabs.mcqs.ui.swt.views.NorthEastView;
import org.eclipselabs.mcqs.ui.swt.views.NorthView;
import org.eclipselabs.mcqs.ui.swt.views.NorthWestView;
import org.eclipselabs.mcqs.ui.swt.views.SouthEastView;
import org.eclipselabs.mcqs.ui.swt.views.SouthView;
import org.eclipselabs.mcqs.ui.swt.views.SouthWestView;
import org.eclipselabs.mcqs.ui.swt.views.WestView;

public class Perspective implements IPerspectiveFactory {

  public static final String ID = Perspective.class.getName();
  public static final String FOLDER_CENTER = ID + ".folders.center";
  public static final String FOLDER_NORTH = ID + ".folders.north";
  public static final String FOLDER_NORTH_EAST = ID + ".folders.northeast";
  public static final String FOLDER_EAST = ID + ".folders.east";
  public static final String FOLDER_SOUTH_EAST = ID + ".folders.southeast";
  public static final String FOLDER_SOUTH = ID + ".folders.south";
  public static final String FOLDER_SOUTH_WEST = ID + ".folders.southwest";
  public static final String FOLDER_WEST = ID + ".folders.west";
  public static final String FOLDER_NORTH_WEST = ID + ".folders.northwest";

  private static final String ALL_SECONDARY_VIEW_IDS = ":*";

  @Override
  public void createInitialLayout(IPageLayout layout) {
    layout.setEditorAreaVisible(false);

    IPlaceholderFolderLayout folder = layout.createPlaceholderFolder(FOLDER_WEST, IPageLayout.LEFT, 0.2f, IPageLayout.ID_EDITOR_AREA);
    folder.addPlaceholder(WestView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    // create a folder instead of a placeholder to ensure the space of the center view is always visible.
    folder = layout.createFolder(FOLDER_CENTER, IPageLayout.RIGHT, 0.6f, IPageLayout.ID_EDITOR_AREA);
    folder.addPlaceholder(CenterView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_EAST, IPageLayout.RIGHT, 0.6f, FOLDER_CENTER);
    folder.addPlaceholder(EastView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_NORTH, IPageLayout.TOP, 0.2f, FOLDER_CENTER);
    folder.addPlaceholder(NorthView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_NORTH_WEST, IPageLayout.TOP, 0.2f, FOLDER_WEST);
    folder.addPlaceholder(NorthWestView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_NORTH_EAST, IPageLayout.TOP, 0.2f, FOLDER_EAST);
    folder.addPlaceholder(NorthEastView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_SOUTH, IPageLayout.BOTTOM, 0.6f, FOLDER_CENTER);
    folder.addPlaceholder(SouthView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_SOUTH_WEST, IPageLayout.BOTTOM, 0.6f, FOLDER_WEST);
    folder.addPlaceholder(SouthWestView.class.getName() + ALL_SECONDARY_VIEW_IDS);

    folder = layout.createPlaceholderFolder(FOLDER_SOUTH_EAST, IPageLayout.BOTTOM, 0.6f, FOLDER_EAST);
    folder.addPlaceholder(SouthEastView.class.getName() + ALL_SECONDARY_VIEW_IDS);
    folder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);

    IViewLayout outlineLayout = layout.getViewLayout(NorthWestView.class.getName());
    if (outlineLayout != null) {
      outlineLayout.setCloseable(false);
      outlineLayout.setMoveable(false);
    }
  }
}
