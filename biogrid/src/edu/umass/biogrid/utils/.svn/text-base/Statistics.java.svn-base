/**
 * 
 */
package edu.umass.biogrid.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;


import edu.umass.biogrid.DatabaseAccess;

/**
 * @author qing
 *
 */
public class Statistics {
	
	private DatabaseAccess databaseAccess;
	
	public Statistics() throws ClassNotFoundException, SQLException{
		databaseAccess = new DatabaseAccess();
	}
	
	public void getDegree(String path) throws IOException, SQLException{
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int count = 0;
		FileWriter writer = new FileWriter(path+".degree");
		  while ((strLine = br.readLine()) != null){
			 int id = Integer.parseInt(strLine);
			 int degree = databaseAccess.getInteractorA(id, 2014).size()+ databaseAccess.getInteractorB(id, 2014).size();
			 writer.append(strLine).append(",").append(String.valueOf(degree)).append('\n');
			 count ++;
			 if(count % 1000 == 0)
				 System.out.println(count+" processed.");
		  }
		  writer.close();
	}

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		if(args.length != 1){
			System.out.println("--path");
			return;
		}
		Statistics stat = new Statistics();
		stat.getDegree(args[0]);

	}

}
