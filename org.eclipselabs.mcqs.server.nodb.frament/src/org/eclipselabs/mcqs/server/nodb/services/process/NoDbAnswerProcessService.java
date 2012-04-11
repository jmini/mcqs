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
import org.eclipselabs.mcqs.shared.services.process.AnswerFormData;
import org.eclipselabs.mcqs.shared.services.process.IAnswerProcessService;

@Priority(100)
public class NoDbAnswerProcessService extends AbstractService implements IAnswerProcessService {

  @Override
  public AnswerFormData prepareCreate(AnswerFormData formData) throws ProcessingException {
    if (formData.getQuestionNr().getValue() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    DataStore.getInstance().updateQuestionText(formData.getQuestionNr().getValue(), formData.getQuestionText());
    return formData;
  }

  @Override
  public AnswerFormData create(AnswerFormData formData) throws ProcessingException {
    DataStore.getInstance().storeAnswer(formData);
    return formData;
  }

  @Override
  public AnswerFormData load(AnswerFormData formData) throws ProcessingException {
    if (formData.getAnswerNr() == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    AnswerFormData newFormData = DataStore.getInstance().getAnswer(formData.getAnswerNr());

    if (newFormData.getQuestionNr().getValue() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    DataStore.getInstance().updateQuestionText(newFormData.getQuestionNr().getValue(), newFormData.getQuestionText());
    return newFormData;
  }

  @Override
  public AnswerFormData store(AnswerFormData formData) throws ProcessingException {
    if (formData.getAnswerNr() == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    DataStore.getInstance().storeAnswer(formData);
    return formData;
  }

  @Override
  public void delete(Long answerNr) throws ProcessingException {
    if (answerNr == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    DataStore.getInstance().deleteAnswer(answerNr);
  }
}
