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
package mcq.shared;

import org.eclipse.scout.rt.shared.ScoutTexts;

/**
 * This class provides the nls support.
 * Do not change any member nor field of this class anytime otherwise the
 * nls support is not anymore garanteed.
 * This class is auto generated and is maintained by the plugins
 * translations.nls file in the root directory of the plugin.
* @see translations.nls
 */

public class Texts extends ScoutTexts{

	public static final String RESOURCE_BUNDLE_NAME = "resources.texts.Texts";//$NON-NLS-1$
	private static Texts instance=new Texts();

	public static Texts getInstance(){
	  return instance;
	}

	public static String get(String key,String ... messageArguments){
	  return getInstance().getText(key,messageArguments);
	}

	protected Texts(){
	  registerResourceBundle(RESOURCE_BUNDLE_NAME,Texts.class);
	}
}
