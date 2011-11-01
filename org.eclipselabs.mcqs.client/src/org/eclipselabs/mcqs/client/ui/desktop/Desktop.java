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
import org.eclipse.scout.rt.client.ui.desktop.bookmark.menu.AbstractBookmarkMenu;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipselabs.mcqs.client.ClientSession;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm;
import org.eclipselabs.mcqs.shared.Texts;

public class Desktop extends AbstractDesktop implements IDesktop{
  private static IScoutLogger logger = ScoutLogManager.getLogger(Desktop.class);

  public Desktop(){
  }

  @Override
  public String getConfiguredTitle(){
    return Texts.get("ApplicationTitle");
  }



  @Override
  protected void execOpened() throws ProcessingException {
    // dektop form
    QuestionsListForm desktopForm = new QuestionsListForm();
    desktopForm.startDisplay();
  }



  @Order(10.0)
  public class FileMenu extends AbstractMenu{

    @Override
    public String getConfiguredText(){
      return Texts.get("FileMenu");
    }

    @Order(100.0)
    public class ExitMenu extends AbstractMenu{

      @Override
      public String getConfiguredText(){
        return Texts.get("ExitMenu");
      }

      @Override
      public void execAction() throws ProcessingException{
        ClientSyncJob.getCurrentSession(ClientSession.class).stopSession();
      }
    }
  }

  @Order(20.0)
  public class ToolsMenu extends AbstractMenu{

    @Override
    public String getConfiguredText(){
      return Texts.get("ToolsMenu");
    }
  }

  @Order(25)
  public class BookmarkMenu extends AbstractBookmarkMenu{
    public BookmarkMenu(){
      super(Desktop.this);
    }
  }

  @Order(30.0)
  public class HelpMenu extends AbstractMenu{

    @Override
    public String getConfiguredText(){
      return Texts.get("HelpMenu");
    }

    @Order(10.0)
    public class AboutMenu extends AbstractMenu{

      @Override
      public String getConfiguredText(){
        return Texts.get("AboutMenu");
      }

      @Override
      public void execAction() throws ProcessingException{
        ScoutInfoForm form=new ScoutInfoForm();
        form.startModify();
      }
    }

  }

}
