package importExcel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import db.CreateConn;
import tables.InsertDataPIDCCheckTbl;
import tables.OrderSummeryTbl;

public class ImportExceldata {

	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		readXLSXFile();

	}*/

	public static void readXLSXFile() throws IOException

	{

		InputStream ExcelFileToRead = new FileInputStream("D:/aa.xlsx");

		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook test = new XSSFWorkbook();
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();
		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			int pid = 0,nos =0;
			for (int i = 1; i <= 2; i++) {
				cell = (XSSFCell) cells.next();
				if(i==1) {
					pid=(int) cell.getNumericCellValue();
				}
				if(i==2) {
					nos=(int) cell.getNumericCellValue();
				}
			}
			CreateConn createConn = new CreateConn();
			try (Connection conn = createConn.GetConn();) {
				boolean insert = false;
				 insert = InsertDataPIDCCheckTbl.InsertPIDCCrossTbl(conn, pid, nos);
				 if (insert) {
					System.out.println("Inserted");
				}else {
					System.out.println("Not Inserted");
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println(pid+" "+nos);
			
		}
	}
}