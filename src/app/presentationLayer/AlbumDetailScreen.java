/*
 * AlbumDetailScreen class allows user to view list of tracks related
 * to the user selected album and select a track which they wish to
 * retrieve the lyrics for.
 * 
 * TODO: Commenting and lots of work
 * 
 * Author: Mahendra M. Rai
 * Last modified: 06/07/2012
 */

package app.presentationLayer;

import java.util.Vector;

import app.applicationLayer.logic.TrackList;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;

public class AlbumDetailScreen extends MainScreen implements FieldChangeListener{
	private TextField text;
	private TrackList album;
	private String songTitle;
	private String[] data;
	
	public AlbumDetailScreen(TrackList temp){
		setTitle("MobiLyr: Album");
		populateList(temp);
		add(trackList);
		trackList.setRowHeight(40);
	}
	
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
	};
	
	public void fieldChanged(Field field, int context) {
	}
	
	private void populateList(TrackList temp){
		//Album temp = new Album(id);
		album = temp.getTrackList();
		Vector list = album.getList();
		int size = list.size();
		
		data = new String[size];
		
		for(int i=0; i<size; i++){
			data[i] = (String) list.elementAt(i);
		}
		
		trackList.set(data);
	}

}
