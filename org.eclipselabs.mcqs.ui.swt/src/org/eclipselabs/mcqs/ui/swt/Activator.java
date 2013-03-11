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
package org.eclipselabs.mcqs.ui.swt;

import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipselabs.mcqs.client.ClientSession;
import org.eclipselabs.mcqs.ui.swt.perspective.Perspective;
import org.eclipselabs.mcqs.ui.swt.views.CenterView;
import org.eclipselabs.mcqs.ui.swt.views.DetailView;
import org.eclipselabs.mcqs.ui.swt.views.EastView;
import org.eclipselabs.mcqs.ui.swt.views.OutlineView;
import org.eclipselabs.mcqs.ui.swt.views.SearchView;
import org.eclipselabs.mcqs.ui.swt.views.TableView;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  // the plugin id
  public static final String BUNDLE_ID = "org.eclipselabs.mcqs.ui.swt";

  // all view ID's commodity to access.
  public static final String CENTER_VIEW_ID = CenterView.class.getName();
  public static final String DETAIL_VIEW_ID = DetailView.class.getName();
  public static final String EAST_VIEW_ID = EastView.class.getName();
  public static final String OUTLINE_VIEW_ID = OutlineView.class.getName();
  public static final String TABLE_VIEW_ID = TableView.class.getName();
  public static final String SEARCH_VIEW_ID = SearchView.class.getName();

  private ISwtEnvironment m_environment;

  // the shared instance
  private static Activator m_bundle;

  @Override
  public void start(BundleContext context) throws Exception {
    m_bundle = this;
    m_environment = new SwtEnvironment(context.getBundle(), Perspective.ID, ClientSession.class);
  }

  @Override
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
