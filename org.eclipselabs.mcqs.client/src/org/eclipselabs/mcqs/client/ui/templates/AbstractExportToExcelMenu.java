/*******************************************************************************
 * Copyright 2013 Jeremie Bresson
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
package org.eclipselabs.mcqs.client.ui.templates;

import java.io.File;
import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.docx4j.client.ScoutXlsxSpreadsheetAdapter;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.shell.IShellService;
import org.eclipse.scout.service.SERVICES;

public abstract class AbstractExportToExcelMenu extends AbstractExtensibleMenu {

  @Override
  protected String getConfiguredText() {
    return TEXTS.get("ExportToExcelMenu");
  }

  @Override
  protected void execAction() throws ProcessingException {
    ScoutXlsxSpreadsheetAdapter s = new ScoutXlsxSpreadsheetAdapter();
    File xlsx = s.exportTable(null, provideTitle(), provideTable());
    SERVICES.getService(IShellService.class).shellOpen(xlsx.getAbsolutePath());
  }

  @Override
  protected Set<? extends IMenuType> getConfiguredMenuTypes() {
    return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
  }

  protected abstract String provideTitle();

  protected abstract ITable provideTable();
}
