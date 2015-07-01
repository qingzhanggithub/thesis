package edu.umass.biogrid.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import edu.umass.biogrid.DatabaseAccess;

public class IDCheckerForOrganism {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		if(args.length != 2){
			System.out.println("--path --db-table");
			return;
		}
		DatabaseAccess.INTERACTION = args[1];
		DatabaseAccess database = new DatabaseAccess();
		FileInputStream fstream = new FileInputStream(args[0]);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		FileWriter writer = new FileWriter(args[0]+".intable");
		  while ((strLine = br.readLine()) != null){
			  String[] fields = strLine.split(",");
			  boolean flag = database.interactorIDexist(Integer.parseInt(fields[0]));
			  if(flag)
				  writer.append(strLine).append('\n');
		  }
		  writer.close();
		  in.close();
		  fstream.close();
	}

}
