package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import beans.MoldDetailBean;
import beans.TireDetailsBean;

public class MoldDetailsTbl {
		public MoldDetailBean GetMoldDetailsFromTC(Connection conn, int tireCode) throws SQLException {
			
			MoldDetailBean beanMoldDeatail= new MoldDetailBean();

			String sql = "select qcapp from srtspec.tirecode tc"
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

			try (PreparedStatement stmt = conn.prepareStatement(sql);) {

				rs = stmt.executeQuery();

				if (rs.next()) {
				beanMoldDeatail.setqCapp(rs.getInt("qcapp"));
				

					return beanMoldDeatail;
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
}
