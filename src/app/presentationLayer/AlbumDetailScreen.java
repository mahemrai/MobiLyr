/*
 * AlbumDetailScreen class allows user to view list of tracks related
 * to the user selected album and select a track which they wish to
 * retrieve the lyrics for.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 03/08/2012
 */

package app.presentationLayer;

import java.util.Vector;

import app.applicationLayer.logic.Lyric;
import app.applicationLayer.logic.TrackList;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;

public class AlbumDetailScreen extends MainScreen{
	private TextField text;
	private TrackList album;
	private String songTitle, artistName;
	private String[] data;
	
	public AlbumDetailScreen(String artistName, TrackList temp){
		setTitle("MobiLyr: Album");
		this.artistName = artistName;
		populateList(temp);
		add(trackList);
		trackList.setRowHeight(40);
	}
	
	//list tracks of the selected album
	ObjectListField trackList = new ObjectListField(){
		public void drawListRow(ListField list, Graphics g, int index, int y, int width){
			if(index >= 0){
				songTitle = data[index];
				Font i = getFont().derive(Font.PLAIN);
				g.setFont(i);
				g.drawText(songTitle, 20, y+5);
				g.drawLine(0, y+trackList.getRowHeight()-1, 
						trackList.getWidth(), y+trackList.getRowHeight()-1);
			}
		}
		
		/**
		 * get the lyrics of the selected song and display the LyricsScreen
		 */
		public boolean navigationClick(int status, int time){
			int index = trackList.getSelectedIndex();
			songTitle = data[index];
			
			//instantiate new Lyric object and retrieve lyrics of the song
			Lyric lyric = new Lyric(artistName, songTitle);
			String id = lyric.getLyricDbID();
			String text = lyric.getSongLyrics(id);
			
			UiApplication.getUiApplication().pushScreen(new LyricsScreen(artistName, songTitle, text));
			return true;
		}
	};
	
	/**
	 * populate the listfield object after retrieving tracklist
	 * @param temp
	 */
	private void populateList(TrackList temp){
		album = temp.getTrackList();
		//retrieve tracklist
		Vector list = album.getList();
		int size = list.size();
		
		data = new String[size];
		
		//swap the list into an array
		for(int i=0; i<size; i++){
			data[i] = (String) list.elementAt(i);
		}
		
		//assign the list to listfield object
		trackList.set(data);
	}

}
