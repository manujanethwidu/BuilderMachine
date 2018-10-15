package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import application.BuilderController;
import beans.SpecDetailsBean;
import beans.TireDetailsBean;
import db.CreateConn;

public class SpecTbl {

	/*
	 * This is called when user clicks insert button. get relevent spec ID of
	 * tirecode;
	 */

	public int GetTireSpecID(Connection conn, int intTireCode, int intTireTypeIDAdjusted) throws SQLException {

		int specid = 0;
		String sql = "SELECT specid " + "FROM " + "srtspec.spec " + "WHERE " + "vid = (SELECT " + "vid " + " FROM "
				+ "srtspec.volume " + "WHERE " + "moldid = (SELECT " + "moldid " + "FROM " + "srtspec.tirecode "
				+ "WHERE " + "tirecode = " + intTireCode + ") " + "AND rimsizeid = (SELECT " + "rimsizeid " + "FROM "
				+ "srtspec.pid " + "WHERE " + "pid = (SELECT " + "pid " + "FROM " + "srtspec.tirecode " + "WHERE "
				+ "tirecode = " + intTireCode + "))) " + "AND tiretypeid = '" + intTireTypeIDAdjusted + "'; ";
		ResultSet rs = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			rs = stmt.executeQuery();
			if (rs.next()) {
				specid = (rs.getInt("specid"));
			} else {
				JOptionPane.showMessageDialog(null, "Spec is not Available for the given TireCode--SpecTbl-E03");
			}

		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "SpecTbl-E01-table.SpecTbl.GetTireSpecID method :-" + e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return specid;

	}

	/*
	 * This is called when user clicks insert button. get relevent spec details of
	 * specID;
	 */
	public SpecDetailsBean GetTireSpecData(Connection conn, int tireCode, int tireTypeID) throws SQLException {
		SpecDetailsBean beanspec = new SpecDetailsBean();
		int specID = 0;
		specID = GetTireSpecID(conn, tireCode, tireTypeID);
		String sql = " select bvol,cvol,trvol,bcode,ccode,trcode,bonwgt,specversion,bcomp,ccomp,trcomp,rndaproval,edc1sttire from srtspec.spec sp"
				+ " join srtspec.bcomp bc on bc.compid= sp.bcode " + " join srtspec.ccomp cc on cc.compid= sp.ccode "
				+ " join srtspec.trcomp tr on tr.compid= sp.trcode" + " where specid = ?";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, specID);
			rs = stmt.executeQuery();
			if (rs.next()) {

				beanspec.setbVol(rs.getFloat("bvol"));
				beanspec.setcVol(rs.getFloat("cvol"));
				beanspec.setTrVol(rs.getFloat("trvol"));
				beanspec.setbCode(rs.getInt("bcode"));
				beanspec.setcCode(rs.getInt("ccode"));
				beanspec.setTrCode(rs.getInt("trcode"));
				beanspec.setBonWgt(rs.getFloat("bonwgt"));
				beanspec.setSpecVersion(rs.getInt("specversion"));
				beanspec.setSpecID(specID);
				beanspec.setbComp(rs.getString("bcomp"));
				beanspec.setcComp(rs.getString("ccomp"));
				beanspec.settRComp(rs.getString("trcomp"));
				beanspec.setRnDApproval(rs.getInt("rndaproval"));
				beanspec.seteDC1stTire(rs.getInt("edc1sttire"));
				return beanspec;
			} else {
				JOptionPane.showMessageDialog(null, "No SpecID :-" + specID);
				return null;
			}
		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "SpecTbl-E02-table.SpecTbl.GetTireSpecData method :-" + e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	// update the builder table
		// when supervisor click the insert button
		public static boolean UpdateSpecTbl(Connection conn, SpecDetailsBean beanSpec) throws Exception {

			// update data to mfg.mfgcorrection table.
			// return bool to veryfy updateion success

			String sql = "UPDATE srtspec.spec SET rndaproval = 1 , edc1sttire = 1 where specid = " + beanSpec.getSpecID()
					+ ";";
			ResultSet keys = null;
			try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

				int affected = stmt.executeUpdate();

				if (affected == 1) {
					return true;
				} else {
					System.err.println("Now rows affected");
					JOptionPane.showMessageDialog(null,
							"UpdateSpecTbl-E01-table.UpdateSpecTbl.UpdateSpecTbl method :-Not Updated");
					return false;
				}
			} catch (SQLException e) {
				System.err.println(e);
				JOptionPane.showMessageDialog(null, "UpdateSpecTbl-E02-table.UpdateSpecTbl.UpdateSpecTbl method :-" + e);

				return false;
			} finally {
				if (keys != null)
					keys.close();
			}

		}

}
