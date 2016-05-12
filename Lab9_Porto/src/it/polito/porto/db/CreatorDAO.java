package it.polito.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.porto.model.Creator;

public class CreatorDAO {
	
	public List<Creator> getAllCreators(){
		List<Creator> creators = new ArrayList<Creator>();
		String sql = "SELECT * FROM creator";
		Connection c = DBConnect.getConnection();
		try {
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()){
				int id = res.getInt("id_creator");
				String family = res.getString("family_name");
				String given = res.getString("given_name");
				Creator ctemp = new Creator(id, family, given);
				creators.add(ctemp);
			}
			res.close();
			c.close();
			return creators;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
