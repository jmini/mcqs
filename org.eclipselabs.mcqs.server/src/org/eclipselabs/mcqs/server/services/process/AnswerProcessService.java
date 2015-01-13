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

import org.eclipse.scout.commons.BooleanUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.exception.VetoException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.shared.security.CreateAnswerPermission;
import org.eclipselabs.mcqs.shared.security.DeleteAnswerPermission;
import org.eclipselabs.mcqs.shared.security.ReadAnswerPermission;
import org.eclipselabs.mcqs.shared.security.UpdateAnswerPermission;
import org.eclipselabs.mcqs.shared.services.process.AnswerFormData;
import org.eclipselabs.mcqs.shared.services.process.IAnswerProcessService;

public class AnswerProcessService extends AbstractService implements IAnswerProcessService {

  @Override
  public AnswerFormData prepareCreate(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateAnswerPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (formData.getQuestionNr().getValue() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    loadQuestion(formData);

    return formData;
  }

  @Override
  public AnswerFormData create(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateAnswerPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.insert(" insert into answers (name, question_id) " +
        " values (:YourName, :QuestionNr) ", formData);

    SQL.selectInto(" values IDENTITY_VAL_LOCAL() " +
        " into  :AnswerNr", formData);

    if (formData.getAnswerNr() == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    storeAnswerChoice(formData);
    return formData;
  }

  @Override
  public AnswerFormData load(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadAnswerPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (formData.getAnswerNr() == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    SQL.selectInto(" select question_id, name " +
        " from answers " +
        " where answer_id = :AnswerNr " +
        " into  :QuestionNr, :YourName", formData);

    if (formData.getQuestionNr().getValue() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    loadQuestion(formData);
    if (BooleanUtility.nvl(formData.getMultipleChoices())) {
      SQL.selectInto(" select choice_id " +
          " from  answers_choices " +
          " where answer_id = :AnswerNr " +
          " into  :Choices", formData);
    }
    else {
      SQL.selectInto(" select choice_id " +
          " from  answers_choices " +
          " where answer_id = :AnswerNr " +
          " into  :Choice", formData);
    }

    return formData;
  }

  @Override
  public AnswerFormData store(AnswerFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateAnswerPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (formData.getAnswerNr() == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    SQL.update("update answers set name = :YourName where answer_id = :AnswerNr", formData);

    SQL.delete("delete from answers_choices where answer_id = :AnswerNr", formData);

    storeAnswerChoice(formData);

    return formData;
  }

  /**
   * @param formData
   * @throws ProcessingException
   */
  private void loadQuestion(AnswerFormData formData) throws ProcessingException {
    SQL.selectInto(" select question_text, multiple_choices " +
        " from  questions " +
        " where question_id = :QuestionNr " +
        " into  :QuestionText, :MultipleChoices", formData);
  }

  /**
   * @param formData
   * @throws ProcessingException
   */
  private void storeAnswerChoice(AnswerFormData formData) throws ProcessingException {
    if (BooleanUtility.nvl(formData.getMultipleChoices())) {
      if (formData.getChoices().isValueSet() && formData.getChoices().getValue() != null) {
        for (Long choiceId : formData.getChoices().getValue()) {
          SQL.insert(" insert into answers_choices (answer_id, choice_id) " +
              " values (:AnswerNr, :ChoiceId) ", formData, new NVPair("ChoiceId", choiceId));
        }
      }
    }
    else {
      SQL.insert(" insert into answers_choices (answer_id, choice_id) " +
          " values (:AnswerNr, :Choice) ", formData);
    }
  }

  @Override
  public void delete(Long answerNr) throws ProcessingException {
    if (!ACCESS.check(new DeleteAnswerPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (answerNr == null) {
      throw new ProcessingException("AnswerNr can no be null");
    }

    SQL.delete("delete from answers_choices where answer_id = :AnswerNr", new NVPair("AnswerNr", answerNr));
    SQL.delete("delete from answers where answer_id = :AnswerNr", new NVPair("AnswerNr", answerNr));
  }
}
