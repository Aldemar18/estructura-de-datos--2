import java.util.*;

public class Ejercicio1MST {
    private static final int V = 10;
    private static final int MIN_ARISTAS = 15;
    private static int[][] matriz = new int[V][V];

    public static void main(String[] args) {
        generarMatrizAdyacencia();
        imprimirMatriz();

        int costoPrim = primMST();
        int costoKruskal = kruskalMST();

        System.out.println("\nCosto MST (Prim): " + costoPrim);
        System.out.println("Costo MST (Kruskal): " + costoKruskal);

        if (costoPrim == costoKruskal) {
            System.out.println("✅ Los costos coinciden.");
        } else {
            System.out.println("❌ Los costos NO coinciden.");
        }
    }

    private static void generarMatrizAdyacencia() {
        Random rand = new Random();
        int aristas = 0;
        while (aristas < MIN_ARISTAS) {
            int i = rand.nextInt(V);
            int j = rand.nextInt(V);
            if (i != j && matriz[i][j] == 0) {
                int peso = rand.nextInt(20) + 1;
                matriz[i][j] = peso;
                matriz[j][i] = peso;
                aristas++;
            }
        }
    }

    private static void imprimirMatriz() {
        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.printf("%3d ", matriz[i][j]);
            }
            System.out.println();
        }
    }

    private static int primMST() {
        boolean[] visitado = new boolean[V];
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;
        int costo = 0;

        for (int i = 0; i < V; i++) {
            int u = -1;
            for (int v = 0; v < V; v++) {
                if (!visitado[v] && (u == -1 || dist[v] < dist[u])) {
                    u = v;
                }
            }

            visitado[u] = true;
            costo += (dist[u] == Integer.MAX_VALUE ? 0 : dist[u]);

            for (int v = 0; v < V; v++) {
                if (matriz[u][v] != 0 && !visitado[v] && matriz[u][v] < dist[v]) {
                    dist[v] = matriz[u][v];
                }
            }
        }
        return costo;
    }

    private static int kruskalMST() {
        List<Edge> aristas = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (matriz[i][j] != 0) {
                    aristas.add(new Edge(i, j, matriz[i][j]));
                }
            }
        }

        Collections.sort(aristas);
        UnionFind uf = new UnionFind(V);
        int costo = 0;
        int edgesUsed = 0;
        for (Edge e : aristas) {
            if (uf.union(e.u, e.v)) {
                costo += e.w;
                edgesUsed++;
                if (edgesUsed == V-1) break;
            }
        }
        return costo;
    }

    static class Edge implements Comparable<Edge> {
        int u, v, w;
        Edge(int u, int v, int w) { this.u = u; this.v = v; this.w = w; }
        public int compareTo(Edge other) { return this.w - other.w; }
    }

    static class UnionFind {
        int[] parent, rank;
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb]) parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else { parent[rb] = ra; rank[ra]++; }
            return true;
        }
    }
}
