/**
 * 
 */
package edu.umass.biogrid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.index.TermFreqVector;
import org.apache.lucene.queryParser.ParseException;

import edu.uwm.elsevier.authoranalysis.InterDisipline;

import pmidmapper.MedlineSearcher;
import pmidmapper.PMArticle;

/**
 * @author qing
 *
 */
public class TextSimilarity {

	public static String TEXT_FIELD = "abstract";
	private MedlineSearcher indexAccess;
	private static Logger logger = Logger.getLogger(TextSimilarity.class);
	public TextSimilarity() throws IOException{
		indexAccess = new MedlineSearcher(MedlineSearcher.defaultMedlineIndexPath);
	}
	
	public Map<String, Float> getTFIDF(Set<String> pmids) throws NumberFormatException, ParseException, IOException{
		List<TermFreqVector[]> termVecList = new ArrayList<TermFreqVector[]>();
		for(String pmidStr: pmids){
			TermFreqVector[] termVecs =indexAccess.getTermVec(Long.parseLong(pmidStr.trim()));
			if(termVecs != null)
				termVecList.add(termVecs);
		}
		
		if(termVecList.size() ==0){
			logger.info("TermVecList.size()==0. There is not content for all these pmids of size "+pmids.size());
			return null;
		}
		List<Map<String, TermFreqVector>> mapList = new ArrayList<Map<String,TermFreqVector>>();
		for(int i=0; i< termVecList.size() ; i++){
			TermFreqVector[] termVecs = termVecList.get(i);
			Map<String, TermFreqVector> fieldMap = new HashMap<String, TermFreqVector>();
			for(TermFreqVector vec: termVecs){
				fieldMap.put(vec.getField(), vec);
//				logger.info("doc"+i+"\tfield:"+vec.getField());
			}
			mapList.add(fieldMap);
		}
		
		Map<String, Integer> tfMap = InterDisipline.mergeTermVectors(mapList, TEXT_FIELD); // use abstract only for now.
		Map<String, Float> idfMap = indexAccess.getIDFForField(tfMap.keySet(), TEXT_FIELD);
		Map<String, Float> tfidfMap = InterDisipline.getTFIDFMap(tfMap, idfMap);
		
		return tfidfMap;
	}
	
	public Set<String> getMeshSet(Set<String> pmids) throws NumberFormatException, ParseException, IOException{
		Set<String> meshSet = new HashSet<String>();
		for(String pmid: pmids){
			PMArticle article = indexAccess.getPMArticleByPMID(Long.parseLong(pmid), false);
			if(article ==null)
				continue;
			String meshStr = article.getMeshs();
			List<String> meshs = MedlineSearcher.parseMeshs(meshStr);
			for(String mesh: meshs){
				String[] tokens = mesh.toLowerCase().split("\\s+");
				for(String token: tokens)
					meshSet.add(token);
			}
			
		}
		return meshSet;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
