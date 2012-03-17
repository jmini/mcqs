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
package org.eclipselabs.mcqs.server.nodb.services.lookup;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.scout.commons.annotations.Priority;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipselabs.mcqs.shared.services.lookup.IChoicesLookupService;

@Priority(100)
public class NoDbChoicesLookupService extends LocalLookupService implements IChoicesLookupService {

  @Override
  protected List<LookupRow> execCreateLookupRows(LookupCall call) throws ProcessingException {
    Object question = call.getMaster();
    if (call.getMaster() == null || !(question instanceof Integer)) {
      return Collections.emptyList();
    }
    else {
      int questionNr = ((Integer) question).intValue();
      switch (questionNr) {
        case 1:
          return Arrays.<LookupRow> asList(
              new LookupRow(1, "Asia"),
              new LookupRow(2, "Africa"),
              new LookupRow(3, "North America"),
              new LookupRow(4, "South America"),
              new LookupRow(5, "Antarctica"),
              new LookupRow(6, "Europe"),
              new LookupRow(7, "Australia")
              );
        case 2:
          return Arrays.<LookupRow> asList(
              new LookupRow(8, "Monday"),
              new LookupRow(9, "Tuesday"),
              new LookupRow(10, "Wednesday"),
              new LookupRow(11, "Thursday"),
              new LookupRow(12, "Friday"),
              new LookupRow(13, "Saturday"),
              new LookupRow(14, "Sunday")
              );
        default:
          return Collections.emptyList();
      }
    }
  }
}