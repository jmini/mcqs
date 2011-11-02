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

import org.eclipse.scout.rt.ui.swt.AbstractSwtStartup;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;

/** <h3>CenterView</h3>
 *  The Startup class is registered as a view extension point in the plugin.xml.
 *  It is used to get aware that the Workbench is ready.
* @see AbstractSwtStartup
 */
public class SwtStartup extends AbstractSwtStartup{

  @Override
  protected ISwtEnvironment getSwtEnvironment(){
    return Activator.getDefault().getEnvironment();
  }
}