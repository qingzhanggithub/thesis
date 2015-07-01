/**
 * 
 */
package edu.umass.biogrid;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.lucene.queryParser.ParseException;

/**
 * @author qing
 *
 */
public class Evaluation {
	
	private DatabaseAccess database;
	private int beforeYear = 2014;
	private MedlineSearcherSimple medlineSearcher;
//	private Logger logger = Logger.getLogger(Evaluation.class);
	public Evaluation() throws ClassNotFoundException, SQLException, IOException{
		database = new DatabaseAccess();
		medlineSearcher = new MedlineSearcherSimple(MedlineSearcherSimple.INDEX_PATH);
	}
	
	public int getRandomNeighbor(int interactor) throws SQLException{
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(database.getInteractorA(interactor, beforeYear));
		set.addAll(database.getInteractorB(interactor, beforeYear));
		Integer[] list = new Integer[set.size()];
		set.toArray(list);
		Random rand = new Random();
		int index = rand.nextInt(list.length);
		return list[index];
	}
	
	public List<String> getContacts(int interactor, int size) throws SQLException, ParseException, IOException{
		String symbols = database.getSymbols(interactor);
		String query = MedlineSearcherSimple.cleanStr(MedlineSearcherSimple.getQueryString(symbols));
		Set<Long> pmids = new HashSet<Long>(medlineSearcher.getLatestPMIDs(query, size));
		Set<String> biogridPMIDs = database.getPMIDs(interactor, beforeYear);
		for(String pmidStr : biogridPMIDs){
			pmids.add(Long.parseLong(pmidStr));
		}
		List<String> emails = new ArrayList<String>();
		for(long pmid: pmids){
			String email = database.getEmailByPMID(pmid);
			if(email !=null)
				emails.add(email);
//			logger.info("pmid,"+pmid+"\t"+email);
		}
		return emails;		
	}
	
	public String processInteractor(int interactor, int sizeOfSearch, int sizeOfContact) throws SQLException, ParseException, IOException{
		StringBuffer sb = new StringBuffer();
		int neighbor = getRandomNeighbor(interactor);
		sb.append("interactor,").append(interactor).append('\n');
		sb.append("neighbor,").append(neighbor).append('\n');
		List<String> contacts = getContacts(interactor, sizeOfSearch);
		int count = 0;
		for(String contact : contacts){
			sb.append("contact,").append(contact).append('\n');
			count ++;
			if(count == sizeOfContact)
				break;
		}
		return sb.toString();
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
		if(args.length != 3){
			System.out.println("--interactor --search-size --contact-size");
			return;
		}
		Evaluation evaluation = new Evaluation();
		String content = evaluation.processInteractor(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		System.out.println(content);
	}
}
