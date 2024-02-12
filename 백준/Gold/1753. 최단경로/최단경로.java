import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());

        Graph graph = new Graph(vertices);

        int start = Integer.parseInt(br.readLine());

        for (int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph.addEdge(from, to, weight);
        }

        int[] distances = graph.shortestPath(start);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < distances.length; i++) {
            sb.append(distances[i] == Integer.MAX_VALUE ? "INF" : distances[i]).append("\n");
        }

        System.out.println(sb.toString());
    }

    static class Graph {
        private int vertices;
        private List<List<Vertex>> adj;
        private int[] dist;

        public Graph(int vertices) {
            this.vertices = vertices;
            adj = new ArrayList<>(vertices + 1);
            for (int i = 0; i <= vertices; i++) {
                adj.add(new ArrayList<>());
            }
            dist = new int[vertices + 1];
            Arrays.fill(dist, Integer.MAX_VALUE);
        }

        public void addEdge(int v, int w, int weight) {
            adj.get(v).add(new Vertex(w, weight));
        }

        public int[] shortestPath(int start) {
            PriorityQueue<Vertex> pq = new PriorityQueue<>(Comparator.comparingInt(Vertex::getDist));
            pq.add(new Vertex(start, 0));
            dist[start] = 0;

            while (!pq.isEmpty()) {
                Vertex current = pq.poll();
                int vertexNum = current.getNum();

                for (Vertex neighbor : adj.get(vertexNum)) {
                    int newDist = dist[vertexNum] + neighbor.getDist();
                    if (newDist < dist[neighbor.getNum()]) {
                        dist[neighbor.getNum()] = newDist;
                        pq.add(new Vertex(neighbor.getNum(), dist[neighbor.getNum()]));
                    }
                }
            }

            return dist;
        }

        static class Vertex {
            private int vertexNum;
            private int weight;

            public Vertex(int vertexNum, int weight) {
                this.vertexNum = vertexNum;
                this.weight = weight;
            }

            public int getDist() {
                return weight;
            }

            public int getNum() {
                return vertexNum;
            }
        }
    }
}
