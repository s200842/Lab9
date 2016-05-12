package it.polito.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Article {

	private int eprintid;
	private int year;
	private String title;
	private List<Creator> authors;
	
	public Article(int eprintid, int year, String title) {
		this.eprintid = eprintid;
		this.year = year;
		this.title = title;
		authors = new ArrayList<Creator>();
	}
	
	public List<Creator> getAuthors() {
		return authors;
	}

	public int getEprintid() {
		return eprintid;
	}

	public void setEprintid(int eprintid) {
		this.eprintid = eprintid;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + eprintid;
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
		Article other = (Article) obj;
		if (eprintid != other.eprintid)
			return false;
		return true;
	}
}
