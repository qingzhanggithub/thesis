/**
 * 
 */
package edu.umass.biogrid;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
/**
 * @author qing
 *
 */
public class MEDLINECoOccurence {
	
	private DatabaseAccess databaseAccess;
	private MedlineSearcherSimple medlineSearcher;
	private Logger logger = Logger.getLogger(MEDLINECoOccurence.class);
	
	public MEDLINECoOccurence() throws ClassNotFoundException, SQLException, IOException{
		databaseAccess = new DatabaseAccess();
		medlineSearcher = new MedlineSearcherSimple(MedlineSearcherSimple.INDEX_PATH);
	}
	
	public Coocurence  getCoOccurence(int interactorIDA, int interactorIDB, int year) throws SQLException, IOException, ParseException{
		String symbolsA = databaseAccess.getSymbols(interactorIDA);
		String symbolsB = databaseAccess.getSymbols(interactorIDB);
		logger.info("symbols of "+interactorIDA+": "+symbolsA+"\nsymbols of "+interactorIDB+": "+symbolsB);
		if(symbolsA ==null || symbolsB == null)
			return null;
//		BooleanQuery queryA = getQuery(symbolsA);
//		BooleanQuery queryB = getQuery(symbolsB);
//		BooleanQuery interQuery = new BooleanQuery();
//		interQuery.add(queryA, Occur.MUST);
//		interQuery.add(queryB, Occur.MUST);
		String queryA = MedlineSearcherSimple.cleanStr(MedlineSearcherSimple.getQueryString(symbolsA));
		String queryB = MedlineSearcherSimple.cleanStr(MedlineSearcherSimple.getQueryString(symbolsB));
		String interQuery = "("+queryA+") AND ("+queryB+") AND year:[* TO "+(year-1)+"]";
		Coocurence occurence = new Coocurence();
		String queryAFull = "("+queryA+") AND year:[* TO "+(year-1)+"]";
		String queryBFull = "("+queryB+") AND year:[* TO "+(year-1)+"]";
		String queryABFull = "("+queryA+" "+queryB+") AND year:[* TO "+(year-1)+"]";
		if(MedlineSearcherSimple.MESH_FIELD != null){
			queryAFull += queryAFull +" AND mesh:"+MedlineSearcherSimple.MESH_FIELD;
			queryBFull += queryBFull +" AND mesh:"+MedlineSearcherSimple.MESH_FIELD;
			queryABFull += queryABFull + " AND mesh:"+MedlineSearcherSimple.MESH_FIELD;
		}
		
		occurence.src = medlineSearcher.getMatchNumByQuery(queryAFull);
		occurence.srcPMIDs = medlineSearcher.getPMIDByQuery(queryAFull);
		
		occurence.dest = medlineSearcher.getMatchNumByQuery(queryBFull);
		occurence.destPMIDs  = medlineSearcher.getPMIDByQuery(queryBFull);
		
		occurence.cooccur = medlineSearcher.getMatchNumByQuery(interQuery);
//		BooleanQuery unionQuery = new BooleanQuery();
//		unionQuery.add(queryA, Occur.SHOULD);
//		unionQuery.add(queryB, Occur.SHOULD);
//		unionQuery.setMinimumNumberShouldMatch(1);
		occurence.union = medlineSearcher.getMatchNumByQuery("("+queryA+" "+queryB+") AND year:[* TO "+(year-1)+"]");
		logger.info("\n"+occurence.toString()+"\n");
		return occurence;
	}	
	

	public MedlineSearcherSimple getMedlineSearcher() {
		return medlineSearcher;
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, NumberFormatException, ParseException {
		if(args.length != 4){
			System.out.println("--interactorID_A --interactorID_B --return --year");
			return;
		}
		MedlineSearcherSimple.NUM_OF_SEARCH_RESTURN = Integer.parseInt(args[2]);
		MEDLINECoOccurence occurence = new MEDLINECoOccurence();
		Coocurence occr = occurence.getCoOccurence(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[3]));
		System.out.println(occr.toString());
		float jaccard = occr.cooccur *1.0f /occr.union;
		System.out.println("Jaccard:"+jaccard);
	}

}
