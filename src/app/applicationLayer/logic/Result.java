/*
 * Result class contains logic for handling user query for an album
 * and selection of a particular album.
 * 
 * Mahendra M. Rai
 * Last modified: 07/06/2012
 */

package app.applicationLayer.logic;

import java.lang.Character;

import app.applicationLayer.core.internalLib.ServerCall;

public class Result extends LogicObject{
	//Response-related parameters
	private String title;
	private String coverart;
	private String uri;
	private long discogsID;
	
	//Query-related parameters
	private String titleForQuery;
	private String artistForQuery;
	
	public Result(String titleForQuery, String artistForQuery){
		this.titleForQuery = titleForQuery;
		this.artistForQuery = artistForQuery;
	}
	
	//Setters
	public void setTitle(String title){ this.title = title; }
	public void setCoverart(String coverart){ this.coverart = coverart; }
	public void setUri(String uri){ this.uri = uri; }
	
	//Getters
	public String getTitle(){ return this.title; }
	public String getCoverart(){ return this.coverart; }
	public long getDiscogsID(){ return this.discogsID; }
	
	public String getSearchResult(){
		//URL to perform search query in Discogs' database for
		//user's preferred album
		String url = "http://api.discogs.com/database/" +
					"search?q="+ replaceWhiteSpaces(this.titleForQuery) + "+" + 
					replaceWhiteSpaces(this.artistForQuery) + "&type=release" +
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
		return this.jsonData;
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
	
	/**
	 * Extracts the ID of the album used in the Discogs' database.
	 * @param uri URI of the album in Discogs' website.
	 * @return ID as long object.
	 */
	public long extractDiscogsIDFromUri(String uri){
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
