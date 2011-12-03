package org.eclipselabs.mcqs.ui.swt.window.messagebox;

import org.eclipse.scout.rt.client.ui.messagebox.IMessageBox;
import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.window.messagebox.SwtScoutMessageBoxDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class SwtMacScoutMessageBoxDialog extends SwtScoutMessageBoxDialog {

  /**
   * @param parentShell
   * @param scoutObject
   * @param environment
   */
  public SwtMacScoutMessageBoxDialog(Shell parentShell, IMessageBox scoutObject, ISwtEnvironment environment) {
    super(parentShell, scoutObject, environment);
    int dialogStyle = SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.SHEET;
    setShellStyle(dialogStyle);
  }

  //TODO: handle the title.

}
