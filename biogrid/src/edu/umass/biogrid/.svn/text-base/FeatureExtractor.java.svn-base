/**
 * 
 */
package edu.umass.biogrid;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;

import edu.umass.biogrid.utils.OrganismTool;
import edu.uwm.elsevier.authoranalysis.InterDisipline;


/**
 * @author qing
 *
 */
public class FeatureExtractor {
	
	private DatabaseAccess databaseAccess;
	private TextSimilarity textSimilarity;
	private MEDLINECoOccurence occurence;
	private int beforeYear;
	private Logger logger = Logger.getLogger(FeatureExtractor.class);
	public FeatureExtractor(int beforeYear) throws ClassNotFoundException, SQLException, IOException{
		databaseAccess = new DatabaseAccess();
		this.beforeYear = beforeYear;
		textSimilarity = new TextSimilarity();
		occurence = new MEDLINECoOccurence();
	}
	
	public Feature getFeatureForPair(int src, int dest, int year) throws SQLException, NumberFormatException, ParseException, IOException{
		this.beforeYear = year;
		Set<Integer> srcNeighbors = getNeighbors(src);		
		Set<Integer> destNeighbors = getNeighbors(dest);
		Coocurence occr = occurence.getCoOccurence(src, dest, year);
		if(occr ==null)
			return null;
		Feature feature = new Feature();
		feature.sumNeighbor = srcNeighbors.size() + destNeighbors.size();
		Set<Integer> common = getIntersection(srcNeighbors, destNeighbors);
		Set<Integer> union = getUnion(srcNeighbors, destNeighbors);
		feature.numCommonFriend = common.size();
		feature.jaccard = (union.size()==0?0:common.size()* 1.0f /union.size());
		float sum = 0;
		for(int id: common){
			int nei = getNeighbors(id).size();
			if(nei > 1)	// if nei <2, there is error in the graph
				sum += 1.0f/Math.log(nei);
			else{
				logger.error("Adamic error: node "+id+" of common neighbor "+src+" and "+dest+" has less than 2 neighbors.");
			}
		}
		feature.adamic = sum;
//		feature.simText = getSimText(src, dest);
		feature.simText =0.0f;
		feature.simMesh = getSimMesh(new HashSet<String>(occr.srcPMIDs), new HashSet<String>(occr.destPMIDs));
//		feature.sumPub = databaseAccess.getPMIDs(src, beforeYear).size() + databaseAccess.getPMIDs(dest, beforeYear).size();	// 	get from BioGRID
		feature.sumPub = occr.src +occr.dest;	// get from MEDLINE
//		feature.jaccardArticleCoOccur = getArticleCoOccur(src, dest);
		feature.jaccardArticleCoOccur =(occr.union==0?0:occr.cooccur *1.0f/ occr.union);	//4/23/2013 fixed NaN problem caused by occr.union =0
		feature.sumClusteringCoef = getClusteringCoef(src)+getClusteringCoef(dest);
//		System.out.println(src+","+dest+"\n"+feature.toFriendlyString()+"\n---");
		return feature;
	}
	
	
	public float getArticleCoOccur(int src, int dest) throws SQLException{
		Set<String> srcArticles = databaseAccess.getPMIDs(src, beforeYear);
		Set<String> destArticles = databaseAccess.getPMIDs(dest, beforeYear);
		int count = 0;
		for(String srcPmid: srcArticles){
			if(destArticles.contains(srcPmid))
				count++;
		}
		srcArticles.addAll(destArticles);
		float jaccard = count * 1.0f/srcArticles.size();
		return jaccard;
	}
	
	
	public Set<Integer> getNeighbors(int id) throws SQLException{
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(databaseAccess.getInteractorA(id, beforeYear));
		set.addAll(databaseAccess.getInteractorB(id, beforeYear));
		return set;
	}
	
	public Set<Integer> getIntersection(Set<Integer> src, Set<Integer> dest){
		Set<Integer> set = new HashSet<Integer>();
		for(int s: src){
			if(dest.contains(s))
				set.add(s);
		}
		return set;
	}
	
