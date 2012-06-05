/**
 * MobiLyrApp class
 * 
 * Last modified: 05/06/2012
 * Author: Mahendra M. Rai
 */

package app;

import presentationLayer.findTitleScreen;
import net.rim.device.api.ui.UiApplication;

public class MobiLyrApp extends UiApplication{
	public static void main(String[] args){
		MobiLyrApp theApp = new MobiLyrApp();
		theApp.enterEventDispatcher();
	}
	
	public MobiLyrApp(){
		pushScreen(new findTitleScreen());
	}
}
