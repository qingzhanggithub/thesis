/**
 * 
 */
package edu.umass.biogrid;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * @author qing
 *
 */
public class DatabaseAccess {
	public static String INTERACTION = "interaction";
	private BiogridDatabaseConnection databaseConnection;
	private String prepSql = "update "+INTERACTION+" set year=? where BioGRID_Interaction_ID= ?";
	private PreparedStatement prepStmt ;
	private Logger logger = Logger.getLogger(DatabaseAccess.class);
	public DatabaseAccess() throws ClassNotFoundException, SQLException{
		databaseConnection = BiogridDatabaseConnection.getInstance();
	}
	
	public void addYearToInteraction() throws SQLException{
		prepStmt = databaseConnection.getConnection().prepareStatement(prepSql);
		String sql = "select BioGRID_Interaction_ID, Author from "+INTERACTION;
		Statement stmt = databaseConnection.getConnection().createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(Integer.MIN_VALUE);
		ResultSet rs = stmt.executeQuery(sql);
		int count =0;
		while(rs.next()){
			int id = rs.getInt(1);
			String author = rs.getString(2);
			int  year = getYear(author);
			insertYear(id, year);
			count ++;
			if(count %1000 ==0)
				logger.info("count= "+count);
		}
		rs.close();
		stmt.close();
		prepStmt.close();
	}
	
	public List<Integer> getInteractorB(int interactorAID, int beforeYear) throws SQLException{
		String sql = "select distinct BioGRID_ID_Interactor_B from "+INTERACTION+" where BioGRID_ID_Interactor_A ="+interactorAID+" and year<"+beforeYear;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Integer> ids = new ArrayList<Integer>();
		while(rs.next()){
			ids.add(rs.getInt(1));
		}
		rs.close();
		stmt.close();
		return ids;
	}
	
	public List<Integer> getInteractorA(int interactorBID, int beforeYear) throws SQLException{
		String sql = "select distinct BioGRID_ID_Interactor_A from "+INTERACTION+" where BioGRID_ID_Interactor_B ="+interactorBID+" and year<"+beforeYear;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Integer> ids = new ArrayList<Integer>();
		while(rs.next()){
			ids.add(rs.getInt(1));
		}
		rs.close();
		stmt.close();
		return ids;
	}
	
