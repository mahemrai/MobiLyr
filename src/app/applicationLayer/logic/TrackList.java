/*
 * TrackList class contains logic for retrieving list of tracks
 * for the user selected album.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 06/07/2012
 */

package app.applicationLayer.logic;

import java.util.Vector;

import app.applicationLayer.core.internalLib.DataParser;
import app.applicationLayer.core.internalLib.ServerCall;

public class TrackList extends LogicObject{
	private long albumId;
	private Vector list;
	
	public TrackList(){};
	
	public TrackList(long albumId){
		this.albumId = albumId;
	}
	
	public void setList(Vector list){ this.list = list;}
	
	public Vector getList(){ return this.list; }
	
	/**
	 * 
	 * @return
	 */
	public TrackList getTrackList(){
		String url = "http://api.discogs.com/releases/" + Long.toString(this.albumId) +
						";deviceside=true";
		ServerCall requestCall = new ServerCall("GET", url, this);
		requestCall.start();
		try{
			requestCall.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		String data = new String(response);
		DataParser parser = new DataParser(data);
		TrackList obj = parser.getAlbum();
		return obj;
	}
}
