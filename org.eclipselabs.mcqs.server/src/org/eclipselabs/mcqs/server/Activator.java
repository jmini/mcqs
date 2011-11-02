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
package org.eclipselabs.mcqs.server;

import javax.security.auth.Subject;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.scout.rt.server.scheduler.Scheduler;
import org.eclipse.scout.rt.shared.services.common.security.SimplePrincipal;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {

  public static String PLUGIN_ID = "org.eclipselabs.mcqs.server";
  // The shared instance
  private static Activator plugin;

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  private Scheduler m_scheduler;
  /**
   * The subject used for backend activity, independent of any (client) user
   */
  private Subject m_subject;

  /**
   * The constructor
   */
  public Activator() {
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  @Override
  public void start(BundleContext context) throws Exception {
    super.start(context);
    plugin = this;
    //create the backend subject
    m_subject = new Subject();
    m_subject.getPrincipals().add(new SimplePrincipal("server"));
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  @Override
  public void stop(BundleContext context) throws Exception {
    if (m_scheduler != null) {
      m_scheduler.stop();
      m_scheduler = null;
    }
    plugin = null;
    super.stop(context);
  }

  public Subject getBackendSubject() {
    return m_subject;
  }

  public Scheduler getScheduler() {
    return m_scheduler;
  }

  public void setScheduler(Scheduler scheduler) {
    m_scheduler = scheduler;
  }

}