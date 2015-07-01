/**
 * 
 */
package edu.umass.biogrid;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.umass.biogrid.utils.OrganismTool;

/**
 * @author qing
 *
 */
public class NegativeGenerator {
	
	private DatabaseAccess databaseAccess;
	private int year;
	
	public NegativeGenerator(int year) throws ClassNotFoundException, SQLException{
		databaseAccess = new DatabaseAccess();
		this.year = year;
	}

	public void genPairs(String path, int targetSize) throws IOException, SQLException{
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int count = 0;
		FileWriter writer = new FileWriter(path+".pair"+year+"_"+targetSize);
		List<Integer> list = new ArrayList<Integer>();
		while ((strLine = br.readLine()) != null){
			 list.add(Integer.parseInt(strLine));
		}
		
		int i=0;
		while(i<list.size()-targetSize){
			for(int j=1; j<=targetSize; j++){
				boolean has = databaseAccess.hasInteraction(list.get(i), list.get(i+j), year);
				if(!has){
					writer.append(String.valueOf(list.get(i))).append("\t").append(String.valueOf(list.get(i+j))).append("\n");
					count ++;
					if(count %1000 ==0){
						System.out.println(count+" pairs generated ..");
					}
				}else
					System.out.println("pair("+i+","+j+") "+list.get(i)+","+list.get(i+j)+" exist!");
			}
			i++;
		}
		writer.close();
		System.out.println("Finish generating pairs..");
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		if(args.length != 4){
			System.out.println("--path --target-size --year --db-table");
			return;
		}
		DatabaseAccess.INTERACTION = args[3];
		MedlineSearcherSimple.MESH_FIELD = OrganismTool.getMeSH(args[3]);
		NegativeGenerator negGen = new NegativeGenerator(Integer.parseInt(args[2]));
		negGen.genPairs(args[0], Integer.parseInt(args[1]));
	}

}
