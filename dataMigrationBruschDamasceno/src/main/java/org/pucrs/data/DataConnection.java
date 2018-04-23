package org.pucrs.data;

public interface DataConnection {
	
	public void connectBase( String connString );
	
	public void runQuery( String queryContent );

	public void dataBaseFromCSV(String filePath);
	
}
