/*
 * LogicObject class acts as a generalisation of all the other 
 * logic classes and contains some universal methods.
 * 
 * Author: Mahendra M. Rai
 * Last modified: 07/06/2012
 */

package app.applicationLayer.logic;

public abstract class LogicObject{
	protected String jsonData;
	
	//TODO: Implement auto-detection of the device connection
	protected String connectionType(){
		return null;
	}
	
	/**
	 * Set data resource for the appropriate logic object for future process
	 * @param jsonData data resource retrieved from the server
	 */
	public void setJSONData(String jsonData){ 
		this.jsonData = jsonData; 
	}
}
