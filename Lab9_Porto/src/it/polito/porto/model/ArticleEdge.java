package it.polito.porto.model;

import org.jgrapht.graph.DefaultEdge;

@SuppressWarnings("serial")
public class ArticleEdge extends DefaultEdge {

	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
	

}
