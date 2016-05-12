package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
    void doCluster(ActionEvent event) {

    }

    @FXML
    void doFindArticles(ActionEvent event) {

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

    }
}
