package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintAlignment {
	private static String strbandWgt01 = "";

	public int getZPLPrintAlignment() {
		int lineNo;
		int align = 0;

		try {
			FileReader fr = new FileReader("C:\\SLTL_DataBase\\ZPLalignment.txt");
			// FileReader fr = new FileReader("/home/fg-admin/DBUtil/DBUtil.txt");

			BufferedReader br = new BufferedReader(fr);
			for (lineNo = 1; lineNo < 2; lineNo++) {
				if (lineNo == 1) {
					strbandWgt01 = br.readLine();
					align = Integer.parseInt(strbandWgt01);
				} else
					br.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(align);
		return align;

	}
}
