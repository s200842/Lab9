package it.polito.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.Multigraph;

import it.polito.porto.db.PortoDAO;

public class PortoModel {

	private List<Creator> authors;
	private List<Article> articles;
	private List<Authorship> authorships;
	private PortoDAO dao;
	private Multigraph<Creator, ArticleEdge> graph;
	
	public Multigraph<Creator, ArticleEdge> getGraph() {
		return graph;
	}
	
	public PortoModel(){
		//Inizializzo le liste di autori e articoli
		dao = new PortoDAO();
		authors = dao.getAllCreators();
		articles = dao.getAllArticles();
		authorships = dao.getAllAutorships();
		graph = new Multigraph<Creator, ArticleEdge>(ArticleEdge.class);
		//Per ogni autore aggiungo gli articoli scritti
		for(Creator c : authors){
			for(Authorship a : authorships){
				if(a.getId_creator() == c.getId_creator()){
					for(Article ar : articles){
						if(a.getEprintid() == ar.getEprintid()){
							c.getArticles().add(ar);
						}
					}
				}
			}
		}
		//Per ogni articolo salvo la lista di autori che hanno partecipato alla stesura
		for(Article ar : articles){
			for(Authorship a : authorships){
				if(a.getEprintid() == ar.getEprintid()){
					for(Creator c : authors){
						if(c.getId_creator() == a.getId_creator()){
							ar.getAuthors().add(c);
						}
					}
				}
					
			}
		}
	}
	
	public void generateGraph(){
		//Ogni autore rappresenta un veritce del grafo
		Graphs.addAllVertices(graph, authors);
		for(Creator c1 : graph.vertexSet()){
			for(Creator c2 : graph.vertexSet()){
				//Se i due autori sono diversi..
				if(!c1.equals(c2)){
					for(Article a : c1.getArticles()){
						//.. per ogni articolo che hanno in comune..
						if(c2.getArticles().contains(a)){
							//Creo un arco che collega i due autori contenente info sull'articolo scritto
							graph.addEdge(c1, c2);
							graph.getEdge(c1, c2).setArticle(a);
						}
					}
				}
			}
		}
		
	}

	public List<Creator> findCoauthors(Creator c){
		List<Creator> list = Graphs.neighborListOf(graph, c);
		List<Creator> singleCoauthors = new ArrayList<Creator>();
		for(Creator c1 : list){
			if(!singleCoauthors.contains(c1)){
				singleCoauthors.add(c1);
			}
		}
		return singleCoauthors;
	}
	
	
	
	/*public static void main(String args[]){
		PortoModel m = new PortoModel();
		//System.out.println(m.authors);
		/*for(Article a : m.articles){
			System.out.print(a.getTitle()+", ");
		}
		System.out.println(m.authors.get(0));
		System.out.println(m.authors.get(0).getArticles());
		System.out.println(m.articles.get(0));
		System.out.println(m.articles.get(0).getAuthors());
		
	}*/
	
}
