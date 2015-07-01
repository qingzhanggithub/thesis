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

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import edu.umass.biogrid.DatabaseAccess;
import edu.umass.biogrid.MEDLINECoOccurence;
import edu.umass.biogrid.MedlineSearcherSimple;

/**
 * @author qing
 *
 */
public class YearTool {
	
	private MEDLINECoOccurence co;
	private Sort yearSort;
	private DatabaseAccess databaseAccess;
	private Logger logger = Logger.getLogger(YearTool.class);
	public YearTool() throws IOException, ClassNotFoundException, SQLException{
		co = new MEDLINECoOccurence();
		SortField sortField = new SortField("year", SortField.STRING);
		yearSort = new Sort(sortField);
		databaseAccess = new DatabaseAccess();
	}
	
	public int getEarliestYear(int interactorID) throws SQLException, IOException{
		String symbols = databaseAccess.getSymbols(interactorID);
		String query = MedlineSearcherSimple.cleanStr(MedlineSearcherSimple.getQueryString(symbols));
		int year = -1;
		try {
			year = co.getMedlineSearcher().getEarliestYearByQuery(query, yearSort);
		} catch (ParseException e) {
			logger.error("Parsing ID "+interactorID+"\n"+e.getMessage());
		}
		return year;
	}
	
	public void getEarliestYearForAll(String path) throws IOException, SQLException, ParseException{
		FileWriter writer = new FileWriter(path+".earliest");
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int count = 0;
		while ((strLine = br.readLine()) != null){
			int interactor = Integer.parseInt(strLine.trim());
			int year = getEarliestYear(interactor);
			writer.append(String.valueOf(interactor)).append(",").append(String.valueOf(year)).append('\n');
			count ++;
			if(count % 1000 == 0)
				logger.info(count+" lines processed.");
		}
		writer.close();
		br.close();
		in.close();
		fstream.close();
		logger.info("== Task done. ==");
	}
	/**
	 * @param args
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException {
		if(args.length != 2){
			System.out.println("--id-path --db-table");
			return;
		}
		DatabaseAccess.INTERACTION = args[1];
		YearTool tool = new YearTool();
		tool.getEarliestYearForAll(args[0]);
	}

}
