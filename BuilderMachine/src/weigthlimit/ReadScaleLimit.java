package weigthlimit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.swing.JOptionPane;

import beans.FinalWgtTolBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReadScaleLimit {
	private static String str1 = "";
	private static String str2 = "";
	private static String str3 = "";
	private static String str4 = "";
	private static String str5 = "";
	private static String str6 = "";
	private static String str7 = "";
	private static String str8 = "";
	private static String str9 = "";
	private static String str10 = "";
	private static String str11 = "";
	private static String str12 = "";
	private static String str13 = "";
	private static String str14 = "";
	private static String str15 = "";
	private static String str16 = "";
	private static String str17 = "";
	
	float maxTolerance = 0f;
	float minTolerance = 0f;

	List<Float> maxMinTotalList;

	public FinalWgtTolBean getCorrectScale(float totalWeight) throws ClassNotFoundException {
		FinalWgtTolBean bean = new FinalWgtTolBean();
		int lineNo;
		boolean blnWgtRangesSet = false;

		String xx = System.getProperty("os.name");
		try {
			FileReader fr = new FileReader("C:\\SLTL_DataBase\\weightrangesTires.txt");
			// FileReader fr = new FileReader("/home/fg-admin/DBUtil/DBUtil.txt");

			BufferedReader br = new BufferedReader(fr);
			for (lineNo = 1; lineNo < 25; lineNo++) {
				if (lineNo == 2) {
					str1 = br.readLine();

				} else if (lineNo == 3) {
					str2 = br.readLine();

				} else if (lineNo == 5) {
					str3 = br.readLine();

				} else if (lineNo == 6) {
					str4 = br.readLine();

				} else if (lineNo == 8) {
					str5 = br.readLine();

				} else if (lineNo == 9) {
					str6 = br.readLine();

				} else if (lineNo == 11) {
					str7 = br.readLine();

				} else if (lineNo == 12) {
					str8 = br.readLine();

				} else if (lineNo == 14) {
					str9 = br.readLine();

				} else if (lineNo == 15) {
					str10 = br.readLine();

				} else if (lineNo == 17) {
					str11 = br.readLine();

				} else if (lineNo == 18) {
					str12 = br.readLine();

				} else if (lineNo == 20) {
					str13 = br.readLine();

				} else if (lineNo == 21) {
					str14 = br.readLine();

				} else if (lineNo == 23) {
					str15 = br.readLine();

				} else if (lineNo == 24) {
					str16 = br.readLine();
				} else
					br.readLine();
			}
			blnWgtRangesSet = true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ReadScaleLimit-E02-Weight Limit file not available");
		}
		// this if is to veryfy that file is found and all str fields are set
		if (blnWgtRangesSet) {
			try {
				if (totalWeight <= 10) {
					// set values to the variable
					System.out.println("Below 10kg");
					maxTolerance = Float.parseFloat(str1);
					minTolerance = Float.parseFloat(str2);
				} else if (totalWeight <= 30) {
					System.out.println("10kg to 30kg");
					maxTolerance = Float.parseFloat(str3);
					minTolerance = Float.parseFloat(str4);
				} else if (totalWeight <= 50) {
					System.out.println("30kg to 50kg");
					maxTolerance = Float.parseFloat(str5);
					minTolerance = Float.parseFloat(str6);
				} else if (totalWeight <= 70) {
					System.out.println("50kg to 70kg");
					maxTolerance = Float.parseFloat(str7);
					minTolerance = Float.parseFloat(str8);
				} else if (totalWeight <= 100) {
					System.out.println("70kg to 100kg");
					maxTolerance = Float.parseFloat(str9);
					minTolerance = Float.parseFloat(str10);
				} else {
					System.out.println("Above 100kg");
					maxTolerance = Float.parseFloat(str11);
					minTolerance = Float.parseFloat(str12);
				}
				bean.setMaxTol(totalWeight+maxTolerance);
				bean.setMinTol(totalWeight+minTolerance);

				return bean;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "ReadScaleLimit-E01-wightlimit.getCorrectScale method :- ");
				return null;
			}
		}else {//blnWgtRangesSet
			return null;
		}
		// Class.forName("org.postgresql.Driver");
	}

}
