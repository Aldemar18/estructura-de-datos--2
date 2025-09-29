import java.util.*;

public class Ejercicio6Floyd {
    private static final int V = 6;
    private static final int INF = 99999;
    private static int[][] dist = new int[V][V];
    private static int[][] next = new int[V][V];

    public static void main(String[] args) {
        generarGrafoAleatorio();
        imprimirMatriz();

        floydWarshall();
        imprimirResultados();
    }

    private static void generarGrafoAleatorio() {
        Random rand = new Random();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) dist[i][j] = 0;
                else dist[i][j] = INF;
                next[i][j] = -1;
            }
        }

        int aristas = V + 5;
        while (aristas > 0) {
            int i = rand.nextInt(V);
            int j = rand.nextInt(V);
            if (i != j && dist[i][j] == INF) {
                int peso = rand.nextInt(15) + 1;
                dist[i][j] = peso;
                dist[j][i] = peso;
                next[i][j] = j;
                next[j][i] = i;
                aristas--;
            }
        }
    }

    private static void imprimirMatriz() {
        System.out.println("Matriz de Adyacencia (pesos, INF=99999):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) System.out.printf("%5s ", "INF");
                else System.out.printf("%5d ", dist[i][j]);
            }
            System.out.println();
        }
    }

    private static void floydWarshall() {
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    private static void imprimirResultados() {
        System.out.println("\nResultados Floyd-Warshall (distancias y caminos):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i != j) {
                    if (dist[i][j] == INF) System.out.println("No hay ruta de " + i + " a " + j);
                    else {
                        System.out.print("Distancia mínima " + i + " → " + j + " = " + dist[i][j]);
                        System.out.print(", Camino: ");
                        imprimirCamino(i, j);
                        System.out.println();
                    }
                }
            }
        }
    }

    private static void imprimirCamino(int u, int v) {
        if (next[u][v] == -1) {
            System.out.print("No existe");
            return;
        }
        System.out.print(u);
        while (u != v) {
            u = next[u][v];
            System.out.print(" → " + u);
        }
    }
}
