/*
 * SearchResult class contains logic for handling user query for an album
 * and selection of a particular album.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 07/06/2012
 */

package app.applicationLayer.logic;

import java.lang.Character;
import java.util.Vector;

import app.applicationLayer.core.externalLib.jsonParser.JSONException;
import app.applicationLayer.core.internalLib.DataParser;
import app.applicationLayer.core.internalLib.ServerCall;

public class SearchResult extends LogicObject{
	private String nextURL;
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
	public void setResults(Result[] setOfResults){ this.setOfResults = setOfResults; }
	
	//Getters
	public String getNextURL(){ return this.nextURL; }
	public Result[] getResults(){ return this.setOfResults; }
	
	public String getSearchResult(){
		//URL to perform search query in Discogs' database for
		//user's preferred album
		String url = "http://api.discogs.com/database/" +
					"search?q="+ replaceWhiteSpaces(this.artistForQuery) + "+" + 
					replaceWhiteSpaces(this.titleForQuery) + "&type=release" +
					"&page=1&per_page=10;deviceside=true";
		
		//Initialise ServerCall object with GET method
		ServerCall requestCall = new ServerCall("GET", url, this);
		//Start the thread
		requestCall.start();
		try{
			//Wait for its process to end
			requestCall.join();
		}catch(InterruptedException e){
			//TODO: Implement proper exception handler
			e.printStackTrace();
		}
		
		DataParser parser = new DataParser(this.jsonData);
		SearchResult obj = parser.getSearchResult();
		
		return obj.getNextURL();
		//return parser.getResult();
		//return this.jsonData;
	}
	
	/**
	 * Replaces whitespace in a query text with '+' symbol.
	 * @param text User entered text.
	 * @return
	 */
	public String replaceWhiteSpaces(String text){
		StringBuffer buffer = new StringBuffer();
		//Loop through the provided String
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
	
	/*
	 * Result class
	 */
	public class Result{
		//Response-related parameters
		private String title;
		private String format;
		private String coverart;
		private String uri;
		private long discogsID;
		
		public Result(String title, String format, String coverart, String uri){
			this.title = title;
			this.format = format;
			this.coverart = coverart;
			this.uri = uri;
			this.discogsID = extractDiscogsIDFromUri(uri);
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
}
