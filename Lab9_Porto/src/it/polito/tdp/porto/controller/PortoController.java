package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    		//TODO cluster
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
    		List<Article> articles = model.findArticles(boxAuthor1.getValue(), boxAuthor2.getValue());
    		if(articles.isEmpty()){
    			txtResult.setText("Non esiste alcun articolo che colleghi i due autori selezionati");
    		}
    		else{
    			txtResult.setText(String.format("Articoli in comune tra %s e %s:\n\n", boxAuthor1.getValue(), boxAuthor2.getValue()));
    			for(Article a : articles){
    				txtResult.appendText(a.toString()+"\n");
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
    	if(boxAuthor1.getValue() != null){
    		result = model.findCoauthors(boxAuthor1.getValue());
    	}
    	else{
    		result = model.findCoauthors(boxAuthor2.getValue());
    	}
    	//Controllo che la lista non sia vuota
    	if(result.isEmpty()){
    		txtResult.setText("Nessun coautore trovato.");
    		return;
    	}
    	//Stampo lista coautori
    	txtResult.setText(String.format("Coautori di %s:\n\n", boxAuthor1.getValue()));
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
