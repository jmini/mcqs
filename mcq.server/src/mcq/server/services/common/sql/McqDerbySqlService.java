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
package mcq.server.services.common.sql;

import org.eclipse.scout.rt.services.common.jdbc.AbstractDerbySqlService;
import org.eclipse.scout.service.IService;

public class McqDerbySqlService extends AbstractDerbySqlService implements IService {
  @Override
  protected String getConfiguredJdbcMappingName() {
    return "jdbc:derby:/Users/jebr/code/mcq/mcq_db.derby";
  }

  @Override
  protected String getConfiguredUsername() {
    return "mcq";
  }

  @Override
  protected String getConfiguredPassword() {
    return "qcm";
  }
}
