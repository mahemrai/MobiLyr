/*
 * DataParser class
 * 
 * TODO: Comment and lots of work
 * 
 * Author: Mahendra M. Rai
 * Last modified: 11/06/2012
 */

package app.applicationLayer.core.internalLib;

import app.applicationLayer.core.externalLib.jsonParser.*;
import app.applicationLayer.logic.SearchResult;
import app.applicationLayer.logic.SearchResult.Result;

public class DataParser{
	private String dataSource;
	
	public DataParser(String dataSource){
		this.dataSource = dataSource;
	}
	
	public SearchResult getSearchResult(){
		SearchResult searchResult = new SearchResult();
		try{
			JSONObject json = new JSONObject(this.dataSource);
			JSONObject pagination = json.getJSONObject("pagination");
			JSONObject urls = pagination.getJSONObject("urls");
			searchResult.setNextURL(urls.getString("next"));
		} catch(JSONException e){
			e.printStackTrace();
		}
		return searchResult;
	}
	
	public String getResult(){
		int per_page;
		String result = null;
		try {
			JSONObject json = new JSONObject(this.dataSource);
			JSONObject pagination = json.getJSONObject("pagination");
			per_page = pagination.getInt("per_page");
			result = Integer.toString(per_page);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
