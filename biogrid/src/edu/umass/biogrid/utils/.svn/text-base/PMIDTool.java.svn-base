/**
 * 
 */
package edu.umass.biogrid.utils;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;

import edu.umass.biogrid.MedlineSearcherSimple;

/**
 * @author qing
 *
 */
public class PMIDTool {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws IOException, NumberFormatException, ParseException {
		if(args.length != 2){
			System.out.println("--symbols --return-size");
			return;
		}
		
		MedlineSearcherSimple medlineSearcher = new MedlineSearcherSimple(MedlineSearcherSimple.INDEX_PATH);
		String query = MedlineSearcherSimple.cleanStr(MedlineSearcherSimple.getQueryString(args[0]));
		List<Long> pmids = medlineSearcher.getPMIDsRegular(query, Integer.parseInt(args[1]));
		if(pmids.size()> 0)
			System.out.println(pmids.get(0));
	}

}
