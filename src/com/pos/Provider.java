package com.pos;

import java.sql.Connection;

interface Provider
{
	String DRIVER = "oracle.jdbc.driver.OracleDriver";
	String CONNECTION_URL = "jdbc:sqlserver://poscmsc495.database.windows.net:1433;database=POS;"
			+ "user=wholl@poscmsc495;password=cmsc495!;encrypt=true;"
			+ "trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
	Connection openSQL();
}