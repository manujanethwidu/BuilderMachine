package tables;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

import beans.CompoundSGBean;
import beans.SpecDetailsBean;
import beans.TireDetailsBean;

public class MfgBuilderTbl {

	public static boolean InsertMfgBuilderTbl(Connection conn, TireDetailsBean beanTire, SpecDetailsBean beanspec,CompoundSGBean beanComp) throws Exception {
 
		// update data to mfg.mfgcorrection table.
		// return bool to veryfy updateion success

		String sql = "INSERT into mfg.builder (sn,tirecode,sver,bvol,cvol,trvol,bonwgt,bsg,csg,trsg,actwgt,bcode,ccode,trcode,specid,stdbandwgt,actbandwgt)  VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			// inser into data mfgcorrectiontable this method is called by maincontroller
			// class
			stmt.setInt(1, beanTire.getsN());
			stmt.setInt(2, beanTire.getTirecode());
			stmt.setInt(3, beanspec.getSpecVersion());
			stmt.setDouble(4, beanspec.getbVol());
			stmt.setDouble(5, beanspec.getcVol());
			stmt.setDouble(6, beanspec.getTrVol());
			stmt.setDouble(7, beanspec.getBonWgt());
			stmt.setDouble(8, beanComp.getBsg());
			stmt.setDouble(9, beanComp.getCsg());
			stmt.setDouble(10, beanComp.getTrsg());
			stmt.setDouble(11, beanspec.getActTtlTireWeight());
			stmt.setInt(12, beanspec.getbCode());
			stmt.setInt(13, beanspec.getcCode());
			stmt.setInt(14, beanspec.getTrCode());
			stmt.setInt(15, beanspec.getSpecID());
			stmt.setDouble(16, beanTire.getStdBandWGT());
			stmt.setDouble(17, beanTire.getActBandWGT());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				System.err.println("Now rows affected");
				JOptionPane.showMessageDialog(null,
						"mfgcorrectionTbl-E01-table.MfgBuilderTbl.InsertMfgBuilderTbl method :-not Inserted SN  :"
								+ beanTire.getsN());
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"mfgcorrectionTbl-E02-table.MfgBuilderTbl.InsertMfgBuilderTbl error :-" + e);
			return false;
		} finally {
			if (keys != null)
				keys.close();
		}
	}
}
