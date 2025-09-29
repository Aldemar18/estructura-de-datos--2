import java.util.*;

public class Ejercicio7Warshall {
    private static final int V = 6;
    private static int[][] matriz = new int[V][V];
    private static int[][] alcanzabilidad = new int[V][V];

    public static void main(String[] args) {
        generarGrafoAleatorio();
        imprimirMatriz();

        warshall();
        imprimirAlcanzabilidad();
        componentesFuertementeConexos();
    }

    private static void generarGrafoAleatorio() {
        Random rand = new Random();
        int aristas = V + 5;

        for (int i = 0; i < V; i++) for (int j = 0; j < V; j++) matriz[i][j] = 0;

        while (aristas > 0) {
            int i = rand.nextInt(V);
            int j = rand.nextInt(V);
            if (i != j && matriz[i][j] == 0) {
                matriz[i][j] = 1;
                aristas--;
            }
        }
    }

    private static void imprimirMatriz() {
        System.out.println("Matriz de Adyacencia:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void warshall() {
        for (int i = 0; i < V; i++) for (int j = 0; j < V; j++) alcanzabilidad[i][j] = matriz[i][j];

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    alcanzabilidad[i][j] = (alcanzabilidad[i][j] == 1) ||
                                           (alcanzabilidad[i][k] == 1 && alcanzabilidad[k][j] == 1) ? 1 : 0;
                }
            }
        }
    }

    private static void imprimirAlcanzabilidad() {
        System.out.println("\nMatriz de Alcanzabilidad:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(alcanzabilidad[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void componentesFuertementeConexos() {
        boolean[] visitado = new boolean[V];
        System.out.println("\nComponentes Fuertemente Conexos:");
        for (int i = 0; i < V; i++) {
            if (!visitado[i]) {
                List<Integer> componente = new ArrayList<>();
                for (int j = 0; j < V; j++) {
                    if (alcanzabilidad[i][j] == 1 && alcanzabilidad[j][i] == 1) {
                        componente.add(j);
                        visitado[j] = true;
                    }
                }
                if (!componente.isEmpty()) System.out.println(componente);
            }
        }
    }
}
