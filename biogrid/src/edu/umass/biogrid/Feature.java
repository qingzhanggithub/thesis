/**
 * 
 */
package edu.umass.biogrid;

/**
 * @author qing
 *
 */
public class Feature {
	
	protected int numCommonFriend;
	protected int sumNeighbor;
	protected float sumClusteringCoef;
	protected float simText;
	protected float simMesh;
	protected float jaccard;
	protected float adamic;
	protected int sumPub;
	protected float jaccardArticleCoOccur;
	public int getNumCommonFriend() {
		return numCommonFriend;
	}
	public void setNumCommonFriend(int numCommonFriend) {
		this.numCommonFriend = numCommonFriend;
	}
	public int getSumNeighbor() {
		return sumNeighbor;
	}
	public void setSumNeighbor(int sumNeighbor) {
		this.sumNeighbor = sumNeighbor;
	}
	public float getSumClusteringCoef() {
		return sumClusteringCoef;
	}
	public void setSumClusteringCoef(float sumClusteringCoef) {
		this.sumClusteringCoef = sumClusteringCoef;
	}
	public float getSimText() {
		return simText;
	}
	public void setSimText(float simText) {
		this.simText = simText;
	}
	public float getJaccard() {
		return jaccard;
	}
	public void setJaccard(float jaccard) {
		this.jaccard = jaccard;
	}
	public float getAdamic() {
		return adamic;
	}
	public void setAdamic(float adamic) {
		this.adamic = adamic;
	}
	public float getSimMesh() {
		return simMesh;
	}
	public void setSimMesh(float simMesh) {
		this.simMesh = simMesh;
	}
	
	
	public int getSumPub() {
		return sumPub;
	}
	public void setSumPub(int sumPub) {
		this.sumPub = sumPub;
	}
	
	public float getJaccardArticleCoOccur() {
		return jaccardArticleCoOccur;
	}
	public void setJaccardArticleCoOccur(float jaccardArticleCoOccur) {
		this.jaccardArticleCoOccur = jaccardArticleCoOccur;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		String sep = ",";
		sb.append(simText).append(sep);
		sb.append(simMesh).append(sep);
		sb.append(numCommonFriend).append(sep);
		sb.append(sumNeighbor).append(sep);
		sb.append(jaccard).append(sep);
		sb.append(adamic).append(sep);
		sb.append(sumPub).append(sep);
		sb.append(jaccardArticleCoOccur).append(sep);
		sb.append(sumClusteringCoef);
		return sb.toString();
	}
	
	public String toFriendlyString(){
		StringBuffer sb = new StringBuffer();
		String sep = "\n";
		sb.append("simText:").append(simText).append(sep);
		sb.append("simMesh:").append(simMesh).append(sep);
		sb.append("numCommonFriend:").append(numCommonFriend).append(sep);
		sb.append("sumNeighbor:").append(sumNeighbor).append(sep);
		sb.append("Jaccard:").append(jaccard).append(sep);
		sb.append("Adamic:").append(adamic).append(sep);
		sb.append("sumPub:").append(sumPub).append(sep);
		sb.append("jaccardArticleCoOccur:").append(jaccardArticleCoOccur).append(sep);
		sb.append("ClusteringCoef:").append(sumClusteringCoef);
		return sb.toString();
	}
	
	
}
