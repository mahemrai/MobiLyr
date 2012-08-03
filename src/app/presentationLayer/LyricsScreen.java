/*
 * LyricsScreen class allows user to view the lyrics of their selected
 * song.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 03/08/2012
 */

package app.presentationLayer;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class LyricsScreen extends MainScreen{
	private LabelField header;
	private SeparatorField separator;
	private RichTextField lyrics;
	
	private String artistName, songTitle;
	
	public LyricsScreen(String artistName, String songTitle, String songLyrics){
		setTitle("MobiLyr: Lyrics");
		
		this.artistName = artistName;
		this.songTitle = songTitle;
		
		header = new LabelField(this.songTitle + " by " + this.artistName);
		separator = new SeparatorField();
		lyrics = new RichTextField();
		
		lyrics.setText(songLyrics);
		
		add(header);
		add(separator);
		add(lyrics);
	}
}
