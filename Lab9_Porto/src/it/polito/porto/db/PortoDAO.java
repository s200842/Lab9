package it.polito.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.porto.model.Article;
import it.polito.porto.model.Authorship;
import it.polito.porto.model.Creator;

public class PortoDAO {
	
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
	
	public List<Creator> getAllCreators(){
		List<Creator> creators = new ArrayList<Creator>();
		String sql = "SELECT * FROM creator ORDER BY family_name";
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
	
	public List<Authorship> getAllAutorships(){
		List<Authorship> auths = new ArrayList<Authorship>();
		String sql = "SELECT * FROM authorship";
		Connection c = DBConnect.getConnection();
		try {
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()){
				int id = res.getInt("id_authorship");
				int idarticolo = res.getInt("eprintid");
				int idautore = res.getInt("id_creator");
				Authorship atemp = new Authorship(id, idarticolo, idautore);
				auths.add(atemp);
			}
			res.close();
			c.close();
			return auths;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
