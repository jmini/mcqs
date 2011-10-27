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
package mcq.client;


import org.eclipse.scout.rt.client.ClientJob;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import mcq.client.ui.desktop.Desktop;
import org.eclipse.scout.rt.client.AbstractClientSession;
import org.eclipse.scout.rt.client.servicetunnel.http.HttpServiceTunnel;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.rt.shared.services.common.code.CODES;

public class ClientSession extends AbstractClientSession{
  private static IScoutLogger logger = ScoutLogManager.getLogger(ClientSession.class);

  public ClientSession(){
    super(true);
  }

  /**
   * @return session in current ThreadContext
   */
  public static ClientSession get(){
    return ClientJob.getCurrentSession(ClientSession.class);
  }

  @FormData
  public Long getPersonNr(){
    return getSharedContextVariable("personNr",Long.class);
  }

  @Override
  public void execLoadSession() throws ProcessingException{
    setServiceTunnel(new HttpServiceTunnel(this,getBundle().getBundleContext().getProperty("server.url")));

    //pre-load all known code types
    CODES.getAllCodeTypes(mcq.shared.Activator.PLUGIN_ID);

    setDesktop(new Desktop());

    // turn client notification polling on
    // getServiceTunnel().setClientNotificationPollInterval(2000L);
  }

  @Override
  public void execStoreSession() throws ProcessingException{
  }
}
