import org.apache.poi.ss.usermodel.Row;

public class CellAccessor {
	ColumnInfo columninfo;
	
	
	public CellAccessor(ColumnInfo info) {
		columninfo = info;
	}
	
	public int getKovetel(Row row) {
		if(columninfo.getKovetelcolumn()!= null)
			return (int)row.getCell(columninfo.getKovetelcolumn()).getNumericCellValue();
		else {
			if(row.getCell(columninfo.getTkcolumn()).getStringCellValue().equals("K")) {
				return (int) row.getCell(columninfo.getSumcolumn()).getNumericCellValue();
			}else {
				return 0;
			}
		}
	}
	public int getTartozik(Row row) {
		if(columninfo.getKovetelcolumn()!= null)
			return (int)row.getCell(columninfo.getTartozikcolumn()).getNumericCellValue();
		else {
			if(row.getCell(columninfo.getTkcolumn()).getStringCellValue().equals("T")) {
				return (int) row.getCell(columninfo.getSumcolumn()).getNumericCellValue();
			}else {
				return 0;
			}
		}
	}
	
	public int getDate(Row row) {
		if(columninfo.getKovetelcolumn()!= null)
			return (int)row.getCell(columninfo.getDatecolumn()).getDateCellValue().getTime()/86400000;
		else
			return 0;
	}
}
