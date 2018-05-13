
/**leetcode 787, medium, airbnb.
 * There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.
   Now given all the cities and fights, together with starting city src and the destination dst,
   your task is to find the cheapest price from src to dst with up to k stops.
   If there is no such route, output -1.
   
   The number of nodes n will be in range [1, 100], with nodes labeled from 0 to n - 1.
   The size of flights will be in range [0, n * (n - 1) / 2].
   The format of each flight will be (src, dst, price).
   The price of each flight will be in the range [1, 10000].
   k is in the range of [0, n - 1].
   There will not be any duplicated flights or self cycles.
   */
/**My BFS solution.*/
import java.util.*;
public class CheapestFlightsKStop {
	  private class Node {
	        int city, price;
	        public Node(int city, int price) {
	            this.city = city;
	            this.price = price;
	        }       
	    }
	    
	    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
	        Map<Integer, Set<Node>> graph = new HashMap<>();
	        buildGraph(flights, graph);
	        int[] prices = new int[n]; // build prices array for pruning.
	        Arrays.fill(prices, Integer.MAX_VALUE);
	        prices[src] = 0;
	        int ans = Integer.MAX_VALUE;
	        Queue<Node> queue = new LinkedList<>();
	        queue.add(new Node(src, 0));            
	        while (!queue.isEmpty() && K >= 0) {
	            int size = queue.size();
	            for (int i = 0; i < size; i++) {
	                Node curr = queue.remove();
	                Set<Node> neighbors = graph.get(curr.city);
	                if (neighbors == null) continue;
	                for (Node neigh : neighbors) {
	                    int nPrice = curr.price + neigh.price;
	                    if (neigh.city == dst) ans = Math.min(ans, nPrice);
	                    if (nPrice >= prices[neigh.city] || nPrice >= ans) continue; // pruning
	                    prices[neigh.city] = nPrice;
	                    queue.add(new Node(neigh.city, nPrice));
	                }                                               
	            }
	            K--;
	        }
	        return ans != Integer.MAX_VALUE ? ans : -1;
	    }
	    
	    private void buildGraph(int[][] flights, Map<Integer, Set<Node>> graph) {
	        for (int i = 0; i < flights.length; i++) {
	            int key = flights[i][0];
	            Set<Node> neigh = graph.get(key);
	            if (neigh == null) {
	                neigh = new HashSet<>();
	                graph.put(key, neigh);
	            }
	            neigh.add(new Node(flights[i][1], flights[i][2]));
	        }
	    }
}
