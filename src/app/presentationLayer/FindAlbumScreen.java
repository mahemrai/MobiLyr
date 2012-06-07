/*
 * FindAlbumScreen class allows the user to search for an album of a 
 * particular artist. The user has to enter name of the album and the 
 * artist to make a query. The query is made to discogs database using 
 * the discogs API.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 05/06/2012
 */

package app.presentationLayer;

import app.applicationLayer.logic.Result;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BorderFactory;

public class FindAlbumScreen extends MainScreen implements FieldChangeListener{
	private LabelField title, artist;
	private TextField _title, _artist;
	private ButtonField search;
	private VerticalFieldManager searchFields;
	
	public FindAlbumScreen(){
		//Set the displayed title of the screen
		setTitle("MobiLyr: Search Title");
		
		//Bitmap image for the border
		Bitmap border = Bitmap.getBitmapResource("img/rounded-border.png");
		
		//UI field container
		searchFields = new VerticalFieldManager();
		//Create container border using the bitmap image
		searchFields.setBorder(
			BorderFactory.createBitmapBorder(
				new XYEdges(12,12,12,12), border
			)
		);
		
		//UI fields
		title = new LabelField("Title:");
		artist = new LabelField("Artist:");
		_title = new TextField();
		_artist = new TextField();
		search = new ButtonField("Search", Field.USE_ALL_WIDTH | 
								Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		
		//Create border for text fields
		_title.setBorder(BorderFactory.createRoundedBorder(new XYEdges(5,5,5,5)));
		_artist.setBorder(BorderFactory.createRoundedBorder(new XYEdges(5,5,5,5)));
		
		//Attach listener to the button
		search.setChangeListener(this);
		
		//Add UI fields to the screen
		searchFields.add(title);
		searchFields.add(_title);
		searchFields.add(artist);
		searchFields.add(_artist);
		searchFields.add(search);
		add(searchFields);
	}
	
	/**
	 * Take action appropriately when a field is clicked or changed.
	 * @param field Specifies active field
	 * @param context
	 */
	public void fieldChanged(Field field, int context){
		//In the case of button being clicked check whether all required
		//information are provided.
		if(field == search){
			if(_title.getText().equals("") || _artist.getText().equals("")){
				Dialog.alert("Enter all required fields.");
			}else{
				//Use Result logic to retrieve data from discogs server
				Result result = new Result(_title.getText(), _artist.getText());
				//Push screen to the stack
				UiApplication.getUiApplication().pushScreen(
					new SearchResultScreen(result.getSearchResult())
				);
			}
		}
	}
}
