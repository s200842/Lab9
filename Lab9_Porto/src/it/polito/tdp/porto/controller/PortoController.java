package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.ResourceBundle;

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
    private ComboBox<?> boxAuthor1;

    @FXML
    private ComboBox<?> boxAuthor2;

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
    }

    @FXML
    void doCluster(ActionEvent event) {

    }

    @FXML
    void doFindArticles(ActionEvent event) {

    }

    @FXML
    void doFindCoauthors(ActionEvent event) {

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
