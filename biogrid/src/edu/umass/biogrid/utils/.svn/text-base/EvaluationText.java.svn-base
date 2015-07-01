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
public class EvaluationText {
	
	private DatabaseAccess databaseAccess;
	
	public EvaluationText() throws ClassNotFoundException, SQLException{
		databaseAccess = new DatabaseAccess();
	}

	public String getSymbol(int interactorID) throws SQLException{
		String symbols = databaseAccess.getSymbols(interactorID);
		return symbols;
	}
	
	public int getOrgnism(int interactorID) throws SQLException{
		int orgn = databaseAccess.getOrgnismIDByInteractor(interactorID);
		return orgn;
	}
	
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		if(args.length !=2){
			System.out.println("--option(sym / orgn) --id");
			return;
		}
		EvaluationText eval = new EvaluationText();
		if(args[0].equalsIgnoreCase("sym"))
			System.out.println(eval.getSymbol(Integer.parseInt(args[1])));
		else if(args[0].equalsIgnoreCase("orgn"))
			System.out.println(eval.getOrgnism(Integer.parseInt(args[1])));
	}
}
