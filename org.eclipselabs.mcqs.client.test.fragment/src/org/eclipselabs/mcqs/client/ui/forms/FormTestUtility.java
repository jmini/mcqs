package org.eclipselabs.mcqs.client.ui.forms;

import junit.framework.Assert;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenu;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;

public final class FormTestUtility {
	
	static void checkMenu(ITable table, Class<? extends IMenu> menuClass) throws ProcessingException {
		IMenu menu = table.getMenu(menuClass);
		Assert.assertNotNull(menuClass.getSimpleName() + "exists", menu);
		Assert.assertTrue(menuClass.getSimpleName() + "is visible", menu.isVisible());
	}
	
	private FormTestUtility() {}
}
