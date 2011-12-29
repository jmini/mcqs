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
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.service.AbstractService;
import org.eclipselabs.mcqs.shared.Texts;
import org.eclipselabs.mcqs.shared.security.CreateQuestionPermission;
import org.eclipselabs.mcqs.shared.security.DeleteQuestionPermission;
import org.eclipselabs.mcqs.shared.security.ReadQuestionPermission;
import org.eclipselabs.mcqs.shared.security.UpdateQuestionPermission;
import org.eclipselabs.mcqs.shared.services.process.IQuestionProcessService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;

public class QuestionProcessService extends AbstractService implements IQuestionProcessService {

  @Override
  public QuestionFormData prepareCreate(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateQuestionPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }
    //nothing to do
    return formData;
  }

  @Override
  public QuestionFormData create(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new CreateQuestionPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }

    SQL.insert(" insert into questions (question_text) " +
        " values (:QuestionText) ", formData);

    SQL.selectInto(" values IDENTITY_VAL_LOCAL() " +
        " into  :QuestionNr", formData);

    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }
    storeQuestionChoices(formData);
    return formData;
  }

  @Override
  public QuestionFormData load(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new ReadQuestionPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }

    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    SQL.selectInto(" select question_text " +
        " from questions " +
        " where question_id = :QuestionNr " +
        " into  :QuestionText ", formData);

    SQL.selectInto(" select choice_id, choice_text " +
        " from  choices " +
        " where question_id = :questionNr " +
        " into  :choiceNr, :choiceText", formData.getChoices(), formData);

    return formData;
  }

  @Override
  public QuestionFormData store(QuestionFormData formData) throws ProcessingException {
    if (!ACCESS.check(new UpdateQuestionPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }

    if (formData.getQuestionNr() == null) {
      throw new ProcessingException("QuestionNr can no be null");
    }

    SQL.update("update questions set question_text = :QuestionText where question_id = :QuestionNr", formData);

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
    for (int i = 0; i < formData.getChoices().getRowCount(); i++) {
      switch (formData.getChoices().getRowState(i)) {
        case ITableHolder.STATUS_INSERTED:
          SQL.insert(" insert into choices (choice_text, question_id) " +
              " values (:ChoiceText, :QuestionNr) ", formData, new NVPair("ChoiceText", formData.getChoices().getChoiceText(i)));
          break;
        case ITableHolder.STATUS_DELETED:
          SQL.delete("delete from answers_choices where choice_id = :ChoiceId", new NVPair("ChoiceId", formData.getChoices().getChoiceNr(i)));
          SQL.delete(" delete from choices " +
              " where choice_id = :ChoiceId ", new NVPair("ChoiceId", formData.getChoices().getChoiceNr(i)));
          break;
        case ITableHolder.STATUS_UPDATED:
          SQL.update(" update choices " +
              " set choice_text = :ChoiceText " +
              " where choice_id = :ChoiceId ",
              new NVPair("ChoiceText", formData.getChoices().getChoiceText(i)),
              new NVPair("ChoiceId", formData.getChoices().getChoiceNr(i)));
          break;
        default:
          break;
      }
    }
  }

  @Override
  public void delete(Integer questionNr) throws ProcessingException {
    if (!ACCESS.check(new DeleteQuestionPermission())) {
      throw new VetoException(Texts.get("AuthorizationFailed"));
    }

    SQL.delete("delete from answers_choices where answer_id in (select answer_id from answers where question_id = :QuestionNr)", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from answers where question_id = :QuestionNr", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from answers_choices where choice_id in (select choice_id from choices where question_id = :QuestionNr)", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from choices where question_id = :QuestionNr", new NVPair("QuestionNr", questionNr));
    SQL.delete("delete from questions where question_id = :QuestionNr", new NVPair("QuestionNr", questionNr));
  }
}
