/*
 * SearchResultScreen class
 * 
 * TODO: Commenting and lots of work
 * 
 * Author: Mahendra M. Rai
 * Last modified: 12/06/2012
 */

package app.presentationLayer;

import app.applicationLayer.logic.Result;
import app.applicationLayer.logic.SearchResult;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.MainScreen;

public class SearchResultScreen extends MainScreen implements FieldChangeListener{
	private LabelField responseText;
	private SearchResult searchResult;
	private Result[] results;
	private Result result;
	
	public SearchResultScreen(SearchResult data){
		setTitle("MobiLyr: Search Result");
		
		responseText = new LabelField("Results:");
		add(responseText);
		
		searchResult = data;
		results = searchResult.getResults();
		int size = results.length;
		
		for(int i=0; i<size; i++){
			result = results[i];
			if(!result.getCoverart().equalsIgnoreCase("null")){
				result.setImage(result.getCoverart());
			}
			resultList.insert(i, Long.toString(result.getDiscogsID()));
		}
		
		add(resultList);
		resultList.setRowHeight(100);
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
				
				g.drawBitmap(0, y+5, bmp.getWidth(), bmp.getHeight(), bmp, 0, 0);
				//g.drawText(result.getCoverart(), 0, y);
				g.drawLine(0, y+resultList.getRowHeight()-1, 
						resultList.getWidth(), y+resultList.getRowHeight()-1);
			}
		}
	};

	public void fieldChanged(Field field, int context) {
		// TODO Auto-generated method stub
		
	}
}
