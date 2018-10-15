package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import beans.TireDetailsBean;

public class StockStkTbl {
	public static boolean InsertStockstk(Connection conn, TireDetailsBean beanTire) throws Exception {

		// update data to mfg.mfgcorrection table.
		// return bool to veryfy updateion success

		String sql = "INSERT into stock.stk (sn,pid,qg,tc)  VALUES (?,?,'A',?);";

		// insert into stock.stk (sn,pid,qg) values (711032866,113485,'A')

		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

			// inser into data mfgcorrectiontable this method is called by maincontroller
			// class
			stmt.setInt(1, beanTire.getsN());
			stmt.setInt(2, beanTire.getPid());
			stmt.setInt(3, beanTire.getTirecode());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				System.err.println("Now rows affected");
				JOptionPane.showMessageDialog(null,
						"StockStkTbl-E01-table.StockStkTbl.InsertStockstk method :-not Inserted SN  :"
								+ beanTire.getsN());
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"StockStkTbl-E02-table.StockStkTbl.InsertStockstk error :-" + beanTire.getsN() + e);
			return false;
		} finally {
			if (keys != null)
				keys.close();
		}
	}

	public int GetTiresinHand(Connection conn, int PID) throws SQLException {

		int TiresinHand = 0;

		String sql = "select count(sn) countsn from stock.stk ss  " + "where (avl = 1 or avl = 3 )and ss.pid = ?  "
				+ "and (qg= 'A'or qg = 'a' or qg isnull)";
		ResultSet rs = null;
		System.out.println(sql);
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			
stmt.setInt(1, PID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				TiresinHand = (rs.getInt("countsn"));
			} else {
				TiresinHand = 0;
			}

		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "StockStkTbl-E03-table.SpecTbl.GetTireSpecID method :-" + e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return TiresinHand;

	}
}
