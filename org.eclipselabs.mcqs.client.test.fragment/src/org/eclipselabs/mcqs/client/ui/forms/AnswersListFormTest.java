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
package org.eclipselabs.mcqs.client.ui.forms;

import junit.framework.Assert;

import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.ListBox.AnswersField.Table;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.ListBox.AnswersField.Table.AddAnAnswerMenu;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.ListBox.AnswersField.Table.EditAnswerMenu;
import org.eclipselabs.mcqs.client.ui.forms.AnswersListForm.MainBox.AnswersTabsBox.ListBox.AnswersField.Table.DeleteAnswerMenu;
import org.junit.Test;

public class AnswersListFormTest {

	private static final int QUESTION_NR = 1;

	@Test
	public void testAnswersListAndMenus() throws Exception {
		AnswersListForm form = new AnswersListForm();
		form.getQuestionNrField().setValue(QUESTION_NR);
		form.startDisplay();

		Table table = form.getAnswersField().getTable();
		Assert.assertTrue("AnswersField has rows", table.getRowCount() > 0);
		
		FormTestUtility.checkMenu(table, AddAnAnswerMenu.class);
		FormTestUtility.checkMenu(table, EditAnswerMenu.class);
		FormTestUtility.checkMenu(table, DeleteAnswerMenu.class);
	}

}
