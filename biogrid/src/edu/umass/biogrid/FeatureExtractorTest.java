/**
 * 
 */
package edu.umass.biogrid;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.lucene.queryParser.ParseException;

/**
 * @author qing
 *
 */
public class FeatureExtractorTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws NumberFormatException, ClassNotFoundException, SQLException, IOException, ParseException {
		if(args.length != 3){
			System.out.println("--src --dest --year");
			return;
		}
		FeatureExtractor extractor = new FeatureExtractor(Integer.parseInt(args[2]));
		Feature feature = extractor.getFeatureForPair(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		System.out.println(feature.toFriendlyString());
	}

}
