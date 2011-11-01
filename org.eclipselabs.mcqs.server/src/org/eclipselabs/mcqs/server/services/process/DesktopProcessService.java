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
package org.eclipselabs.mcqs.server.services.process;


import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.shared.services.process.DesktopFormData;
import org.eclipselabs.mcqs.shared.services.process.IDesktopProcessService;

public class DesktopProcessService extends AbstractService implements IDesktopProcessService {

  @Override
  public DesktopFormData load(DesktopFormData formData) throws ProcessingException {
    SQL.selectInto(" select question_id, question_text " +
        " from  questions " +
        " into :QuestionNr, :Question",
        formData.getQuestions());
    return formData;
  }
}