	public Set<String> getPMIDofA(int interactorID, int beforeYear) throws SQLException{
		String sql="select distinct Pubmed_ID from "+INTERACTION+" where BioGRID_ID_Interactor_A ="+interactorID+" and year<"+beforeYear;
		Set<String> pmids = new HashSet<String>();
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			pmids.add(rs.getString(1).trim());
		}
		rs.close();
		stmt.close();
		return pmids;
	}
	
	public Set<String> getPMIDofB(int interactorID, int beforeYear) throws SQLException{
		String sql="select distinct Pubmed_ID from "+INTERACTION+" where BioGRID_ID_Interactor_B ="+interactorID+" and year<"+beforeYear;
		Set<String> pmids = new HashSet<String>();
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			pmids.add(rs.getString(1).trim());
		}
		rs.close();
		stmt.close();
		return pmids;
	}
	
	
	public boolean hasInteraction(int src, int dest, int beforeYear) throws SQLException{
		String sql ="select Pubmed_ID from "+INTERACTION+" where year<"+beforeYear+" and BioGRID_ID_Interactor_A="+src+" and BioGRID_ID_Interactor_B="+dest+" or  BioGRID_ID_Interactor_A="+dest+" and BioGRID_ID_Interactor_B="+src;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		boolean has= rs.next();
		rs.close();
		stmt.close();
		return has;
	}
	
	public String getOfficialSymbol(int interactorID) throws SQLException{
		String symbol = getOfficialSymbol(interactorID, "A");
		if(symbol == null){
			symbol = getOfficialSymbol(interactorID, "B");
		}
		return symbol;
	}
	
	public String getSystematicName(int interactorID) throws SQLException{
		String name = getSystematicName(interactorID, "A");
		if(name == null){
			name = getSystematicName(interactorID, "B");
		}
		return name;
	}
	
	public String getSystematicName(int interactorID, String column) throws SQLException{
		String sql = "select Systematic_Name_Interactor_"+column+" from "+INTERACTION+" where BioGRID_ID_Interactor_"+column+"= "+interactorID;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String symbol = null;
		if(rs.next()){
			symbol = rs.getString(1);
		}
		rs.close();
		stmt.close();
		return symbol;
	}
	public String getOfficialSymbol(int interactorID, String column) throws SQLException{
		String sql = "select Official_Symbol_Interactor_"+column+" from "+INTERACTION+" where BioGRID_ID_Interactor_"+column+"= "+interactorID;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String symbol = null;
		if(rs.next()){
			symbol = rs.getString(1);
		}
		rs.close();
		stmt.close();
		return symbol;
	}
	
	public String getSynonyms(int interactorID) throws SQLException{
		String symbol = getSynonyms(interactorID, "A");
		if(symbol == null){
			symbol = getSynonyms(interactorID, "B");
		}
		return symbol;
	}
	
	public String getSynonyms(int interactorID, String column ) throws SQLException{
		String sql ="select Synonyms_Interactor_"+column+" from "+INTERACTION+" where BioGRID_ID_Interactor_"+column+"= "+interactorID;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String syn = null;
		if(rs.next()){
			syn = rs.getString(1);
		}
		rs.close();
		stmt.close();
		return syn;
	}
	
	public List<String> getExperimentalSystemType(int interactorA, int interactorB, int year) throws SQLException{
		String sql ="select distinct Experimental_System_Type from "+INTERACTION+" where BioGRID_ID_Interactor_A="+interactorA+" AND BioGRID_ID_Interactor_B="+interactorB;
		if(year != -1)
			sql +=" AND year="+year;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<String> types = new ArrayList<String>();
		while(rs.next()){
			types.add(rs.getString(1));
		}
		rs.close();
		stmt.close();
		return types;
	}
	
	
	public Set<String> getPMIDs(int interactorID, int beforeYear) throws SQLException{
		Set<String> setA = getPMIDofA(interactorID, beforeYear);
		Set<String> setB = getPMIDofB(interactorID, beforeYear);
		setA.addAll(setB);
		return setA;
		
	}
	
	public int getOrgnismIDByInteractor(int interactorID) throws SQLException{
		String sql = "select Organism_Interactor from orgnism where BioGRID_ID_Interactor="+interactorID;
		int id = -1;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()){
			id = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		return id;
	}
	
	public static int getYear(String str){
		int start = str.indexOf('(');
		int end = str.indexOf(')');
		if(start != -1 && end != -1){
			String yearStr = str.substring(start+1, end);
			return Integer.parseInt(yearStr);
		}
		return -1;
	}
	
	private void insertYear(int id, int year) throws SQLException{
		prepStmt.setInt(1, year);
		prepStmt.setInt(2, id);
		prepStmt.executeUpdate();
	}
	
	
	public boolean exisitBeforeYear(int src, int dest, int year) throws SQLException{
		String sql = "select * from "+INTERACTION +
				" where year < "+year +" AND BioGRID_ID_Interactor_A="+src+" AND BioGRID_ID_Interactor_B="+dest+" OR BioGRID_ID_Interactor_A="+dest+" AND BioGRID_ID_Interactor_B="+src;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		boolean flag = rs.next();
		rs.close();
		stmt.close();
		return flag;
	}
	
	public String getSymbols(int interactorID) throws SQLException{
		String officialSymbol = getOfficialSymbol(interactorID);
		String synonyms = getSynonyms(interactorID);
		String name = getSystematicName(interactorID);
		String total = officialSymbol;
		
		if(synonyms != null && !synonyms.equals("-"))
			total += "|"+synonyms;
		if(name != null && !name.equals("-"))
			total += "|"+name;
		return total;
	}
	
	public String getEmailByPMID(long pmid) throws SQLException{
		String sql = "select email from medline_contact where pmid="+pmid;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String email = null;
		if(rs.next()){
			email = rs.getString(1);
		}
		rs.close();
		stmt.close();
		return email;
	}
	
	public boolean interactorIDexist(int id) throws SQLException{
		String sql = "select * from "+INTERACTION+" where BioGRID_ID_Interactor_A ="+id+" or BioGRID_ID_Interactor_B="+id;
		Statement stmt = databaseConnection.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		boolean flag  = rs.next();
		rs.close();
		stmt.close();
		return flag;
	}
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		if(args.length >0)
			DatabaseAccess.INTERACTION = args[0];
		DatabaseAccess access = new DatabaseAccess();
		access.addYearToInteraction();
	}

}
