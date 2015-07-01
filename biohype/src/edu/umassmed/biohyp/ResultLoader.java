/**
 * 
 */
package edu.umassmed.biohyp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;

import edu.umass.biogrid.DatabaseAccess;
import edu.umass.biogrid.MedlineSearcherSimple;

/**
 * @author qing
 *
 */
public class ResultLoader {
	
	public static String SPACE ="&nbsp&nbsp&nbsp&nbsp";
	public static String BREAK = "<br>";
	private DatabaseAccess access;
	private MedlineSearcherSimple searcher;
	
	
	public ResultLoader() throws ClassNotFoundException, SQLException, IOException,ParseException{
		access = new DatabaseAccess();
		searcher = new MedlineSearcherSimple(MedlineSearcherSimple.INDEX_PATH);
	}
	
	public String getSnippet(int id) throws SQLException, CorruptIndexException, ParseException, IOException{
		String symbols = access.getSymbols(id);
		String query = MedlineSearcherSimple.cleanStr(MedlineSearcherSimple.getQueryString(symbols));
		List<Long> pmids = searcher.getPMIDsRegular(query, 5);
		int orgnism = access.getOrgnismIDByInteractor(id);
		StringBuffer sb = new StringBuffer();
		String[] symbolList = symbols.split("\\|");
		sb.append("<b>").append(symbolList[0]).append("</b>").append(BREAK).append(BREAK);
		
		if(symbolList.length >1){
			sb.append("Synonym(s):").append(SPACE);
			boolean isFirst = true;
			for(int i=0; i<symbolList.length; i++){
				if(isFirst){
					sb.append(symbolList[i]);
					isFirst = false;
				}else
					sb.append(",").append(SPACE).append(symbolList[i]);
			}
			sb.append(BREAK);
		}
		
		sb.append("NCBI Taxonomy ID:").append(SPACE);
		if(orgnism != -1){
			String ncbi="http://www.ncbi.nlm.nih.gov/taxonomy/?term="+orgnism;
			sb.append("<a href=\'").append(ncbi).append("\'>").append(orgnism).append("</a>");
		}
		else
			sb.append("Unknown");
		sb.append(BREAK);
		if(pmids.size() > 0){
			sb.append("PubMed References:").append(BREAK);
			
			
			for(long pmid: pmids){
				String url = "http://www.ncbi.nlm.nih.gov/pubmed/"+pmid;
				sb.append("<a href=\'").append(url).append("\'>").append("PMID ").append(pmid).append("</a>");
				sb.append(SPACE);
			}
			sb.append(BREAK);
		}
		return sb.toString();
	}
	
	public String getResultList(List<Integer> ids) throws CorruptIndexException, SQLException, ParseException, IOException{
		StringBuffer sb= new StringBuffer();
		int count =0;
		for(Integer id : ids){
			if(count %2 ==0)
				sb.append("<tr bgcolor=FFFFFF>\n<td>\n");
			else
				sb.append("<tr bgcolor=F3FAF5>\n<td>\n");
			String snippet = getSnippet(id);
			sb.append(snippet);
			sb.append("</td>\n</tr>\n");
			count ++;
		}
		return sb.toString();
	}
}
