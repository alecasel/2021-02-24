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
		verticesIdMap = new HashMap<Integer, Player>();
		matches = dao.listAllMatches();
	}

	public List<Match> getMatchList() {
		return matches;
	}
	
	public String createGraph(Match match) {
		
		graph = new SimpleDirectedWeightedGraph<Player, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertices = dao.getAllVertices(match);
		
		for (Player player : vertices) {
			verticesIdMap.put(player.getPlayerID(), player);
		}
		
		Graphs.addAllVertices(graph, vertices);
		
		List<Arco> archi = dao.getArchi(match, verticesIdMap);
		for (Arco arco : archi) {
			if (arco.getWeight() >= 0) { //orientato da 1 a 2
				Graphs.addEdge(graph, arco.getVertex1(), arco.getVertex2(), arco.getWeight());
			} else { //orientato da 2 a 1
				Graphs.addEdge(graph, arco.getVertex2(), arco.getVertex1(), (-1)*arco.getWeight());
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi\n", vertices.size(), graph.edgeSet().size());
	}
	
	public Player getBestPlayer() {  // Devo restituire anche maxDelta, non solo Player
		// Posso o modificare la classe Player, quindi andare ad aggiungere un attributo in più
		// oppure creare una nuova classe BestPlayer
		
		Player best = null;
		Double maxDelta = (double) Integer.MIN_VALUE; //non mettere 0
		
		//somma pesi archi uscenti --e-- somma pesi archi entranti
		for (Player p : graph.vertexSet()) {
			
			double weightOut = 0.0;
			for (DefaultWeightedEdge dwe : graph.outgoingEdgesOf(p)) {
				weightOut += graph.getEdgeWeight(dwe);
			}
			
			double weightIn = 0.0;
			for (DefaultWeightedEdge dwe : graph.incomingEdgesOf(p)) {
				weightIn += graph.getEdgeWeight(dwe);
			}
			
			double delta = weightOut - weightIn; 
			if (delta > maxDelta) {
				p.setDelta(maxDelta);
				best = p;
				maxDelta = delta;
			}
		}
		
		return best;
	}
	
}
