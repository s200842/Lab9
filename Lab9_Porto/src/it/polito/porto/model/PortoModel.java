package it.polito.porto.model;

import java.util.List;

import it.polito.porto.db.ArticleDAO;
import it.polito.porto.db.CreatorDAO;

public class PortoModel {

	private List<Creator> authors;
	private List<Article> articles;
	private CreatorDAO cdao;
	private ArticleDAO adao;
	
	public PortoModel(){
		cdao = new CreatorDAO();
		adao = new ArticleDAO();
		authors = cdao.getAllCreators();
		articles = adao.getAllArticles();
	}
	
	public static void main(String args[]){
		PortoModel m = new PortoModel();
		System.out.println(m.authors);
		for(Article a : m.articles){
			System.out.println(a.getTitle());
		}
	}
	
}
