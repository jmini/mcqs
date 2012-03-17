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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.ConfigOperation;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

//TODO: remove this class and use the Eclipse Scout LocalLookupService class when it is available.
//SEE: https://bugs.eclipse.org/bugs/show_bug.cgi?id=369024
public class LocalLookupService extends AbstractLookupService implements ILookupService {

  @ConfigOperation
  @Order(30)
  protected List<LookupRow> execCreateLookupRows(LookupCall call) throws ProcessingException {
    return Collections.emptyList();
  }

  public static Pattern createLowerCaseSearchPattern(String s) {
    if (s == null) s = "";
    s = s.toLowerCase();
    if (s.indexOf('*') < 0) {
      s = s + "*";
    }
    return Pattern.compile(StringUtility.toRegExPattern(s), Pattern.DOTALL);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByKey(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    Object key = call.getKey();
    if (key != null) {
      for (LookupRow row : execCreateLookupRows(call)) {
        if (key.equals(row.getKey())) {
          list.add(row);
        }
      }
    }
    return list.toArray(new LookupRow[0]);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByText(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    Pattern p = createLowerCaseSearchPattern(call.getText());
    for (LookupRow row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list.toArray(new LookupRow[0]);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByAll(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    Pattern p = createLowerCaseSearchPattern(call.getAll());
    for (LookupRow row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list.toArray(new LookupRow[0]);
  }

  /**
   * Complete override using local data
   */
  @Override
  public LookupRow[] getDataByRec(LookupCall call) throws ProcessingException {
    ArrayList<LookupRow> list = new ArrayList<LookupRow>();
    return list.toArray(new LookupRow[0]);
  }
}
