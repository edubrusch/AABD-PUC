package org.pucrs.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataConnection {
	
	public Connection connectBase( String connString ) throws SQLException;
	
	public ResultSet runQuery( String queryContent, Connection connect ) throws SQLException;

	public void flush( Connection connect ) throws SQLException;
	
}
