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

import java.util.Collection;

import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.server.nodb.DataStore;
import org.eclipselabs.mcqs.shared.services.process.IQuestionsListProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionsListFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionsListFormData.Questions;

@Priority(100)
public class NoDbQuestionsListProcessService extends AbstractService implements IQuestionsListProcessService {

  @Override
  public QuestionsListFormData load(QuestionsListFormData formData) throws ProcessingException {
    Collection<QuestionFormData> questions = DataStore.getInstance().getQuestions();
    Questions questionsTable = formData.getQuestions();
    questionsTable.clearRows();
    for (QuestionFormData question : questions) {
      int r = questionsTable.addRow();
      questionsTable.setQuestionNr(r, question.getQuestionNr());
      questionsTable.setQuestion(r, question.getQuestionText().getValue());
    }
    return formData;
  }
}
