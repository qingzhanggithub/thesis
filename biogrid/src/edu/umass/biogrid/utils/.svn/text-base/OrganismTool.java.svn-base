/**
 * 
 */
package edu.umass.biogrid.utils;

/**
 * @author qing
 *
 */
public class OrganismTool {
	
	public static String Homo_sapiens = "interaction_Homo_sapiens";
	public static String Saccharomyces_cerevisiae = "interaction_Saccharomyces_cerevisiae";
	public static String Schizosaccharomyces_pombe = "interaction_Schizosaccharomyces_pombe";
	
	public static String getMeSH(String tableName){
		String name =null;
		if(tableName.equals(Homo_sapiens)){
			name = "(human mice)";
		}else if(tableName.equals(Saccharomyces_cerevisiae)){
			name = "\"saccharomyces cerevisiae\"";
		}else if(tableName.equals(Schizosaccharomyces_pombe)){
			name = "\"schizosaccharomyces pombe\"";
		}
		return name;
	}

}
