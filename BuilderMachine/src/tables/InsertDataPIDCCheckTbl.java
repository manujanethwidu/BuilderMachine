package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class InsertDataPIDCCheckTbl {

	public static boolean InsertPIDCCrossTbl(Connection conn, int PID, int NOS) throws Exception {
		 
		// update data to mfg.pidcrosscheck table.
		// return bool to veryfy updateion success

		String sql = "INSERT INTO mfg.pidcorsscheck (pid,nos) values(?,?)";
		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			// inser into data mfg.pidcrosscheck table this method is called by maincontroller
			// class
			stmt.setInt(1, PID);
			stmt.setInt(2, NOS);
			
			
			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				System.err.println("Now rows affected");
				JOptionPane.showMessageDialog(null,
						"E01-table.InsertDataPIDCCheckTbl.InsertPIDCCrossTbl method :-not Inserted SN  :"
								+ PID);
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"mfgcorrectionTbl-E02-table.InsertDataPIDCCheckTbl.InsertPIDCCrossTbl error :-" + e);
			return false;
		} finally {
			if (keys != null)
				keys.close();
		}
	}
}
