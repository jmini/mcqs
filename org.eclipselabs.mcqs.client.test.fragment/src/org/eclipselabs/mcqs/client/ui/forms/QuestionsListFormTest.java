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

import org.eclipse.scout.testing.client.runner.ScoutClientTestRunner;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.QuestionsField.Table;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.QuestionsField.Table.AddAnAnswerMenu;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.QuestionsField.Table.CreateQuestionMenu;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.QuestionsField.Table.DeleteQuestionMenu;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.QuestionsField.Table.DisplayAllAnswersMenu;
import org.eclipselabs.mcqs.client.ui.forms.QuestionsListForm.MainBox.QuestionsField.Table.EditQuestionMenu;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ScoutClientTestRunner.class)
public class QuestionsListFormTest {

	@Test
	public void testTableAndMenus() throws Exception {
		QuestionsListForm form = new QuestionsListForm();
		form.startDisplay();

		Table table = form.getQuestionsField().getTable();
		Assert.assertTrue("QuestionsField has rows", table.getRowCount() > 0);
		
		FormTestUtility.checkMenu(table, AddAnAnswerMenu.class);
		FormTestUtility.checkMenu(table, DisplayAllAnswersMenu.class);
		FormTestUtility.checkMenu(table, CreateQuestionMenu.class);
		FormTestUtility.checkMenu(table, EditQuestionMenu.class);
		FormTestUtility.checkMenu(table, DeleteQuestionMenu.class);
	}



}
