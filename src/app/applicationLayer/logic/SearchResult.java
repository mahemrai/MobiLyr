/*
 * SearchResult class contains logic for handling user query for an album
 * and selection of a particular album.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 12/06/2012
 */

package app.applicationLayer.logic;

import app.applicationLayer.core.internalLib.DataParser;
import app.applicationLayer.core.internalLib.ServerCall;

public class SearchResult extends LogicObject{
	private String nextURL;
	private String prevURL;
	private Result[] setOfResults;
	
	//Query-related parameters
	private String titleForQuery;
	private String artistForQuery;
	
	public SearchResult(){}
	
	public SearchResult(String titleForQuery, String artistForQuery){
		this.titleForQuery = titleForQuery;
		this.artistForQuery = artistForQuery;
	}
	
	//Setters
	public void setNextURL(String nextUrl){ this.nextURL = nextUrl; }
	public void setPrevURL(String prevUrl){ this.prevURL = prevUrl; }
	public void setResults(Result[] setOfResults){ this.setOfResults = setOfResults; }
	
	//Getters
	public String getNextURL(){ return this.nextURL; }
	public String getPrevURL(){ return this.prevURL; }
	public Result[] getResults(){ return this.setOfResults; }
	
	/**
	 * Processes search query and returns the result
	 * @return SearchResult object
	 */
	public SearchResult getSearchResult(){
		//URL to perform search query in Discogs' database for
		//user's preferred album
		String url = "http://api.discogs.com/database/" +
					"search?q="+ replaceWhiteSpaces(artistForQuery) + "+" + 
					replaceWhiteSpaces(titleForQuery) + "&type=release" +
					"&page=1&per_page=10;deviceside=true";
		
		//Initialise ServerCall object with GET method
		ServerCall requestCall = new ServerCall("GET", url, this);
		requestCall.start();
		try{
			requestCall.join();
		}catch(InterruptedException e){
			//TODO: Implement proper exception handler
			e.printStackTrace();
		}
		
		String data = new String(response);
		
		//Initialise DataParser object by providing JSON data
		DataParser parser = new DataParser(data);
		//Get the parsed data as an object and return it
		SearchResult obj = parser.getSearchResult();
		return obj;
	}
	
	/**
	 * Gets a set of result as a page
	 * @param url
	 * @return
	 */
	public SearchResult getPageResult(String url){
		//Initialise ServerCall object with GET method
		ServerCall requestCall = new ServerCall("GET", url+";deviceSide=true", this);
		requestCall.start();
		try{
			requestCall.join();
		}catch(InterruptedException e){
			//TODO: Implement proper exception handler
			e.printStackTrace();
		}
		
		String data = new String(response);
		
		//Initialise DataParser object by providing JSON data
		DataParser parser = new DataParser(data);
		//Get the parsed data as an object and return it
		SearchResult obj = parser.getSearchResult();
		return obj;
	}
	
	/**
	 * Replaces whitespace in a query text with '+' symbol.
	 * @param text User entered text.
	 * @return
	 */
	private String replaceWhiteSpaces(String text){
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<text.length(); i++){
			char a = text.charAt(i);
			//Check if the current character is a whitespace
			//and replace it
			if(a == ' '){
				buffer.append("+");
			}else{
				buffer.append(a);
			}
		}
        return buffer.toString();
	}
}
