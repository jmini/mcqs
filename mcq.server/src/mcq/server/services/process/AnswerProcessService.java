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
package mcq.server.services.process;

import mcq.shared.Texts;
import mcq.shared.security.CreateAnswerPermission;
import mcq.shared.security.ReadAnswerPermission;
import mcq.shared.security.UpdateAnswerPermission;
import mcq.shared.services.process.AnswerFormData;
import mcq.shared.services.process.IAnswerProcessService;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;

public class AnswerProcessService extends AbstractService implements IAnswerProcessService {

  @Override
  public AnswerFormData prepareCreate(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateAnswerPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }

    SQL.selectInto(" select question_text " +
        " from  questions " +
        " where question_id = :QuestionNr " +
        " into  :questionText", formData);

    return formData;
  }

  @Override
  public AnswerFormData create(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateAnswerPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }
    //TODO [jebr] business logic here.
    return formData;
  }

  @Override
  public AnswerFormData load(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadAnswerPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }
    //TODO [jebr] business logic here
    return formData;
  }

  @Override
  public AnswerFormData store(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateAnswerPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }
    //TODO [jebr] business logic here
    return formData;
  }
}
