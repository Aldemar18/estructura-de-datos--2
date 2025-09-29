import java.util.*;

public class Ejercicio2Prim {
    private static final int V = 8;
    private static int[][] matriz = new int[V][V];

    public static void main(String[] args) {
        generarGrafoNoCompleto();
        imprimirMatriz();

        if (esConexo()) {
            int costo = primMST();
            System.out.println("\nCosto del Árbol de Expansión Mínima: " + costo);
        } else {
            System.out.println("\nEl grafo no tiene árbol de expansión mínima");
        }
    }

    private static void generarGrafoNoCompleto() {
        Random rand = new Random();
        int aristas = V + 2;
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

    private static boolean esConexo() {
        boolean[] visitado = new boolean[V];
        dfs(0, visitado);
        for (boolean v : visitado) if (!v) return false;
        return true;
    }

    private static void dfs(int nodo, boolean[] visitado) {
        visitado[nodo] = true;
        for (int i = 0; i < V; i++) {
            if (matriz[nodo][i] != 0 && !visitado[i]) dfs(i, visitado);
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
                if (!visitado[v] && (u == -1 || dist[v] < dist[u])) u = v;
            }
            visitado[u] = true;
            costo += (dist[u] == Integer.MAX_VALUE ? 0 : dist[u]);
            for (int v = 0; v < V; v++) {
                if (matriz[u][v] != 0 && !visitado[v] && matriz[u][v] < dist[v]) dist[v] = matriz[u][v];
            }
        }
        return costo;
    }
}
