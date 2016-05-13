package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.porto.model.Article;
import it.polito.porto.model.Creator;
import it.polito.porto.model.PortoModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Creator> boxAuthor1;

    @FXML
    private ComboBox<Creator> boxAuthor2;

    @FXML
    private Button btnCoauthor;

    @FXML
    private Button btnCluster;

    @FXML
    private Button btnArticles;
    
    @FXML
    private Button btnReset;

    @FXML
    private TextArea txtResult;
    
    private PortoModel model;
    
    public void setModel(PortoModel model){
    	this.model = model;
    	model.generateGraph();
    	System.out.println("Grafo caricato con successo!");
    	boxAuthor1.getItems().addAll(model.getGraph().vertexSet());
    	boxAuthor2.getItems().addAll(model.getGraph().vertexSet());
    }
    
    @FXML
    void doReset(ActionEvent event) {
    	boxAuthor1.setValue(null);
    	boxAuthor2.setValue(null);
    	txtResult.clear();
    }

    @FXML
    void doCluster(ActionEvent event) {
    	txtResult.clear();
    	//Prima di tutto controllo che non sia selezionato alcun autore dai menu a tendina
    	if(boxAuthor1.getValue() == null && boxAuthor2.getValue() == null){
    		List<Set<Creator>> res = model.findCluster();
    		int i = 1;
    		for(Set<Creator> set : res){
    			txtResult.appendText("---Cluster "+i+" ---\n");
    			for(Creator c : set){
    				txtResult.appendText(c.toString()+"\n");
    				
    			}
    			i ++;
    			txtResult.appendText("\n");
    		}
    	}
    	else{
    		txtResult.setText("Si prega di non selezionare alcun autore dai menu a tendina per ricercare i cluster.");
    		return;
    	}
    }

    @FXML
    void doFindArticles(ActionEvent event) {
    	txtResult.clear();
    	//Prima di tutto controllo che siano selezionati entrambi gli autori dai menu a tendina
    	if(boxAuthor1.getValue() != null && boxAuthor2.getValue() != null){
    		Set<Article> articles = model.findArticles(boxAuthor1.getValue(), boxAuthor2.getValue());
    		if(articles == null || articles.isEmpty()){
    			txtResult.setText("Non esiste alcun articolo che colleghi i due autori selezionati, oppure i due autori sono coautori di uno stesso articolo.");
    		}
    		else{
    			txtResult.setText(String.format("Articoli (archi) totali che collegano %s e %s:\n\nTrovati %d\n\n", boxAuthor1.getValue(), boxAuthor2.getValue(), articles.size()));
    			for(Article a : articles){
    				txtResult.appendText(a.toString()+"\n");
    				System.out.println(a.getEprintid());
    			}
    		}
    	}
    	else{
    		txtResult.setText("Si prega di selezionare entrambi gli autori dai menu a tendina per ricercare gli articoli.");
    		return;
    	}
    }

    @FXML
    void doFindCoauthors(ActionEvent event) {
    	txtResult.clear();
    	List<Creator> result = null;
    	//Prima di tutto controllo che sia selezionato un solo autore dai menu a tendina
    	if((boxAuthor1.getValue() == null && boxAuthor2.getValue() == null) || (boxAuthor1.getValue() != null && boxAuthor2.getValue() != null)){
    		txtResult.setText("Si prega di selezionare un solo autore dai menu a tendina per ricercare i coautori.");
    		return;
    	}
    	//Controllo da che box prendere l'autore
    	ComboBox<Creator> box = null;
    	if(boxAuthor1.getValue() != null){
    		box = boxAuthor1;
    		result = model.findCoauthors(box.getValue());
    	}
    	else{
    		box = boxAuthor2;
    		result = model.findCoauthors(box.getValue());
    	}
    	//Controllo che la lista non sia vuota
    	if(result.isEmpty()){
    		txtResult.setText("Nessun coautore trovato.");
    		return;
    	}
    	//Stampo lista coautori
    	txtResult.setText(String.format("Coautori di %s:\n\n", box.getValue()));
    	for(Creator c : result){
    		txtResult.appendText(c.toString()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxAuthor1 != null : "fx:id=\"boxAuthor1\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxAuthor2 != null : "fx:id=\"boxAuthor2\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCoauthor != null : "fx:id=\"btnCoauthor\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnCluster != null : "fx:id=\"btnCluster\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnArticles != null : "fx:id=\"btnArticles\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Porto.fxml'.";
    }
}
