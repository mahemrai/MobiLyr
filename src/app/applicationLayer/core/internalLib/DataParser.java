/*
 * DataParser class for parsing JSON data received from the Discogs
 * server and creating appropriate logic object that will be presented
 * to the user. 
 * 
 * Note: Our parser object uses org.json.me library for parsing.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 02/07/2012
 */

package app.applicationLayer.core.internalLib;

import java.io.InputStream;
import java.util.Vector;

import net.rim.device.api.xml.parsers.DocumentBuilder;
import net.rim.device.api.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import app.applicationLayer.core.externalLib.jsonParser.*;
import app.applicationLayer.logic.TrackList;
import app.applicationLayer.logic.SearchResult;
import app.applicationLayer.logic.Result;

public class DataParser{
	//JSON data source
	private String dataSource;
	
	//Constructor
	public DataParser(String dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * Extracts SearchResult object from the provided JSON data
	 * @return SearchResult object
	 */
	public SearchResult getSearchResult(){
		SearchResult searchResult = new SearchResult();
		Result[] results;
		try{
			JSONObject json = new JSONObject(this.dataSource);
			JSONObject pagination = json.getJSONObject("pagination");
			JSONObject urls = pagination.getJSONObject("urls");
			
			if(urls.isNull("next")){ searchResult.setNextURL(null); }
			else searchResult.setNextURL(urls.getString("next"));
			
			if(urls.isNull("prev")){ searchResult.setPrevURL(null); }
			else searchResult.setPrevURL(urls.getString("prev"));

			JSONArray array = json.getJSONArray("results");
			results = new Result[array.length()];

			for(int i=0; i<array.length(); i++){
				JSONObject me = array.getJSONObject(i);
				String coverart = me.getString("thumb");
				String title = me.getString("title");
				String format = me.getString("format");
				String label = me.getString("label");				
				long id = me.getLong("id");
				results[i] = new Result(title, format, label, coverart, id);
			}

			searchResult.setResults(results);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return searchResult;
	}
	
	/**
	 * 
	 * @return
	 */
	public TrackList getAlbum(){
		TrackList album = new TrackList();
		Vector tracks = new Vector();
		try{
			JSONObject json = new JSONObject(this.dataSource);
			JSONArray tracklist = json.getJSONArray("tracklist");
			
			for(int i=0; i<tracklist.length(); i++){
				JSONObject me = tracklist.getJSONObject(i);
				tracks.addElement(me.getString("title"));
			}
			
			album.setList(tracks);
		} catch(JSONException e){
			e.printStackTrace();
		}
		
		return album;
	}
}
