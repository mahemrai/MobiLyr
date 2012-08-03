/*
 * Lyric class contains logic for handling user's request for a selected
 * song.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 03/08/2012
 */

package app.applicationLayer.logic;

import app.applicationLayer.core.internalLib.ServerCall;

public class Lyric extends LogicObject{
	private String artist, songTitle;
	private String lyrics;
	
	//constructor
	public Lyric(String artist, String songTitle){
		this.artist = artist;
		this.songTitle = songTitle;
	}
	
	/**
	 * Processes first part of the query by getting the id of the selected song
	 * from LyrDB web service to determine the song exists in their database.
	 * @return
	 */
	public String getLyricDbID(){
		//url for query
		String url = "http://webservices.lyrdb.com/lookup.php?q=" + replaceWhiteSpaces(this.artist) + 
				"|" + replaceWhiteSpaces(this.songTitle) + "&for=match;deviceside=true";
		
		//make request and get the result
		ServerCall requestCall = new ServerCall("GET", url, this);
		requestCall.start();
		try{
			requestCall.join();
		}catch(InterruptedException e){
			//TODO: Implement proper exception handler
			e.printStackTrace();
		}
		
		String data = new String(response);
		return extractLyricDbId(data);
	}
	
	/**
	 * Processes the second part of the query by retrieving the lyrics of the user's
	 * selected song. Uses the id retrieved in the first part to get the lyrics.
	 * @param lyricDbID
	 * @return
	 */
	public String getSongLyrics(String lyricDbID){
		String url = "http://webservices.lyrdb.com/getlyr.php?q=" + lyricDbID + ";deviceside=true";
		ServerCall requestCall = new ServerCall("GET", url, this);
		requestCall.start();
		try{
			requestCall.join();
		}catch(InterruptedException e){
			//TODO: Implement proper exception handler
			e.printStackTrace();
		}
		
		String data = new String(response);
		return data;
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
	
	/**
	 * Extracts id of the song present in the LyrDB web service.
	 * @param myData
	 * @return
	 */
	private String extractLyricDbId(String text){
		StringBuffer buffer = new StringBuffer();
		
		for(int i=0; i<text.length(); i++){
			char a = text.charAt(i);
			if(a == '\\'){
				break;
			}
			else{
				buffer.append(a);
			}
		}
		return buffer.toString();
	}
}
