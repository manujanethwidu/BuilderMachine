package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JOptionPane;

import application.BuilderController;
import beans.MoldDetailBean;
import beans.TireDetailsBean;

public class TireCodeTbl {
	public TireDetailsBean GetTireDataFromTC(Connection conn, int tireCode) throws SQLException {
		TireDetailsBean beanTire = new TireDetailsBean();
		MoldDetailBean beanMoldDeatail = new MoldDetailBean();

		String sql = "select tiresizebasic,lugtypecap,config,tiretypecap,tt.tiretypeid ttypeid,rimsize,brand,swmsg,prid.pid pidcode,sb.tcat tcatcode,moldno,b.bandwgt stdbandwgt,band,qcapp,b.bandid bndid from srtspec.tirecode tc"
				+ "	join srtspec.pid prid on prid.pid = tc.pid   " + "	join srtspec.size s on s.sizeid = prid.sizeid  "
				+ "	join srtspec.sizebasic sb on sb.sizebasicid= s.sizebasicid "
				+ " join srtspec.lugtype lt on lt.lugtypeid = s.lugtypeid "
				+ " join srtspec.config con on con.configid = s.configid "
				+ " join srtspec.tiretype tt on tt.tiretypeid = prid.tiretypeid "
				+ " join srtspec.swmsg sw on sw.swmsgid = prid.swmsgid "
				+ " join srtspec.brand br on br.brandid = prid.brandid "
				+ "  join srtspec.mold ml on ml.moldid = tc.moldid " + " join srtspec.band b on b.bandid = sb.bandid "
				+ " join srtspec.rimsize rs on rs.rimsizeid = prid.rimsizeid where tc.tirecode = " + tireCode + ";";
		ResultSet rs = null;
		beanTire.setTirecode(tireCode);
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			rs = stmt.executeQuery();
			if (rs.next()) {
				beanTire.setTireSize(rs.getString("tiresizebasic"));
				beanTire.setLugType(rs.getString("lugtypecap"));
				beanTire.setConfig(rs.getString("config"));
				beanTire.setTireType(rs.getString("tiretypecap"));
				beanTire.setRimSize(rs.getString("rimsize"));
				beanTire.setBrandName(rs.getString("brand"));
				beanTire.setSwMsg(rs.getString("swmsg"));
				beanTire.setPid(rs.getInt("pidcode"));
				beanTire.settCat(rs.getInt("tcatcode"));
				beanTire.setMoldNO(rs.getString("moldno"));
				beanTire.setStdBandWGT(rs.getFloat("stdbandwgt"));
				beanTire.setBandName(rs.getString("band"));
				int tireTypeID = (rs.getInt("ttypeid"));
				beanTire.setBandId(rs.getInt("bndid"));

				if (tireTypeID == 13) {
					tireTypeID = 7;
				}
				if (tireTypeID == 14) {
					tireTypeID = 8;
				}
				beanTire.setTireTypeId(tireTypeID);
				return beanTire;
			} else {
				JOptionPane.showMessageDialog(null,
						"BuilderTbl-E01-table.BuilderTbl.GetTireDataFromTC method :-Please CrossCheck the TireCode entered!"
								+ tireCode);
				return null;
			}

		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "BuilderTbl-E02-table.BuilderTbl.GetTireDataFromTC method :-" + e);

			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	public static int GetLastSN(Connection conn) throws SQLException {

		int SN = 0;
		int specid = 0;

		String sql = "select max(sn) maxsn from mfg.builder";
		ResultSet rs = null;

		try (PreparedStatement stmt = conn.prepareStatement(sql);) {

			rs = stmt.executeQuery();

			if (rs.next()) {
				SN = (rs.getInt("maxsn"));

			} else {
				JOptionPane.showMessageDialog(null, "Pls check the SN Availability");
			}

		} catch (Exception e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(null, "BuilderTbl-E02-table.BuilderTbl.GetLastSN method :-" + e);
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return SN;

	}

}
