/*
 * Result class
 * 
 * Author: Mahendra M. Rai
 * Last modified: 12/06/2012
 */

package app.applicationLayer.logic;

import app.applicationLayer.core.internalLib.ServerCall;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;

public class Result extends LogicObject{
	//Response-related parameters
	private String title;
	private String format;
	private String coverart;
	private long discogsID;
	
	private Bitmap image;
	
	//Constructor
	public Result(String title, String format, String coverart, long id){
		this.title = title;
		this.format = format;
		this.coverart = coverart;
		this.discogsID = id;
	}
	
	//Getters
	public String getTitle(){ return this.title; }
	public String getFormat(){ return this.format; }
	public String getCoverart(){ return this.coverart; }
	public long getDiscogsID(){ return this.discogsID; }
	public Bitmap getImage(){ return this.image; }
	
	/**
	 * Sets image from the provided url.
	 * @param url
	 * @return
	 */
	public void setImage(String url){
		String urlWithConnSuffix = url + ";deviceside=true";
		ServerCall requestCall = new ServerCall("GET", urlWithConnSuffix, this);
		requestCall.start();
		try {
			requestCall.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EncodedImage bitmap = EncodedImage.createEncodedImage(this.response, 0, this.response.length);
		this.image = bitmap.getBitmap();
	}
	
	/**
	 * Extracts the ID of the album used in the Discogs' database.
	 * @param uri URI of the album in Discogs' website.
	 * @return ID as long object.
	 */
	private long extractDiscogsIDFromUri(String uri){
		StringBuffer buffer = new StringBuffer();
		//Loop through the provided String
		for(int i=0; i<uri.length(); i++){
			char a = uri.charAt(i);
			//Check if the character is a digit and append it into
			//the buffer
			if(Character.isDigit(a)){
				buffer.append(a);
			}
		}
		
		//return the extracted data as long object
		return Long.parseLong(buffer.toString());
	}
}
