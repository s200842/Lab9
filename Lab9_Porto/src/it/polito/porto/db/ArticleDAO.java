package it.polito.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.porto.model.Article;

public class ArticleDAO {

	public List<Article> getAllArticles(){
		List<Article> articles = new ArrayList<Article>();
		String sql = "SELECT * FROM article";
		Connection c = DBConnect.getConnection();
		try {
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()){
				int id = res.getInt("eprintid");
				int year = res.getInt("year");
				String title = res.getString("title");
				Article atemp = new Article(id, year, title);
				articles.add(atemp);
			}
			res.close();
			c.close();
			return articles;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
