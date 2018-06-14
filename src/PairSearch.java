
public class PairSearch {
	boolean[]visited;
	AdjencyMtx mtx;
	private int[] pair;
	public PairSearch(AdjencyMtx mtx){
		this.mtx = mtx;
		int length = mtx.getTartozikLength()+mtx.getKovetelLength();
		visited = new boolean[length];
		pair = new int[length];
		for( int i = 0; i < length;i++){
			pair[i] = -1;
		}
	}	
	
	public void findPair(int index){
		for(int i = 0; i<visited.length;i++){
			visited[i] = false;
		}
		recSearch(index);
	}
	
	public boolean recSearch(int index){
		boolean [] adjvertices = mtx.getAdj(index);
		for(int i = 0; i< adjvertices.length;i++){
			if(adjvertices[i] && pair[i] == -1){
				pair[i] = index;
				pair[index] = i;
				return true;
			}
		}
		for(int i = 0; i< adjvertices.length;i++){
			if(adjvertices[i] && !visited[i]){
				visited[i] = true;
				if(recSearch(pair[i])){
					pair[i] = index;
					pair[index] = i;
					return true;
				}else{
					visited[i] = false;
				}
			}
		}
		return false;
	}
	
	public boolean isPaired(int index){
		return pair[index] != -1;
	}
}
