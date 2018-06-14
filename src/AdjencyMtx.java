
public class AdjencyMtx {
	private Boolean[][] mtx;
	public AdjencyMtx(AtvezetesTable tartozik, AtvezetesTable kovetel,int maxdiff){
		mtx = new Boolean[tartozik.length()][kovetel.length()];
		for(int i =0;i<tartozik.length();i++)
			for(int j = 0; j < kovetel.length();j++)
				mtx[i][j] = Math.abs(tartozik.getDate(i)-kovetel.getDate(j))<= maxdiff && 
				tartozik.getValue(i)==kovetel.getValue(j);
	}
	public boolean[] getAdj(int index){
		if(index < mtx.length)
			return getTartozikAdj(index);
		else
			return getKovetelAdj(index-mtx.length);
	}
	public boolean[] getTartozikAdj(int index){
		int combinedlength = mtx[0].length + mtx.length;
		boolean [] row = new boolean[combinedlength];
		for(int i = 0; i<mtx.length;i++)
			row[i] = false;
		for(int i = mtx.length; i< combinedlength;i++)
			row[i] = mtx[index][i-mtx.length];
		return row;
	}
	public boolean[] getKovetelAdj(int index){
		int combinedlength = mtx[0].length + mtx.length;
		boolean [] row = new boolean[combinedlength];
		for(int i = 0; i<mtx.length;i++)
			row[i] = mtx[i][index];
		for(int i = mtx.length;i < combinedlength; i++){
			row[i] = false;
			
		}
		return row;
	}
	public int getTartozikLength(){
		return mtx.length;
	}
	public int getKovetelLength(){
		return mtx[0].length;
	}
}
