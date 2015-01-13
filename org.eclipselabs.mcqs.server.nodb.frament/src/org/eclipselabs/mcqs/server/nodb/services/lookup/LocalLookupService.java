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
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

//TODO: remove this class and use the Eclipse Scout LocalLookupService class when it is available.
//SEE: https://bugs.eclipse.org/bugs/show_bug.cgi?id=369024
public class LocalLookupService<T> extends AbstractLookupService<T> implements ILookupService<T> {

  @ConfigOperation
  @Order(30)
  protected List<ILookupRow<T>> execCreateLookupRows(ILookupCall<T> call) throws ProcessingException {
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
  public List<? extends ILookupRow<T>> getDataByKey(ILookupCall<T> call) throws ProcessingException {
    ArrayList<ILookupRow<T>> list = new ArrayList<ILookupRow<T>>();
    Object key = call.getKey();
    if (key != null) {
      for (ILookupRow<T> row : execCreateLookupRows(call)) {
        if (key.equals(row.getKey())) {
          list.add(row);
        }
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<T>> getDataByText(ILookupCall<T> call) throws ProcessingException {
    ArrayList<ILookupRow<T>> list = new ArrayList<ILookupRow<T>>();
    Pattern p = createLowerCaseSearchPattern(call.getText());
    for (ILookupRow<T> row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<T>> getDataByAll(ILookupCall<T> call) throws ProcessingException {
    ArrayList<ILookupRow<T>> list = new ArrayList<ILookupRow<T>>();
    Pattern p = createLowerCaseSearchPattern(call.getAll());
    for (ILookupRow<T> row : execCreateLookupRows(call)) {
      if (row.getText() != null && p.matcher(row.getText().toLowerCase()).matches()) {
        list.add(row);
      }
    }
    return list;
  }

  /**
   * Complete override using local data
   */
  @Override
  public List<? extends ILookupRow<T>> getDataByRec(ILookupCall<T> call) throws ProcessingException {
    ArrayList<ILookupRow<T>> list = new ArrayList<ILookupRow<T>>();
    return list;
  }
}
