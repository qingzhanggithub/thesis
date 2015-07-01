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
import java.util.List;

import edu.umass.biogrid.DatabaseAccess;

/**
 * @author qing
 *
 */
public class ExperimentSystemType {
	
	public void processFile(String path, int year) throws ClassNotFoundException, SQLException, IOException{
		DatabaseAccess access = new DatabaseAccess();
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		FileWriter writer = new FileWriter(path+".systype");
		  while ((strLine = br.readLine()) != null){
			 String[] fields = strLine.split(",");
			 List<String> types = access.getExperimentalSystemType(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), year);
			 writer.append(fields[0]).append(",").append(fields[1]).append("\t").append(getTypeString(types)).append('\n');
		  }
		  writer.close();
		  br.close();
		  in.close();
		  fstream.close();
	}
	
	public String getTypeString(List<String> types){
		StringBuffer sb = new StringBuffer();
		if(types.size() > 0){
			sb.append(types.get(0));
			if(types.size() >1){
				for(int i=1; i<types.size(); i++){
					sb.append(',').append(types.get(i));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, NumberFormatException, IOException {
		if(args.length != 3){
			System.out.println("--path --year --db-table");
			return;
		}
		
		DatabaseAccess.INTERACTION = args[2];
		ExperimentSystemType systype = new ExperimentSystemType();
		systype.processFile(args[0], Integer.parseInt(args[1]));
		System.out.println("Finished for "+args[0]);
	}

}
