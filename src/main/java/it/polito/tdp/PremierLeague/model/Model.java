package it.polito.tdp.PremierLeague.model;

import java.util.List;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private List<Match> matches;
	
	public Model() {
		dao = new PremierLeagueDAO();
		
		matches = dao.listAllMatches();
	}

	public List<Match> getMatchList() {
		return matches;
	}
	
	
}
