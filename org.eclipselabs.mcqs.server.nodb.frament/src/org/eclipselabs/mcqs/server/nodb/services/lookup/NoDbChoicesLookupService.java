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
package org.eclipselabs.mcqs.server.nodb.services.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipselabs.mcqs.server.nodb.DataStore;
import org.eclipselabs.mcqs.shared.services.lookup.IChoicesLookupService;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData;
import org.eclipselabs.mcqs.shared.services.process.QuestionFormData.Choices;

@Priority(100)
public class NoDbChoicesLookupService extends LocalLookupService implements IChoicesLookupService {

  @Override
  protected List<LookupRow> execCreateLookupRows(LookupCall call) throws ProcessingException {
    Object question = call.getMaster();
    if (call.getMaster() == null || !(question instanceof Integer)) {
      return Collections.emptyList();
    }
    else {
      QuestionFormData q = DataStore.getInstance().getQuestion(((Integer) question));
      if (q != null) {
        List<LookupRow> list = new ArrayList<LookupRow>();
        Choices choices = q.getChoices();
        for (int i = 0; i < choices.getRowCount(); i++) {
          Integer choiceNr = choices.getChoiceNr(i);
          if (choiceNr != null) {
            list.add(new LookupRow(Long.valueOf(choiceNr.longValue()), choices.getChoiceText(i)));
          }
        }
        return list;
      }
      return Collections.emptyList();
    }
  }
}
