/**
 * 
 */
package edu.umass.biogrid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author qing
 *
 */
public class MedlineSearcherSimple {
	public static String INDEX_PATH = "/home/data_user/pubmed_index";
	private IndexSearcher searcher;
	private IndexReader reader ;
	public static int NUM_OF_SEARCH_RESTURN = 10000;
	private int numDocs = 0;
	private Sort yearReversedSort;
	protected QueryParser parser = new QueryParser(Version.LUCENE_35, "abstract", new StandardAnalyzer(Version.LUCENE_35));
	public static String MESH_FIELD = null;
	public MedlineSearcherSimple(String indexPath) throws IOException{
		NIOFSDirectory directory;
		directory = new NIOFSDirectory(new File(indexPath));
		reader = IndexReader.open(directory, true);
		searcher = new IndexSearcher(reader);
		numDocs = reader.numDocs();
		SortField sortField = new SortField("year", SortField.STRING, true);
		yearReversedSort = new Sort(sortField);
	}
	
	public int getMatchNumByQuery(String str) throws IOException, ParseException{
		Query query = parser.parse(str);
		 TopDocs hits  = searcher.search(query, 1);
		 return hits.totalHits;
	}
	
	public List<String> getPMIDByQuery(String str) throws IOException, ParseException{
		Query query = parser.parse(str);
		 TopDocs hits  = searcher.search(query, 100);
		 ScoreDoc[] docs = hits.scoreDocs;
		 List<String> pmids = new ArrayList<String>();
		 if(docs != null){
			 for(int i=0; i<docs.length ; i++){
				 Document document = searcher.getIndexReader().document(docs[i].doc);
				 pmids.add(document.get("id"));
			 }
		 }
		 return pmids;
	}
	
	public int getEarliestYearByQuery(String str, Sort sort) throws ParseException, IOException{
		Query query = parser.parse(str);
		TopDocs hits = searcher.search(query, 1, sort);
		ScoreDoc[] docs = hits.scoreDocs;
		if(docs != null && docs.length >0){
			Document document = searcher.getIndexReader().document(docs[0].doc);
			String year = document.get("year");
			if(year != null)
				return Integer.parseInt(year);
		}
		return -1;
	}
	
	public List<Long> getLatestPMIDs(String str, int size) throws ParseException, IOException{
		Query query = parser.parse(str);
		TopDocs hits = searcher.search(query, size, yearReversedSort);
		ScoreDoc[] docs = hits.scoreDocs;
		List<Long> pmids = new ArrayList<Long>();
		if(docs != null && docs.length >0){
			for(int i=0; i<docs.length; i++){
				Document document = searcher.getIndexReader().document(docs[i].doc);
				String pmidStr = document.get("id");
				if(pmidStr != null){
					pmids.add(Long.parseLong(pmidStr));
				}
			}
		}
		return pmids;
	}
	
	public List<Long> getPMIDsRegular(String str, int size) throws ParseException, CorruptIndexException, IOException{
		Query query = parser.parse(str);
		TopDocs hits = searcher.search(query, size);
		ScoreDoc[] docs = hits.scoreDocs;
		List<Long> pmids = new ArrayList<Long>();
		if(docs != null && docs.length >0){
			for(int i=0; i<docs.length; i++){
				Document document = searcher.getIndexReader().document(docs[i].doc);
				String pmidStr = document.get("id");
				if(pmidStr != null){
					pmids.add(Long.parseLong(pmidStr));
				}
			}
		}
		return pmids;
	}
	
	public static BooleanQuery getQuery(String namesStr){
		String[] names = namesStr.split("|");
		BooleanQuery query = new BooleanQuery();
		for(String name: names){
			query.add(new TermQuery(new Term("abstract", name)),  Occur.SHOULD);
		}
		query.setMinimumNumberShouldMatch(1);
		return query;
	}
	
	public static String getQueryString(String namesStr){
		
		return namesStr.replace('|', ' ');
	}
	
	public static String cleanStr(String str){
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if(c=='[' || c==']' || c=='(' || c==')'|| c=='\"' || c=='?' || c=='\'' || c=='{' || c=='}' || c=='!' || c==':')
				continue;
			sb.append(c);
		}
		return sb.toString();
	}
	
	public int getNumDocs() {
		return numDocs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
