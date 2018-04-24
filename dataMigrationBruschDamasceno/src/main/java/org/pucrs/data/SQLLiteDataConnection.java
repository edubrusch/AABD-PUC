package org.pucrs.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLLiteDataConnection implements DataConnection {

	@Override
	public Connection connectBase(String connString) throws SQLException {
		
		Connection conn = null;
		String url = connString;
		conn = DriverManager.getConnection(url);		
		return conn;
	}

	@Override
	public ResultSet runQuery(String queryContent, Connection connect) throws SQLException {
		
		ResultSet rs = null;
		Statement stmt = connect.createStatement();
		if( stmt.execute(queryContent) ) {
			rs = stmt.getResultSet();			
		}	
         
        
        if (stmt != null) {
        	stmt.close(); 
    	}
        
		return rs;
	}

	@Override
	public void flush(Connection connect) throws SQLException {
		connect.close();
	}

}
