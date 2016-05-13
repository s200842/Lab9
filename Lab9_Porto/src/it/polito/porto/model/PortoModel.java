package it.polito.porto.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
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
							//Creo un arco che collega i due autori contenente info sull'articolo scritto se non esiste già
							if(!graph.containsEdge(c2, c1)){
								graph.addEdge(c1, c2);
								graph.getEdge(c1, c2).setArticle(a);
							}
							
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

	public Set<Article> findArticles(Creator c1, Creator c2){
		//Devo trovare TUTTI gli archi che collegano i due autori MA NON quelli diretti -> Visita in ampiezza
		Multigraph<Creator, ArticleEdge> graph2 = graph;
		//Rimuovo gli archi tra i due autori in modo da non avere coautori
		graph2.removeAllEdges(c1, c2);
		return bfv(c1, c2, graph2);
	}

	public List<Set<Creator>> findCluster(){
		ConnectivityInspector<Creator, ArticleEdge> ci = new ConnectivityInspector<Creator, ArticleEdge>(graph);
		return ci.connectedSets();
	}

	public Set<Article> bfv(Creator start, Creator end, Multigraph<Creator, ArticleEdge> g){
		Set<Creator> visited = new HashSet<Creator>();
		Set<Article> art = new HashSet<Article>();
		//Creo una coda contenente i vertici su cui devo andare
		Queue<Creator> q = new LinkedList<Creator>();
		q.add(start);
		while(!q.isEmpty()){
			Creator c = q.remove();
			//Se non ho ancora visitato quel nodo, lo aggiungo alla coda per non uscire dal while
			if(!visited.contains(c)){
				visited.add(c);
				for(Creator c2 : Graphs.neighborListOf(g, c)){
					if(g.degreeOf(c2)>1){
						q.add(c2);
						for(ArticleEdge ae : g.getAllEdges(c, c2)){
							art.add(ae.getArticle());
						}
					}
					
				}
			}
		}
		if(visited.contains(end)){
			return art;
		}
		else return null;
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
