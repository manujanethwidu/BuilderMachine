package tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
 

public class BPTbl {

	public boolean updatebpTbl(Connection conn,String pressNo )
			throws Exception {
	 
		// Update stk table
		String sql = "update stock.stk set  pressnox=" + pressNo + "  where id=1; ";
	 
		ResultSet keys = null;
		try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			 
			int affected = stmt.executeUpdate();
			if (affected == 1) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Not updated bp.updatebpTbl table-:"  );
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Not updated bp.updatebpTbl table-:" + e);
			return false;
		} finally {
			if (keys != null)
				keys.close();
		}
	}
	
	
	
}
