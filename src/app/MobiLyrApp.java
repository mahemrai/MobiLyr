/*
 * MobiLyrApp class
 * 
 * A mobile application to search for song lyrics. The app uses discogs API
 * and LyrDB web service to get lyrics for the song selected by the user. It
 * allows the user to search for particular album of an artist and then select
 * the song for which they wish to retrieve the lyrics.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 05/06/2012
 */

package app;

import app.presentationLayer.FindAlbumScreen;
import net.rim.device.api.ui.UiApplication;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MobiLyrApp extends UiApplication{
	/**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
	public static void main(String[] args){
		// Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
		MobiLyrApp theApp = new MobiLyrApp();
		theApp.enterEventDispatcher();
	}
	
	//constructor
	public MobiLyrApp(){
		//Push a screen onto the UI stack for rendering.
		pushScreen(new FindAlbumScreen());
	}
}
