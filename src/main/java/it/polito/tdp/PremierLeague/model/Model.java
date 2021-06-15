package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private List<Match> matches;
	private SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge> graph;
	private List<Player> vertices = new ArrayList<Player>();
	private Map<Integer, Player> verticesIdMap;
	
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
		verticesIdMap = new HashMap<Integer, Player>();
		for (Player player : vertices) {
			verticesIdMap.put(player.getPlayerID(), player);
		}
		
		Graphs.addAllVertices(graph, vertices);
		
		List<Arco> archi = dao.getArchi(match, verticesIdMap);
		for (Arco arco : archi) {
			if (arco.getWeight() >= 0) { //orientato da 1 a 2
				Graphs.addEdge(graph, arco.getVertex1(), arco.getVertex2(), arco.getWeight());
			} else { //orientato da 2 a 1
			Graphs.addEdge(graph, arco.getVertex2(), arco.getVertex1(), arco.getWeight());
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi\n", vertices.size(), graph.edgeSet().size());
	}
	
	
}
