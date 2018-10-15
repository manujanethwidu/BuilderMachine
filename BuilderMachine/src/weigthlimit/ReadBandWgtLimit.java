package weigthlimit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import beans.FinalWgtTolBean;

public class ReadBandWgtLimit {

	private static String strbandWgt13 = "";
	private static String strbandWgt14 = "";
	private static String strbandWgt15 = "";
	private static String strbandWgt16 = "";

	// max band weight precentage
	private float maxbandTolerancePercentage = 0f;
	// minimum band weight precentage
	private float minbandTolerancePercentage = 0f;
	List<Float> maxMinTotalList;

	public boolean veryfyBandWgt(float actBandWgt, float StandardBandWgt) throws ClassNotFoundException {
		FinalWgtTolBean bean = new FinalWgtTolBean();
		int lineNo;

		String xx = System.getProperty("os.name");
		try {
			FileReader fr = new FileReader("C:\\SLTL_DataBase\\weightrangesBand.txt");
			// FileReader fr = new FileReader("/home/fg-admin/DBUtil/DBUtil.txt");

			BufferedReader br = new BufferedReader(fr);
			for (lineNo = 19; lineNo < 25; lineNo++) {
				if (lineNo == 20) {
					strbandWgt13 = br.readLine();

				} else if (lineNo == 21) {
					strbandWgt14 = br.readLine();

				} else if (lineNo == 23) {
					strbandWgt15 = br.readLine();

				} else if (lineNo == 24) {
					strbandWgt16 = br.readLine();

				} else
					br.readLine();

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (actBandWgt > 10) {
				// calculate max band weight tolerace precentage

				maxbandTolerancePercentage = Float.parseFloat(strbandWgt13);
				minbandTolerancePercentage = Float.parseFloat(strbandWgt14);
			} else if (actBandWgt <= 10) {
				// calculate max band weight tolerace precentage
				maxbandTolerancePercentage = Float.parseFloat(strbandWgt15);
				minbandTolerancePercentage = Float.parseFloat(strbandWgt16);
			}

			// calculate maximum band weight
			float maxBandWgt = (maxbandTolerancePercentage * StandardBandWgt);

			// calculate minimum band weight
			float minBandWgt = (minbandTolerancePercentage * StandardBandWgt);
			if (actBandWgt <= maxBandWgt && actBandWgt >= minBandWgt) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"ReadBandWgtLimit-E01-wightlimit.ReadBandWgtLimit.getCorrectBandWgt method :- ");
		}
		Class.forName("org.postgresql.Driver");
		return false;

	}

}
