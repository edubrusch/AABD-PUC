package org.pucrs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.pucrs.data.DataConnection;
import org.pucrs.data.SQLLiteDataConnection;

/**
 * convert data
 *
 */
public class App 
{
    public static void main( String[] args ) {
    	DataConnection test;
    	
    	final String TmpTableName = "TMP_supah_table";
    	
    	String separator = ";";
    	
    	String CommandCreate = 
    			"create table "+ TmpTableName + "(";    					
    	
    	String CommandInsert = 
    			"insert into "+ TmpTableName +" values(";   	
    	
    	String SQLCommandEnd = 
    			")";    	
    	
    	String filePath = 
    			"C:\\Users\\E_Brusch\\";
    	
    	String fileName = 
    			"Dados_Projeto.csv";
    	
    	String SQLLite = 
    			"jdbc:sqlite:C:/Users/E_Brusch/AADBPUCRS.db";
    	
    	/**
    	 * Colocar username e password
    	 * user=05109292;password=*****
    	 * 
    	 */
    	String SQLServer = 
    			"jdbc:sqlserver://sqlfacin:1433;databaseName=CRIAR;user=05109292;password=*****";        
        
    	String carga = "";
    	
    	try {
    		
    		
    		
    		test = new SQLLiteDataConnection();
    		Connection cnt = test.connectBase(SQLLite);
            
            File excel = new File(filePath+fileName);
            
            BufferedReader reader = new 
    				BufferedReader(new FileReader(excel));
       		
            //read first line
            String line = reader.readLine();
            
            String[] columns = line.split(separator);
            
        	for(String columnSrt : columns) {
            	carga += columnSrt +" varchar(50),";
			}           
        	
        	carga = carga.substring(0, carga.length()-1);
        	
            
            test.runQuery(CommandCreate+carga+SQLCommandEnd, cnt);
            System.out.println("Command run: " + CommandCreate+carga+SQLCommandEnd);
            
            carga = "";
            
            //read second line
            line = reader.readLine();
            
            while (line != null) {            	
            	
            	String[] lineData = line.split(separator);
            	
            	for(String cell : lineData) {
            		carga += "'" + cell + "',";
            	} 
            	
            	carga = carga.substring(0, carga.length()-1);
            	            	
            	test.runQuery(CommandInsert + carga+ SQLCommandEnd, cnt);
            	System.out.println("Command run: " + CommandInsert + carga+ SQLCommandEnd);
            	carga = "";
            	
            	line = reader.readLine();            	
            }
       		
       		
    		
    		} catch (SQLException e) {
    			System.out.println("ERRO: problema com a conexão.\n\nSql executado:\n\n"+ carga +"\n\nErro obtido:\n\n"+ e.getStackTrace() + "\n\nPilha:\n"+e.getStackTrace());
    			e.printStackTrace();
    		} catch (IOException ex) {
    			System.out.println("ERRO: problema com o arquivo csv.\n\nErro obtido:"+ ex.getStackTrace() + "\n\nPilha:\n"+ex.getStackTrace());
    			ex.printStackTrace();
    		}
        
    }
}
