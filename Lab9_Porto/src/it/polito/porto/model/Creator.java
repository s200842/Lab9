package it.polito.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Creator {
	
	private int id_creator;
	private String family_name;
	private String given_name;
	private List<Article> articles;
	
	public Creator(int id_creator, String family_name, String given_name) {
		this.id_creator = id_creator;
		this.family_name = family_name;
		this.given_name = given_name;
		articles = new ArrayList<Article>();
	}

	public List<Article> getArticles() {
		return articles;
	}

	public int getId_creator() {
		return id_creator;
	}

	public void setId_creator(int id_creator) {
		this.id_creator = id_creator;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_creator;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creator other = (Creator) obj;
		if (id_creator != other.id_creator)
			return false;
		return true;
	}
	
	public String toString(){
		return family_name+" "+given_name;
	}
}
