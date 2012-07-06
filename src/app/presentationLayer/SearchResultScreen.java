/*
 * SearchResultScreen class allows user to view the result of their search,
 * navigate through them and select a particular version of the album.
 * 
 * TODO: Commenting and lots of work
 * 
 * Author: Mahendra M. Rai
 * Last modified: 02/07/2012
 */

package app.presentationLayer;

import app.applicationLayer.customUIComponents.StatusDialog;
import app.applicationLayer.logic.TrackList;
import app.applicationLayer.logic.Result;
import app.applicationLayer.logic.SearchResult;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class SearchResultScreen extends MainScreen implements FieldChangeListener{
	private HorizontalFieldManager btnManager;
	private ButtonField nextBtn, prevBtn;
	
	private SearchResult data;
	private Result[] results;
	private Result result;
	private int newY;

	public SearchResultScreen(SearchResult data){
		setTitle("MobiLyr: Search Result");

		this.data = data;
		populateList(this.data);

		add(resultList);
		resultList.setRowHeight(100);
		
		btnManager = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
		nextBtn = new ButtonField("Next", Field.FIELD_RIGHT | ButtonField.CONSUME_CLICK);
		prevBtn = new ButtonField("Prev", Field.FIELD_LEFT | ButtonField.CONSUME_CLICK);
		
		nextBtn.setChangeListener(this);
		prevBtn.setChangeListener(this);
		//resultList.setChangeListener(this);
		
		btnManager.add(prevBtn);
		btnManager.add(nextBtn);
		setStatus(btnManager);
	}

	ObjectListField resultList = new ObjectListField(){
		public void drawListRow(ListField list, Graphics g, int index, int y, int width){
			if(index >= 0){
				result = results[index];
				//set font
				Font i = getFont().derive(Font.PLAIN);
				g.setFont(i);
				Bitmap bmp;
				if(!result.getCoverart().equalsIgnoreCase("null")){
					bmp = result.getImage();
				}
				else{
					bmp = Bitmap.getBitmapResource("icon.png");
				}

				g.drawBitmap(4, y+5, bmp.getWidth(), bmp.getHeight(), bmp, 0, 0);
				g.drawText(result.getTitle(), 100, y+5);
				newY = y+(this.getFont()).getHeight()+1;
				g.drawText(result.getLabel(), 100, newY);
				newY = newY+(this.getFont()).getHeight()+1;
				g.drawText(result.getFormat(), 100, newY);
				g.drawLine(0, y+resultList.getRowHeight()-1, 
						resultList.getWidth(), y+resultList.getRowHeight()-1);
			}
		}
		
		public boolean navigationClick(int status, int time){
			int index = resultList.getSelectedIndex();
			result = results[index];
			//String id = Long.toString(result.getDiscogsID());
			TrackList album = new TrackList(result.getDiscogsID());
			//album.getAlbumDetail();
			UiApplication.getUiApplication().pushScreen(new AlbumDetailScreen(album));
			return true;
		}
	};

	public void fieldChanged(final Field field, int context) {
		new Thread(){
			public void run(){
				if(field == nextBtn){
					nextBtnClick();
				}
				else if(field == prevBtn){
					prevBtnClick();
				}
			}
		}.start();
	}
	
	/**
	 * 
	 */
	private void nextBtnClick(){
		//Custom dialog to display status message
		final StatusDialog status = new StatusDialog("Fetching...");
		//Display status message to the user
		UiApplication.getUiApplication().invokeLater(new Runnable(){
			public void run(){
				UiApplication.getUiApplication().pushScreen(status);
			}
		});

		//TODO
		final SearchResult a = this.data;
		this.data = null;
		this.data = a.getPageResult(a.getNextURL());
		results = null;
		
		//Close dialog
		UiApplication.getUiApplication().invokeLater(new Runnable(){
    		public void run(){
    			populateList(data);
    			UiApplication.getUiApplication().popScreen(status);
    			SearchResultScreen.this.invalidate();
    		}
    	});
	}
	
	/**
	 * 
	 */
	private void prevBtnClick(){
		//Custom dialog to display status message
		final StatusDialog status = new StatusDialog("Fetching...");
		//Display status message to the user
		UiApplication.getUiApplication().invokeLater(new Runnable(){
			public void run(){
				UiApplication.getUiApplication().pushScreen(status);
			}
		});

		//TODO
		final SearchResult a = this.data;
		this.data = null;
		this.data = a.getPageResult(a.getPrevURL());
		results = null;
		
		//Close dialog
		UiApplication.getUiApplication().invokeLater(new Runnable(){
    		public void run(){
    			populateList(data);
    			UiApplication.getUiApplication().popScreen(status);
    			SearchResultScreen.this.invalidate();
    		}
    	});
	}
	
	/**
	 * 
	 * @param data
	 */
	private void populateList(SearchResult data){
		results = data.getResults();
		int size = results.length;
		
		for(int i=0; i<size; i++){
			result = results[i];
			if(!result.getCoverart().equalsIgnoreCase("null")){
				result.setImage(result.getCoverart());
			}
		}
		resultList.set(results);
	}
}