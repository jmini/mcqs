/*******************************************************************************
 * Copyright 2012 Jeremie Bresson
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
package org.eclipselabs.mcqs.client.test;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Hashtable;

import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.osgi.framework.internal.core.Constants;
import org.eclipse.scout.rt.testing.shared.ScoutJUnitPluginTestExecutor;
import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipse.scout.testing.client.servicetunnel.http.MultiClientAuthenticator;
import org.eclipse.scout.testing.client.servicetunnel.http.MultiClientSessionCookieStore;
import org.eclipselabs.mcqs.client.Activator;
import org.eclipselabs.mcqs.client.ClientSession;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class McqsClientTestApplication implements IApplication {

  @Override
  public Object start(IApplicationContext context) throws Exception {
    intallNetAuthenticator();
    installCookieStore();
    ScoutClientTestRunner.setDefaultClientSessionClass(ClientSession.class);
    return new ScoutJUnitPluginTestExecutor().runAllTests();
  }

  /**
   * Installs an authenticator that is aware of different client sessions connecting concurrently to the same URL. The
   * authenticator is registered as an OSGi service and used by Scout's network support which is based on Eclipse's
   * networking features.
   * 
   * @return Returns the authenticator's OSGi service registration.
   */
  private Object intallNetAuthenticator() {
    BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();
    ServiceRegistration reg = null;
    Hashtable<String, Object> map = new Hashtable<String, Object>();
    map.put(Constants.SERVICE_RANKING, 1);

    // add credentials of test users
    MultiClientAuthenticator.addUser("admin", "admin");
    MultiClientAuthenticator.addUser("standard", "standard");

    MultiClientAuthenticator.setDefaultUser("admin");

    reg = bundleContext.registerService(java.net.Authenticator.class.getName(), new MultiClientAuthenticator(), map);

    // start the netBundle, it is not started from OSGI because no references exists
    Bundle netBundle = Platform.getBundle("org.eclipse.scout.net");
    if (netBundle != null) {
      try {
        netBundle.start();
      }
      catch (Throwable t) {
        netBundle = null;
      }
    }
    return reg;
  }

  /**
   * Installs a cookie store that is aware of different client sessions connecting concurrently to the same URL.
   */
  private void installCookieStore() {
    CookieManager.setDefault(new CookieManager(new MultiClientSessionCookieStore(), CookiePolicy.ACCEPT_ALL));
  }

  @Override
  public void stop() {
  }
}