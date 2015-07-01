/**
 * 
 */
package edu.umass.biogrid.utils;

import java.sql.SQLException;

import edu.umass.biogrid.DatabaseAccess;

/**
 * @author qing
 *
 */
public class SymbolTool {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws NumberFormatException, SQLException, ClassNotFoundException {
		if(args.length !=1){
			System.out.println("--interactor");
			return;
		}
		DatabaseAccess database = new DatabaseAccess();
		String sym = database.getSymbols(Integer.parseInt(args[0]));
		System.out.println(sym);
	}

}
