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
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.shared.Texts;
import org.eclipselabs.mcqs.shared.security.ReadAnswersListPermission;
import org.eclipselabs.mcqs.shared.services.process.AnswersListFormData;
import org.eclipselabs.mcqs.shared.services.process.IAnswersListProcessService;

public class AnswersListProcessService extends AbstractService implements IAnswersListProcessService {

  @Override
  public AnswersListFormData load(AnswersListFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadAnswersListPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }

    if (!formData.getQuestionNr().isValueSet()) {
      throw new ProcessingException("QuestionId can no be null");
    }

    SQL.selectInto(" select question_text " +
        " from  questions " +
        " where question_id = :questionNr " +
        " into  :questionText", formData);

    SQL.selectInto(" select answer_id, name " +
        " from  answers " +
        " where question_id = :questionNr " +
        " into  :answerNr, :name", formData.getAnswers(), formData);

    formData = loadStatistics(formData);
    return formData;
  }

  @Override
  public AnswersListFormData loadStatistics(AnswersListFormData formData) throws ProcessingException {
    SQL.selectInto("  select c.choice_text, (select count(*) from answers_choices ac where ac.choice_id = c.choice_id) " +
        "  from  choices c " +
        "  where c.question_id = :questionNr " +
        "  into  :choice, :result",
        formData.getStatistics(), formData);

    double nbAnswers = formData.getAnswers().getRowCount();
    if (nbAnswers > 0) {
      for (int i = 0; i < formData.getStatistics().getRowCount(); i++) {
        double result = (formData.getStatistics().getResult(i).doubleValue() / nbAnswers);
        formData.getStatistics().setResult(i, result);
      }
    }

    return formData;
  }
}
