/**
 * 
 */
package edu.umassmed.biohyp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qing
 *
 */
public class FileUtil {
	
	public static List<Integer> readInteractorList(String path) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		List<Integer> list = new ArrayList<Integer>();
		while ((line = br.readLine()) != null) {
			String[] fields = line.split(",");
			if(fields[0].trim().length()>0)
				list.add(Integer.parseInt(fields[0]));
		}
		br.close();
		return list;
	}

}
