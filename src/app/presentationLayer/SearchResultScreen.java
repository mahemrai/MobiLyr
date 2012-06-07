/*
 * SearchResultScreen class
 * 
 * TODO: Commenting and lots of work
 * 
 * Mahendra M. Rai
 * Last modified: 07/06/2012
 */

package app.presentationLayer;

import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BorderFactory;

public class SearchResultScreen extends MainScreen{
	private LabelField responseText;
	private TextField _responseText;
	
	public SearchResultScreen(String data){
		setTitle("MobiLyr: Search Result");
		
		responseText = new LabelField("JSON data:");
		_responseText = new TextField();
		
		_responseText.setBorder(BorderFactory.createRoundedBorder(new XYEdges(5,5,5,5)));
		
		_responseText.setText(data);
		
		add(responseText);
		add(_responseText);
	}
}
