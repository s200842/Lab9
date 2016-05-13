package it.polito.porto.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;

import it.polito.porto.db.PortoDAO;

public class PortoModel {

	private List<Creator> authors;
	private List<Article> articles;
	private List<Authorship> authorships;
	private PortoDAO dao;
	private Multigraph<Creator, DefaultEdge> graph;
	
	
	public Multigraph<Creator, DefaultEdge> getGraph() {
		return graph;
	}
	
	public PortoModel(){
		//Inizializzo le liste di autori e articoli
		dao = new PortoDAO();
		authors = dao.getAllCreators();
		articles = dao.getAllArticles();
		authorships = dao.getAllAutorships();
		graph = new Multigraph<Creator, DefaultEdge>(DefaultEdge.class);
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
							//Creo un arco che collega i due autori
							if(!graph.containsEdge(c2, c1)){
								graph.addEdge(c1, c2);
							}
							
						}
					}
				}
			}
		}
		System.out.println(graph.edgeSet().size());
		
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
		Set<Article> articlesInCommon = new HashSet<Article>();
		//Controllo che non siano coautori
		if(findCoauthors(c1).contains(c2)){
			return null;
		}
		//Controllo che appartengano allo stesso cluster
		for(Set<Creator> sc : findCluster()){
			if(sc.contains(c1) && sc.contains(c2)){
				//Appartengono allo stesso cluster, per cui posso avviare la ricerca dei percorsi
				recursiveVisit(c1, c2, new ArrayList<Article>(), articlesInCommon, new ArrayList<Creator>());
				return articlesInCommon;
			}
		}
		return null;
	}
	
	public void recursiveVisit (Creator start, Creator target, List<Article> tempArtList, Set<Article> totalArticles, List<Creator> visited) {
		List<Creator> neighbors = Graphs.neighborListOf(graph, start);
		//Condizione di uscita: se il punto di partenza coincide con il punto di arrivo ho finito la ricerca
		if(start.equals(target)){
			//Aggiungi la lista di articoli a quella generale COPIANDO LA LISTA;
			totalArticles.addAll(new ArrayList<Article>(tempArtList));
			return;
		}
		for(Creator c : neighbors) {
			if(!visited.contains(c)){
				visited.add(c);
				for(Article aNeighbor : c.getArticles()){
					if(start.getArticles().contains(aNeighbor)){
						tempArtList.add(aNeighbor);
						recursiveVisit(c, target, tempArtList, totalArticles, visited);
					}
				}
			}
		}
	}

	public List<Set<Creator>> findCluster(){
		ConnectivityInspector<Creator, DefaultEdge> ci = new ConnectivityInspector<Creator, DefaultEdge>(graph);
		return ci.connectedSets();
	}

	
}
