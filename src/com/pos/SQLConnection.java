package com.pos;

import java.sql.Connection;
import java.sql.DriverManager;


/*
 *  Group Charlie
 * CMSC 495
 * University of Maryland, University College
 * 
 * 
 * Description: Creates SQL connection so that multiple java files can access this function
 * 					instead of repetitive functions.
 */


public class SQLConnection implements Provider
{
	public Connection openSQL()
	{
		
		try 
		{
			return (DriverManager.getConnection(CONNECTION_URL));
		}
		catch(Exception e) {	e.printStackTrace();		}
		return null;
	}
}
