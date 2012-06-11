/*
 * StatusDialog class for displaying status message. This dialog will not include
 * any buttons but will be displayed during the process and closed when the process
 * is complete.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 08/06/2012
 */

package app.applicationLayer.customUIComponents;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class StatusDialog extends PopupScreen{
	private LabelField status;
	
	public StatusDialog(String message){
		super(new VerticalFieldManager(StatusDialog.NO_HORIZONTAL_SCROLL));
		status = new LabelField(message);
		add(status);
	}
}
