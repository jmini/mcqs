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
package org.eclipselabs.mcqs.server.nodb.services.process;

import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.server.nodb.DataStore;
import org.eclipselabs.mcqs.shared.services.process.IQuestionProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;

@Priority(100)
public class NoDbQuestionProcessService extends AbstractService implements IQuestionProcessService {

  @Override
  public QuestionFormData prepareCreate(QuestionFormData formData) throws ProcessingException {
    return formData;
  }

  @Override
  public QuestionFormData create(QuestionFormData formData) throws ProcessingException {
    DataStore.getInstance().storeQuestion(formData);
    return formData;
  }

  @Override
  public QuestionFormData load(QuestionFormData formData) throws ProcessingException {
    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can not be null");
    }
    return DataStore.getInstance().getQuestion(formData.getQuestionNr());
  }

  @Override
  public QuestionFormData store(QuestionFormData formData) throws ProcessingException {
    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can not be null");
    }

    DataStore.getInstance().storeQuestion(formData);
    return formData;
  }

  @Override
  public void delete(Integer questionNr) throws ProcessingException {
    if (questionNr == null) {
      throw new ProcessingException("QuestionNr can not be null");
    }

    DataStore.getInstance().deleteQuestion(questionNr);
  }
}
