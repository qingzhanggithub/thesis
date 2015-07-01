/**
 * 
 */
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
import edu.umass.biogrid.MedlineSearcherSimple;

/**
 * @author qing
 *
 */
public class PairTool {

	
	public static void getNewPairAfterYear(String path, int year, String sep) throws IOException, ClassNotFoundException, SQLException{
		DatabaseAccess db = new DatabaseAccess();
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int count = 0;
		FileWriter writer = new FileWriter(path+".new");
		  while ((strLine = br.readLine()) != null){
			  String[] fields = strLine.split(sep);
			  boolean has = db.exisitBeforeYear(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), year);
			  if(!has){
				  writer.append(strLine).append("\n");
			  }
			  count++;
			  if(count % 1000 ==0)
				  System.out.println(count+" lines checked.");
		  }
		  writer.close();
		  in.close();
		  fstream.close();
		  System.out.println("== Task done. ==");
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException, ClassNotFoundException, SQLException {
		if(args.length != 4){
			System.out.println("--path --year --sep --db-table");
			return;
		}
		DatabaseAccess.INTERACTION = args[3];
		MedlineSearcherSimple.MESH_FIELD = OrganismTool.getMeSH(args[3]);
		PairTool.getNewPairAfterYear(args[0], Integer.parseInt(args[1]), args[2]);
	}

}
