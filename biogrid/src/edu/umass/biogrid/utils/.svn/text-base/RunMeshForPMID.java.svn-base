package edu.umass.biogrid.utils;

import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;

public class RunMeshForPMID {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		if(args.length != 1){
			System.out.println("--path");
			return;
		}
		
		MeSHCounter counter = new MeSHCounter();
		counter.getMeshOfForAllPMIDs(args[0]);

	}

}
