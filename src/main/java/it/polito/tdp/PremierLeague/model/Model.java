package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private List<Match> matches;
	private SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> graph;
	private List<Player> vertices = new ArrayList<Player>();
	
	public Model() {
		dao = new PremierLeagueDAO();
		
		matches = dao.listAllMatches();
	}

	public List<Match> getMatchList() {
		return matches;
	}
	
	public String createGraph(Match match) {
		
		graph = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertices = dao.getAllVertices(match);
		
		Graphs.addAllVertices(graph, vertices);
		
		return String.format("Grafo creato con %d vertici e %d archi\n", vertices.size(), graph.edgeSet().size());
	}
	
	
}
