package org.eclipselabs.mcqs.ui.swt.window.dialog;

import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.window.dialog.SwtScoutDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * <h3>SwtScoutDialog</h3> ...
 * 
 * @since 1.0.9 18.07.2008
 */
public class SwtMacScoutDialog extends SwtScoutDialog {

  public SwtMacScoutDialog(Shell parentShell, ISwtEnvironment environment) {
    super(parentShell, environment);
  }

  public SwtMacScoutDialog(Shell parentShell, ISwtEnvironment environment, int style) {
    super(parentShell, environment, style);
  }

  @Override
  protected void handleScoutPropertyChange(String name, Object newValue) {
    super.handleScoutPropertyChange(name, newValue);
    if (name.equals(IForm.PROP_SAVE_NEEDED)) {
      setSaveNeeded(((Boolean) newValue).booleanValue());
    }
  }

  /**
   * 
   */
  private void setSaveNeeded(boolean state) {
    getShell().setModified(state);
  }
}
