package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import beans.OrderDetailsBean;
import beans.TCValidateBean;

public class OrderSummeryTbl {
	public OrderDetailsBean CheckDemandAvlforPID(Connection conn, int PID) throws SQLException {
		boolean blnPIDAvl = false;
		OrderDetailsBean beanPIDdetails = new OrderDetailsBean();
		String sql = "SELECT pid,nos FROM mfg.ordersummery where pid = " + PID + "";
		ResultSet rs = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			rs = stmt.executeQuery();
			if (rs.next()) {
				beanPIDdetails.setpID(rs.getInt("pid"));
				beanPIDdetails.setDemand(rs.getInt("nos"));
				blnPIDAvl = true;
				beanPIDdetails.setBlnPIDAvailability(blnPIDAvl);
				return beanPIDdetails;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "PIDCheckingTbl-E03-table.OrderSummery.CheckDemandAvlforPID method :-" + e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	

}
