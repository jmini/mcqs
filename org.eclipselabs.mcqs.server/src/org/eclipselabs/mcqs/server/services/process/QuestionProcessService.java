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
import org.eclipse.scout.commons.holders.ITableHolder;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.shared.security.CreateQuestionPermission;
import org.eclipselabs.mcqs.shared.security.DeleteQuestionPermission;
import org.eclipselabs.mcqs.shared.security.ReadQuestionPermission;
import org.eclipselabs.mcqs.shared.security.UpdateQuestionPermission;
import org.eclipselabs.mcqs.shared.services.process.IQuestionProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData.Choices.ChoicesRowData;

public class QuestionProcessService extends AbstractService implements IQuestionProcessService {

  @Override
  public QuestionFormData prepareCreate(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateQuestionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }
    //nothing to do
    return formData;
  }

  @Override
  public QuestionFormData create(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateQuestionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.insert(" insert into questions (question_text, multiple_choices) " +
        " values (:QuestionText, :MultipleChoices) ", formData);

    SQL.selectInto(" values IDENTITY_VAL_LOCAL() " +
        " into  :QuestionNr", formData);

    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can not be null");
    }
    storeQuestionChoices(formData);
    return formData;
  }

  @Override
  public QuestionFormData load(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadQuestionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    SQL.selectInto(" select question_text, multiple_choices " +
        " from questions " +
        " where question_id = :QuestionNr " +
        " into  :QuestionText, :MultipleChoices ", formData);

    SQL.selectInto(" select choice_id, choice_text " +
        " from  choices " +
        " where question_id = :questionNr " +
        " into  :{choices.choiceNr}, :{choices.choiceText}", formData);

    return formData;
  }

  @Override
  public QuestionFormData store(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateQuestionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    SQL.update("update questions set question_text = :QuestionText, multiple_choices = :MultipleChoices where question_id = :QuestionNr", formData);

    storeQuestionChoices(formData);
    return formData;
  }

  /**
   * Store the choices (insert, update, remove).
   *
   * @param formData
   *          where questionNr is set.
   * @throws ProcessingException
   */
  private void storeQuestionChoices(QuestionFormData formData) throws ProcessingException {
    for (ChoicesRowData row : formData.getChoices().getRows()) {
      switch (row.getRowState()) {
        case ITableHolder.STATUS_INSERTED:
          SQL.insert(" insert into choices (choice_text, question_id) " +
              " values (:ChoiceText, :QuestionNr) ", formData, new NVPair("ChoiceText", row.getChoiceText()));
          break;
        case ITableHolder.STATUS_DELETED:
          SQL.delete("delete from answers_choices where choice_id = :ChoiceId", new NVPair("ChoiceId", row.getChoiceNr()));
          SQL.delete(" delete from choices " +
              " where choice_id = :ChoiceId ", new NVPair("ChoiceId", row.getChoiceNr()));
          break;
        case ITableHolder.STATUS_UPDATED:
          SQL.update(" update choices " +
              " set choice_text = :ChoiceText " +
              " where choice_id = :ChoiceId ",
              new NVPair("ChoiceText", row.getChoiceText()),
              new NVPair("ChoiceId", row.getChoiceNr()));
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void delete(Integer questionNr) throws ProcessingException {
    if (!ACCESS.check(new DeleteQuestionPermission())) {
      throw new VetoException(TEXTS.get("AuthorizationFailed"));
    }

    SQL.delete("delete from answers_choices where answer_id in (select answer_id from answers where question_id = :QuestionNr)", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from answers where question_id = :QuestionNr", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from answers_choices where choice_id in (select choice_id from choices where question_id = :QuestionNr)", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from choices where question_id = :QuestionNr", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from questions where question_id = :QuestionNr", new NVPair("QuestionNr", questionNr));
  }
}
