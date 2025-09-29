import java.util.*;

public class Ejercicio8Router {
    private static final int V = 6;
    private static int[][] anchoBanda = new int[V][V];

    public static void main(String[] args) {
        generarRedAleatoria();
        imprimirMatriz();

        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese nodo origen (0-" + (V-1) + "): ");
        int origen = sc.nextInt();
        System.out.print("Ingrese nodo destino (0-" + (V-1) + "): ");
        int destino = sc.nextInt();

        rutaMasRapida(origen, destino);
    }

    private static void generarRedAleatoria() {
        Random rand = new Random();
        int conexiones = V + 5;
        while (conexiones > 0) {
            int i = rand.nextInt(V);
            int j = rand.nextInt(V);
            if (i != j && anchoBanda[i][j] == 0) {
                int bw = rand.nextInt(90) + 10;
                anchoBanda[i][j] = bw;
                anchoBanda[j][i] = bw;
                conexiones--;
            }
        }
    }

    private static void imprimirMatriz() {
        System.out.println("Matriz de Ancho de Banda (Mbps):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.printf("%4d ", anchoBanda[i][j]);
            }
            System.out.println();
        }
    }

    private static void rutaMasRapida(int origen, int destino) {
        int[] maxBW = new int[V];
        int[] parent = new int[V];
        boolean[] visitado = new boolean[V];

        Arrays.fill(maxBW, -1);
        Arrays.fill(parent, -1);
        maxBW[origen] = Integer.MAX_VALUE;

        for (int i = 0; i < V - 1; i++) {
            int u = maximoAncho(maxBW, visitado);
            if (u == -1) break;
            visitado[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visitado[v] && anchoBanda[u][v] > 0) {
                    int posible = Math.min(maxBW[u], anchoBanda[u][v]);
                    if (posible > maxBW[v]) {
                        maxBW[v] = posible;
                        parent[v] = u;
                    }
                }
            }
        }

        if (maxBW[destino] == -1) {
            System.out.println("\nNo hay ruta posible entre los nodos");
        } else {
            System.out.print("\nRuta más rápida: ");
            imprimirCamino(parent, destino);
            System.out.println(" (ancho de banda: " + maxBW[destino] + " Mbps)");
        }
    }

    private static int maximoAncho(int[] maxBW, boolean[] visitado) {
        int max = -1, idx = -1;
        for (int v = 0; v < V; v++) {
            if (!visitado[v] && maxBW[v] > max) {
                max = maxBW[v];
                idx = v;
            }
        }
        return idx;
    }

    private static void imprimirCamino(int[] parent, int j) {
        if (j == -1) return;
        imprimirCamino(parent, parent[j]);
        System.out.print(j + " ");
    }
}
