import java.util.*;

public class Ejercicio5Dijkstra {
    private static final int V = 8;
    private static int[][] matriz = new int[V][V];

    public static void main(String[] args) {
        generarGrafoAleatorio();
        imprimirMatriz();

        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese el vértice de origen (0-" + (V-1) + "): ");
        int origen = sc.nextInt();

        dijkstra(origen);
    }

    private static void generarGrafoAleatorio() {
        Random rand = new Random();
        int aristas = V + 5;
        while (aristas > 0) {
            int i = rand.nextInt(V);
            int j = rand.nextInt(V);
            if (i != j && matriz[i][j] == 0) {
                int peso = rand.nextInt(20) + 1;
                matriz[i][j] = peso;
                matriz[j][i] = peso;
                aristas--;
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

    private static void dijkstra(int origen) {
        int[] dist = new int[V];
        int[] parent = new int[V];
        boolean[] visitado = new boolean[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[origen] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minDist(dist, visitado);
            if (u == -1) break;
            visitado[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visitado[v] && matriz[u][v] != 0 &&
                    dist[u] != Integer.MAX_VALUE &&
                    dist[u] + matriz[u][v] < dist[v]) {
                    dist[v] = dist[u] + matriz[u][v];
                    parent[v] = u;
                }
            }
        }

        System.out.println("\nResultados de Dijkstra desde el vértice " + origen + ":");
        for (int v = 0; v < V; v++) {
            if (dist[v] == Integer.MAX_VALUE) {
                System.out.println("No hay ruta hacia el vértice " + v);
            } else {
                System.out.print("Camino a " + v + ": ");
                imprimirCamino(parent, v);
                System.out.println(" (costo " + dist[v] + ")");
            }
        }
    }

    private static int minDist(int[] dist, boolean[] visitado) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int v = 0; v < V; v++) {
            if (!visitado[v] && dist[v] < min) {
                min = dist[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private static void imprimirCamino(int[] parent, int j) {
        if (j == -1) return;
        imprimirCamino(parent, parent[j]);
        System.out.print(j + " ");
    }
}
