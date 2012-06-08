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
package org.eclipselabs.mcqs.client.ui.desktop;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.ClientSyncJob;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipselabs.mcqs.client.ClientSession;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm;
import org.eclipselabs.mcqs.shared.Icons;

public class Desktop extends AbstractDesktop implements IDesktop {

  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);

  public Desktop() {
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ApplicationTitle");
  }

  @Override
  protected void execOpened() throws ProcessingException {
    // dektop form
    QuestionsListForm desktopForm = new QuestionsListForm();
    desktopForm.setIconId(Icons.McqsSmall);
    desktopForm.startDisplay();
  }

  @Order(10.0)
  public class FileMenu extends AbstractMenu {

    @Override
    public String getConfiguredText() {
      return TEXTS.get("FileMenu");
    }

    @Order(10.0)
    public class AboutMenu extends AbstractMenu {

      @Override
      public String getConfiguredText() {
        return TEXTS.get("AboutMenu");
      }

      @Override
      public void execAction() throws ProcessingException {
        ScoutInfoForm form = new ScoutInfoForm();
        form.startModify();
      }
    }

    @Order(20.0)
    public class SeparatorMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredSeparator() {
        return true;
      }
    }

    @Order(100.0)
    public class ExitMenu extends AbstractMenu {

      @Override
      public String getConfiguredText() {
        return TEXTS.get("ExitMenu");
      }

      @Override
      public void execAction() throws ProcessingException {
        ClientSyncJob.getCurrentSession(ClientSession.class).stopSession();
      }
    }
  }
}