	public float getSimText(int src, int dest) throws SQLException, NumberFormatException, ParseException, IOException{
		Set<String> srcPMID = databaseAccess.getPMIDs(src, beforeYear);
		Set<String> destPMID = databaseAccess.getPMIDs(dest, beforeYear);
		Map<String, Float> srcMap = textSimilarity.getTFIDF(srcPMID);
		Map<String, Float> destMap = textSimilarity.getTFIDF(destPMID);
		float cosine = 0;
		if(srcMap != null && destMap !=null)
			cosine = (float)InterDisipline.getCosine(srcMap, destMap);
		return cosine;
	}
	
	
	public float getSimMesh(int src, int dest) throws SQLException, NumberFormatException, ParseException, IOException{
		Set<String> srcPMID = databaseAccess.getPMIDs(src, beforeYear);
		Set<String> destPMID = databaseAccess.getPMIDs(dest, beforeYear);
		return getSimMesh(srcPMID, destPMID);
	}
	
	public float getSimMesh(Set<String> srcPMID, Set<String> destPMID) throws NumberFormatException, ParseException, IOException{
		Set<String> srcMeshs = textSimilarity.getMeshSet(srcPMID);
		Set<String> destMeshs = textSimilarity.getMeshSet(destPMID);
		int count = 0;
		for(String s: srcMeshs){
			if(destMeshs.contains(s))
				count++;
		}
		srcMeshs.addAll(destMeshs);
		if(srcMeshs.size() ==0)
			return 0f;
		float sim = count*1.0f/srcMeshs.size(); // Jaccard coef.
		return sim;
	}
	
	public float getClusteringCoef(int id) throws SQLException{
		Set<Integer> neighbors = getNeighbors(id);
		Set<Integer> secondNeighbors = null;
		int count = 0;
		for(int nei: neighbors){
			secondNeighbors = getNeighbors(nei);
			for(int coco: secondNeighbors){
				if(neighbors.contains(coco))
					count++;
			}
		}
		
		float coef = 0; //  no neighbor
		if(neighbors.size() >1)
			coef = count *1.0f*2/(neighbors.size()*(neighbors.size()- 1));
		
		return coef;
	}
	
	public Set<Integer> getUnion(Set<Integer> src, Set<Integer> dest){
		Set<Integer> set = new HashSet<Integer>();
		set.addAll(src);
		set.addAll(dest);
		return set;
	}
	
	public void processPairs(String path, String cls) throws IOException, NumberFormatException, SQLException{
		FileInputStream fstream = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int count = 0;
		FileWriter writer = new FileWriter(path+".feature");
		  while ((strLine = br.readLine()) != null){
			  String[] fields = strLine.split("\\s+");
			  int src =0;
			  int dest =0;
			  int year =beforeYear;
			  if(cls.equalsIgnoreCase("pos")){
				  if(fields.length >=4 && fields[0].length() >0 && fields[1].length() >0 && fields[3].length() >0){
					  src =Integer.parseInt(fields[0].trim());
					  dest = Integer.parseInt(fields[1].trim());
					  year = Integer.parseInt(fields[3].trim());
				  }
			  }else if(cls.equalsIgnoreCase("neg")){
				  if(fields.length >=2 && fields[0].length() >0 && fields[1].length() >0){
					  src =Integer.parseInt(fields[0].trim());
					  dest = Integer.parseInt(fields[1].trim());
				  }
			  }else
				  continue;
			  
			 Feature feature;
			try {
				feature = getFeatureForPair(src, dest, year);
				if(feature != null){
					writer.append(String.valueOf(src)).append(",").append(String.valueOf(dest)).append(",").append(feature.toString()).append(",").append(cls).append("\n");
					count ++;
					if(count %10 ==0)
						logger.info(count+" processed.");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		  }
		  writer.close();
		  logger.info("Finished processing pairs.");
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NumberFormatException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws NumberFormatException, ClassNotFoundException, SQLException, IOException, ParseException {
		if(args.length <3){
			System.out.println("--db-table --path --cls --before-year");	// before-year is the default year cut off. it is particularly used for negatives
			return;
		}
		long start = System.currentTimeMillis();
		DatabaseAccess.INTERACTION = args[0];
		MedlineSearcherSimple.MESH_FIELD = OrganismTool.getMeSH(args[0]);
		FeatureExtractor extractor = new FeatureExtractor(Integer.parseInt(args[3]));
		extractor.processPairs(args[1], args[2]);
		long time = (System.currentTimeMillis() - start)/1000/60;
		System.out.println("Task done. Total time used :"+time+" min");
	}

}
