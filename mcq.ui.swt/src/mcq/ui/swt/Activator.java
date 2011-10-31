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
package mcq.ui.swt;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import mcq.client.ClientSession;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;

/** <h3>Activator</h3>
 *  All view ids and perspective ids are kept here.
*/
public class Activator implements BundleActivator{

  // the plugin id
  public static final String BUNDLE_ID = "mcq.ui.swt";
  // the initial perspective id
  public static final String PERSPECITVE_ID = "mcq.ui.swt.perspective.Perspective";
  // all view ids comodity to access.
  public static final String CENTER_VIEW_ID = "mcq.ui.swt.views.CenterView";
  public static final String TABLE_PAGE_VIEW_ID = "mcq.ui.swt.views.TablePageView";
  public static final String OUTLINE_VIEW_ID = "mcq.ui.swt.views.OutlinePageView";
  public static final String SEAECH_VIEW_ID = "mcq.ui.swt.views.SearchView";


  private ISwtEnvironment m_environment;
  // the shared instance
  private static Activator m_bundle;

  public void start(BundleContext context) throws Exception {
    m_bundle = this;
    m_environment = new SwtEnvironment(context.getBundle(), PERSPECITVE_ID, ClientSession.class);
  }

  public void stop(BundleContext context) throws Exception {
    m_bundle = null;
  }

  public static Activator getDefault() {
    return m_bundle;
  }

  public ISwtEnvironment getEnvironment() {
    return m_environment;
  }
}

