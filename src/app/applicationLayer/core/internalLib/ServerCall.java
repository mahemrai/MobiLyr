/*
 * ServerCall class
 * 
 * TODO: Commenting and lots of work
 * 
 * Mahendra M. Rai
 * Last modified: 12/06/2012
 */

package app.applicationLayer.core.internalLib;

import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.IOUtilities;

import app.applicationLayer.logic.LogicObject;

public class ServerCall extends Thread{
	private String requestMethod;
	private String url;
	private LogicObject obj;
	private byte[] postData;
	private byte[] response;
	
	//constructor for GET request
	public ServerCall(String requestMethod, String url, LogicObject obj){
		this.requestMethod = requestMethod;
		this.url = url;
		this.obj = obj;
	}
	
	//constructor for POST request
	public ServerCall(String requestMethod, String url, 
			byte[] postData, LogicObject obj){
		this.requestMethod = requestMethod;
		this.url = url;
		this.postData = postData;
		this.obj = obj;
	}
	
	public void run(){
		HttpConnection connection = null;
		InputStream data = null;
		int responseCode;
		
		try{
			connection = (HttpConnection)Connector.open(url, Connector.READ, true);
			connection.setRequestMethod(requestMethod);
			
			if(requestMethod.equals("POST") && postData != null){
				connection.setRequestProperty("Content-type", 
						"application/x-www-form-urlencoded");
				OutputStream requestOutput = connection.openOutputStream();
				requestOutput.write(postData);
				requestOutput.close();
			}
			
			responseCode = connection.getResponseCode();
			
			if(responseCode != HttpConnection.HTTP_OK){
				System.out.println(Integer.toString(responseCode));
			}
			
			data = connection.openInputStream();
			response = IOUtilities.streamToBytes(data);
			this.obj.setResponse(response);
		} catch(Exception e){
			//TODO: Implement proper exception handler
			System.out.println(e.getMessage());
		} finally{
			try{
				data.close();
				data = null;
				connection.close();
				connection = null;
			}catch(Exception e){}
		}
	}
}
