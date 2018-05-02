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
    	
    	final String TmpTableName = "TMP_TABLE";
    	
    	final int index_col_titulo = 22;
    	
    	final int index_col_titulo_reverse = 8;
    	
    	String separator = ",";
    	
    	String CommandCreate = 
    			"create table "+ TmpTableName + "(";    					
    	
    	String CommandInsert = 
    			"insert into "+ TmpTableName +" values(";   	
    	
    	String SQLCommandEnd = 
    			")";
    	
    	String carga = "";
    	
    	String filePath = 
    			"C:\\SQL\\";
    	
    	String fileName = 
    			"Dados_Projeto.csv";
    	
    	String sqlLiteFilename = "AADBPUCRS.db";
    	
    	String connectionString = 
    			"jdbc:sqlite:"+filePath+sqlLiteFilename;
    	
    	/*
    	 * String connectionString = 
    			"jdbc:sqlserver://sqlfacin:1433;databaseName=CRIAR;user=05109292;password=*****";        
        */
    	
    	
    	
    	try {
    		
    		
    		
    		test = new SQLLiteDataConnection();
    		Connection cnt = test.connectBase(connectionString);
            
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
            	
            	if (lineData.length > columns.length) {
            		
            		System.out.println("\n\nfound instance where title is irregular did it work? ");
            		
            		String[] correctArray = new String[columns.length] ;
            		String titulo_correto = "";
            		
            		for(int i = index_col_titulo; i< (lineData.length - index_col_titulo_reverse); i++ ) {
            			
            			titulo_correto += lineData[i];            			
            		}
            		
            		lineData[index_col_titulo] = titulo_correto;
            		
            		int move = lineData.length - (index_col_titulo_reverse);
            		
            		for (int j = index_col_titulo+1; j <= lineData.length-1; j++) {
            			
            			lineData [j] = lineData[move];
            			
            			if(move < (lineData.length-1)) {
            				
            				move ++;
            			}
            		}
            		
            		for (int k = 0; k < correctArray.length;k++) {
            			correctArray[k] = lineData[k];
            		}
            		lineData = null;
            		lineData = correctArray;
            	}
            	
            	for(String cell : lineData) {
            		carga += "'" + cell.replace("\"", "") + "',";
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
