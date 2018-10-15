package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class CompoudSGTbl {

	public float GetBaseCompoundSG(Connection conn, int Bcode) throws SQLException {

		float bsg = 0f;
		String sql = "  select  bsg from srtspec.bcomp where compid = " + Bcode + ";";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			rs = stmt.executeQuery();
			if (rs.next()) {
				bsg = rs.getFloat("bsg");
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "CompoudSGTbl-E01-table.CompoudSGTbl.GetBaseCompoundSG method :-" + e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return bsg;
	}

	public float GetCentreCompoundSG(Connection conn, int Ccode) throws SQLException {

		float csg = 0f;
		String sql = "  select  csg from srtspec.ccomp where compid = " + Ccode + ";";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			rs = stmt.executeQuery();

			if (rs.next()) {

				csg = rs.getFloat("csg");

			} else {
				return 0f;
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null,
					"CompoudSGTbl-E02-table.CompoudSGTbl.GetCentreCompoundSG method :-" + e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return csg;

	}

	public float GetTreadCompoundSG(Connection conn, int TRcode) throws SQLException {

		float trsg = 0f;
		String sql = "  select  trsg from srtspec.trcomp where compid = " + TRcode + ";";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			rs = stmt.executeQuery();

			if (rs.next()) {

				trsg = rs.getFloat("trsg");

			} else {
				return 0f;
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "CompoudSGTbl-E03-table.CompoudSGTbl.GetTreadCompoundSG method :-" + e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return trsg;

	}
}
