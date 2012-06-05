package presentationLayer;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BorderFactory;

public class findTitleScreen extends MainScreen{
	private Bitmap fieldManagerBorder;
	private LabelField title, artist;
	private TextField _title, _artist;
	private VerticalFieldManager searchFields;
	
	public findTitleScreen(){
		setTitle("MobiLyr: Search Title");
		
		fieldManagerBorder = Bitmap.getBitmapResource("rounded-border.png");
		
		searchFields = new VerticalFieldManager();
		searchFields.setBorder(
			BorderFactory.createBitmapBorder(
				new XYEdges(12,12,12,12), fieldManagerBorder
			)
		);
		
		title = new LabelField("Title:");
		_title = new TextField();
		_title.setBorder(BorderFactory.createRoundedBorder(new XYEdges(5,5,5,5)));
		
		searchFields.add(title);
		searchFields.add(_title);
		add(searchFields);
	}
}
