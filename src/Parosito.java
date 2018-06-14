import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Parosito {
	XSSFWorkbook inputbook;
	XSSFSheet inputsheet;

	AdjencyMtx mtx;
	AtvezetesTable tartozik, kovetel;
	ColumnInfo columninfo = new ColumnInfo();
	ALFrame window;

	public Parosito(String inputfilename, ALFrame window) {
		this.window = window;
		try {
			inputbook = new XSSFWorkbook(inputfilename);
			inputsheet = inputbook.getSheetAt(0);

			tartozik = new AtvezetesTable(inputsheet.getPhysicalNumberOfRows() / 2);
			kovetel = new AtvezetesTable(inputsheet.getPhysicalNumberOfRows() / 2);
			Iterator<Row> sorit = inputsheet.iterator();
			int sorid = -1;
			while (sorit.hasNext()) {
				Row sor = sorit.next();
				sorid++;
				Iterator<Cell> cellait = sor.iterator();
				int oszlopid = -1;
				while (cellait.hasNext()) {
					Cell cella = cellait.next();
					oszlopid++;
					if (cella.getCellType() == Cell.CELL_TYPE_STRING) {
						String seged = cella.getStringCellValue();
						if (seged.equals("Tartozik")) {
							columninfo.setFirstrownum(sorid + 1);
							columninfo.setTartozikcolumn(oszlopid);
						}
						if (seged.equals("Követel")) {
							columninfo.setKovetelcolumn(oszlopid);
						}
						if (seged.equals("Dátum")) {
							columninfo.setFirstrownum(sorid + 1);
							columninfo.setDatecolumn(oszlopid);
						}
						if (seged.equals("T/K")) {
							columninfo.setFirstrownum(sorid + 1);
							columninfo.setTkcolumn(oszlopid);
						}
						if (seged.equals("Összeg")) {
							columninfo.setSumcolumn(oszlopid);;
						}
					}
				}
				if (columninfo.getFirstrownum() != null)
					break;
			}
			CellAccessor accessor = new CellAccessor(columninfo);
			for (int i = columninfo.getFirstrownum(); i < inputsheet.getPhysicalNumberOfRows() - 1; i++) {
				Row row = inputsheet.getRow(i);
				if (accessor.getKovetel(row) != 0) {
					kovetel.addRow(accessor.getDate(row), accessor.getKovetel(row), i);
				} else {
					tartozik.addRow(accessor.getDate(row), accessor.getTartozik(row), i);
				}
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(window,
					"A bemeneti file nem nyitható meg, lehet egy másik programban van megnyitva");
		}
	}

	public void parositas(String outputfilename, int maxdatediff) {
		AdjencyMtx matrix = new AdjencyMtx(tartozik, kovetel, maxdatediff);
		PairSearch pairsearch = new PairSearch(matrix);
		for (int i = 0; i < tartozik.length(); i++)
			pairsearch.findPair(i);
		XSSFWorkbook outputbook = new XSSFWorkbook();
		XSSFSheet outputsheet = outputbook.createSheet();

		CopyRow(outputsheet, 0, inputsheet.getRow(columninfo.getFirstrownum() - 1), outputbook);
		int outputrownum = 1;
		for (int i = 0; i < tartozik.length(); i++) {
			if (!pairsearch.isPaired(i)) {
				CopyRow(outputsheet, outputrownum++, inputsheet.getRow(tartozik.getRow(i)), outputbook);
			}
		}
		for (int i = 0; i < kovetel.length(); i++) {
			if (!pairsearch.isPaired(i + tartozik.length())) {
				CopyRow(outputsheet, outputrownum++, inputsheet.getRow(kovetel.getRow(i)), outputbook);
			}
		}
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(outputfilename));
			outputbook.write(out);
			out.close();
			outputbook.close();
			inputbook.close();
			JOptionPane.showMessageDialog(window, "A kiemeneti fájl a mappában létre lett hozva!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(window,
					"A kiemeneti fájl nem nyitható meg, lehet egy másik programban van megnyitva");
		}
	}

	private void CopyRow(XSSFSheet dest, int destindex, XSSFRow source, XSSFWorkbook outputbook) {

		Row destsor = dest.createRow(destindex);
		int cellindex = 0;
		for (Cell sourcecell : source) {
			CopyCell(destsor, cellindex, sourcecell, outputbook);
			cellindex++;
		}
	}

	private void CopyCell(Row dest, int destindex, Cell source, XSSFWorkbook outputsheet) {
		Cell ujcella = dest.createCell(destindex);
		if (source.getCellType() != Cell.CELL_TYPE_BLANK) {
			CellStyle newStyle = outputsheet.createCellStyle();
			CellStyle origStyle = source.getCellStyle();
			newStyle.cloneStyleFrom(origStyle);
			ujcella.setCellStyle(newStyle);

			if (source.getCellType() == Cell.CELL_TYPE_BLANK) {
				ujcella.setCellType(Cell.CELL_TYPE_BLANK);
			}
			if (source.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				ujcella.setCellValue(source.getBooleanCellValue());
			}
			if (source.getCellType() == Cell.CELL_TYPE_ERROR) {
				ujcella.setCellValue(source.getErrorCellValue());
			}
			if (source.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				ujcella.setCellValue(source.getNumericCellValue());
			}
			if (source.getCellType() == Cell.CELL_TYPE_STRING) {
				ujcella.setCellValue(source.getStringCellValue());
			}
		}
	}
}
