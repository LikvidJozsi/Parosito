import java.util.Vector;

public class AtvezetesTable {
	Vector <Integer> date;
	Vector <Integer> value;
	Vector <Integer> row;
	private static int maxinterval;
	private int index;
	private static boolean useintervallimit = false;
	public AtvezetesTable(int size){
		date = new Vector<>(size);
		value = new Vector<>(size);
		row = new Vector<>(size);
		index = 0;
	}
	public void selectNext(){
		index++;
	}
	public boolean Pairable(AtvezetesTable a){
		boolean pairable = true;
		if(a.value.get(a.index) != this.value.get(this.index))
			pairable = false;
		if(useintervallimit && Math.abs(a.date.get(a.index)-this.date.get(this.index)) > maxinterval)
			pairable = false;
		return pairable;
	}
	public void addRow(int date, int value, int row){
		this.date.add(date);
		this.value.add(value);
		this.row.add(row);
	}
	public int length(){
		return value.size();
	}
	public int getDate(int index){
		return date.get(index);
	}
	public int getRow(int index){
		return row.get(index);
	}
	public int getValue(int index){
		return value.get(index);
	}
}
