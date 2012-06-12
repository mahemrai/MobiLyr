/*
 * DataParser class for parsing JSON data received from the Discogs
 * server and creating appropriate logic object that will be presented
 * to the user. 
 * 
 * Note: Our parser object uses org.json.me library for parsing.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 12/06/2012
 */

package app.applicationLayer.core.internalLib;

import app.applicationLayer.core.externalLib.jsonParser.*;
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
		Result[] results = new Result[10];
		try{
			JSONObject json = new JSONObject(this.dataSource);
			JSONObject pagination = json.getJSONObject("pagination");
			JSONObject urls = pagination.getJSONObject("urls");
			searchResult.setNextURL(urls.getString("next"));
			
			JSONArray result = json.getJSONArray("results");
			
			for(int i=0; i<result.length(); i++){
				JSONObject me = result.getJSONObject(i);
				String coverart = me.getString("thumb");
				String format = me.getString("format");
				String title = me.getString("title");
				long id = me.getLong("id");
				results[i] = new Result(title, format, coverart, id);
			}
			
			searchResult.setResults(results);
		} catch(JSONException e){
			e.printStackTrace();
		}
		return searchResult;
	}
}
