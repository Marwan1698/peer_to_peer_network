import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	PreparedStatement stmt = null;
    static DBConnection db = new DBConnection();
    
	public void insert(String ip, String name, int portNum) {
		db.connection();
		String sql = "INSERT INTO peers (ip, username, portNum) VALUES (?, ?, ?)";
		try {
			stmt = db.conn.prepareStatement(sql);
			stmt.setString(1, ip);
			stmt.setString(2, name);
			stmt.setInt(3, portNum);
			stmt.executeUpdate();
			//System.out.println("SUCCESS!\n");
		} catch (SQLException e) {
		}
	}

	public void logout(String name) {
    	db.connection();
        try {
            String sql = "DELETE FROM peers WHERE username = ?";
            stmt = db.conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException ex) {}
    }
	
	public String selectIP (String name) {
        String IP = "";
        db.connection();
        String sql = "SELECT ip FROM peers WHERE username = ?";
        try {
                stmt = db.conn.prepareStatement(sql);
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                	IP = rs.getString("ip");
            } catch (SQLException ex) {}
        return IP;
    }
	
	public int selectPort (String name) {
        int portNum = 0;
        db.connection();
        String sql = "SELECT portNum FROM peers WHERE username = ?";
        try {
                stmt = db.conn.prepareStatement(sql);
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                	portNum = rs.getInt("portNum");
            } catch (SQLException ex) {}
        return portNum;
    }
	
	public void selectAll (String name) {
		db.connection();
        String sql = "SELECT * FROM peers WHERE NOT (username = ?)";
        try
        {
            stmt = db.conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Online Now");
            while (rs.next()) {
            	System.out.println(">" + rs.getString("username"));
            }
        }
        catch (SQLException e) {}
    }
	
//	public ArrayList<String> reSelectAll (String name) {
//		ArrayList<String> names = new ArrayList<>();
//		db.connection();
//        String sql = "SELECT * FROM peers WHERE NOT (username = ?)";
//        try
//        {
//            stmt = db.conn.prepareStatement(sql);
//            stmt.setString(1, name);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//            	names.add(rs.getString("username"));
//            }
//        }
//        catch (SQLException e) {}
//        return names;
//    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
