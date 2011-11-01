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
package org.eclipselabs.mcqs.ui.swing;


import java.security.PrivilegedExceptionAction;
import javax.security.auth.Subject;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.net.NetActivator;
import org.eclipse.scout.rt.client.IClientSession;
import org.eclipse.scout.rt.client.services.common.session.IClientSessionRegistryService;
import org.eclipse.scout.rt.shared.services.common.security.SimplePrincipal;
import org.eclipse.scout.rt.ui.swing.AbstractSwingApplication;
import org.eclipse.scout.rt.ui.swing.ISwingEnvironment;
import org.eclipse.scout.service.SERVICES;
import org.eclipselabs.mcqs.client.ClientSession;


public class SwingApplication extends AbstractSwingApplication{
  private static IScoutLogger logger = ScoutLogManager.getLogger(SwingApplication.class);

  @Override
  public Object start(final IApplicationContext context) throws Exception{
    Subject subject=new Subject();
    subject.getPrincipals().add(new SimplePrincipal(System.getProperty("user.name")));
    return Subject.doAs(subject, new PrivilegedExceptionAction<Object>(){
      public Object run() throws Exception{
        return startSecure(context);
      }
    });
  }

  @Override
  protected ISwingEnvironment createSwingEnvironment(){
    return new SwingEnvironment();
  }

  private Object startSecure(IApplicationContext context) throws Exception{
    try{
      NetActivator.install();
    }
    catch(Throwable t){
      // no net handler found
      logger.warn("NetActivator is not available", t);
    }
    return super.start(context);
  }

  @Override
  protected IClientSession getClientSession() {
    return SERVICES.getService(IClientSessionRegistryService.class).getClientSession(ClientSession.class);
  }
}
