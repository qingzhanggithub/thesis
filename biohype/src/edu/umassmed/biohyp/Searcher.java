/**
 * 
 */
package edu.umassmed.biohyp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;

import edu.umass.biogrid.DatabaseAccess;
import edu.umass.biogrid.FeatureExtractor;
import edu.umass.biogrid.MedlineSearcherSimple;
import edu.umass.biogrid.utils.OrganismTool;

/**
 * @author qing
 *
 */
public class Searcher {
	
	private FeatureExtractor extractor ;
	private ResultLoader loader;
	
	public Searcher(String dbName) throws ClassNotFoundException, SQLException, IOException, ParseException{
		DatabaseAccess.INTERACTION = dbName;
		MedlineSearcherSimple.MESH_FIELD = OrganismTool.getMeSH(dbName);
		extractor = new FeatureExtractor(2015);
		loader = new ResultLoader();
	}
	
	public List<Integer> randomSample(int size){
		//TODO
		return null;
	}
	
	public int getIDBySymbol(String symbol){
		return -1;
	}

	public void getFeatures(int query, List<Integer> samples){
		
	}
	/**
	 * For test purpose only.
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	public String testSearch(String query) throws IOException, SQLException, ParseException{
		List<Integer> list = FileUtil.readInteractorList("/home/qzhang/interaction/"+query+".txt");
		String result = loader.getResultList(list);	// get the HTML result list. add in <table> when display
		return result;
	}
	
	
}
