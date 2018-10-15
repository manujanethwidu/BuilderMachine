package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class ComPortReader {
	private static String comPort = "";


	public static String getComPort() throws ClassNotFoundException {
		int lineNo;
		try {
			FileReader fr = new FileReader("C:\\SLTL_DataBase\\COMPORT.txt");
			// FileReader fr = new FileReader("/home/fg-admin/DBUtil/DBUtil.txt");
			BufferedReader br = new BufferedReader(fr);
			for (lineNo = 1; lineNo < 10; lineNo++) {
				if (lineNo == 1) {
					comPort = br.readLine();
					System.out.println(comPort);
				} else
					br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comPort;
	}

}