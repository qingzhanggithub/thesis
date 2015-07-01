/**
 * 
 */
package edu.umass.biogrid.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;

import pmidmapper.MedlineSearcher;
import pmidmapper.PMArticle;

import edu.umass.biogrid.DatabaseAccess;
import edu.umass.biogrid.MedlineSearcherSimple;

/**
 * @author qing
 *
 */
public class MeSHCounter {
	
	private MedlineSearcherSimple medlineSearch;
	private MedlineSearcher searcher;
	private Logger logger = Logger.getLogger(MeSHCounter.class);
	
	public MeSHCounter() throws IOException{
		medlineSearch = new MedlineSearcherSimple(MedlineSearcherSimple.INDEX_PATH);
		searcher = new MedlineSearcher(MedlineSearcher.defaultMedlineIndexPath);
	}
	
	public float getMeSHHistogramPieceOfYear(String keywords, String year) throws IOException, ParseException{
		String query = "("+keywords+") AND year:"+year;
		String yearQuery = "year:"+year;
		if(MedlineSearcherSimple.MESH_FIELD != null){
			query += " AND mesh:"+MedlineSearcherSimple.MESH_FIELD;
			yearQuery += " AND mesh:"+MedlineSearcherSimple.MESH_FIELD;
		}
		int computational = medlineSearch.getMatchNumByQuery(query);
		int total = medlineSearch.getMatchNumByQuery(yearQuery);
		if(total == 0)
			return -1.0f;
		float pct = computational * 1.0f /total;
		return pct;
	}
	
	public void getArticleHistogramByMash(String query){
		
	}
	
	public void getMeshOfForAllPMIDs(String path) throws IOException, ParseException{
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int count = 0;
		long start = System.currentTimeMillis();
		FileWriter writer = new FileWriter(path+".mash");
		  while ((strLine = br.readLine()) != null){
			  long pmid = Long.parseLong(strLine.trim());
			  PMArticle article = searcher.getPMArticleByPMID(pmid, false);
			  if(article != null && article.getMeshs() != null){
				  List<String> meshs = MedlineSearcher.parseMeshs(article.getMeshs());
				  for(String mesh: meshs){
					  writer.append(mesh).append('\n');
				  }
			  }
			  count ++;
			  if(count %100 == 0){
				  logger.info(count+" articles have been processed for mesh.");
			  }
				  
		  }
		  writer.close();
		  br.close();
		  in.close();
		  fstream.close();
		  long total = (System.currentTimeMillis() - start)/1000/60;
		  logger.info("Finished: All the meshes have been extracted for "+path+". Time used :"+total);
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		if(args.length <3){
			System.out.println("--year --db-tablename --keywords...");
			return;
		}
		DatabaseAccess.INTERACTION = args[1];
		MedlineSearcherSimple.MESH_FIELD = OrganismTool.getMeSH(args[1]);
		
		StringBuffer sb = new StringBuffer();
		for(int i=2; i<args.length; i++){
			sb.append(args[i]);
		}
		MeSHCounter counter = new MeSHCounter();
		float pct = counter.getMeSHHistogramPieceOfYear(sb.toString(), args[0]);
		System.out.println("pct,"+pct);
	}

}
