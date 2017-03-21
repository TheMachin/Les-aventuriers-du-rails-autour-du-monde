package org.m1;

// Noms des binomes:
import java.text.DecimalFormat;

public class ApplicationTri {

    protected static boolean bugPresent = false;

    public static void triFusionNonRecursif(long[] a) {
        long[] tableauTravail = new long[a.length];
        for (int i = 1; i < a.length; i *= 2) {
            for (int j = 0; j < a.length; j += (i * 2)) {
                fusion(a, tableauTravail, j, j + i, j + i + i - 1);
                if(bugPresent){
                	swap(a, 0, a.length - 1);
                }else{
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    //-- Implantation du tri à bulle
    //--------------------------------------------------------------------------
    public static void triABulle(long[] a) {
        int out, in;
        for (out = a.length - 1; out > 0; out--) {
            for (in = 0; in < out; in++) {
                if (a[in] > a[in + 1]) {
                    swap(a, in, in + 1);
                }
            }
        }
    }

    //--------------------------------------------------------------------------
    //-- Implantation du tri fusion récursif
    //--------------------------------------------------------------------------
    public static void triFusionRecursif(long[] a) {
        triFusionRecursif(a, new long[a.length], 0, a.length - 1);
    }

    protected static void triFusionRecursif(long[] a, long[] tableauTravail, int borneInf, int borneMax) {
        if (borneInf == borneMax) {
        } else {
            // trouve le milieu
            int milieu = (borneInf + borneMax) / 2;
            // trie la moitié inf.
            triFusionRecursif(a, tableauTravail, borneInf, milieu);
            // trie la moitié sup.
            triFusionRecursif(a, tableauTravail, milieu + 1, borneMax);
            // fusionne
            fusion(a, tableauTravail, borneInf, milieu + 1, borneMax);
        }
    }

    protected static void fusion(long[] a, long[] tableauTravail, int pointeurInf, int pointeurMax, int borneMax) {
        int j = 0;                          // index
        int borneInf = pointeurInf;
        int milieu = pointeurMax - 1;
        int n = borneMax - borneInf + 1;    // nb d'éléments

        while (pointeurInf <= milieu && pointeurMax <= borneMax) {
            if (a[pointeurInf] < a[pointeurMax]) {
                tableauTravail[j++] = a[pointeurInf++];
            } else {
                tableauTravail[j++] = a[pointeurMax++];
            }
        }

        while (pointeurInf <= milieu) {
            tableauTravail[j++] = a[pointeurInf++];
        }

        while (pointeurMax <= borneMax) {
            tableauTravail[j++] = a[pointeurMax++];
        }

        for (j = 0; j < n; j++) {
            a[borneInf + j] = tableauTravail[j];
        }
    }

    //--------------------------------------------------------------------------
    //-- Implantation du tri quicksort
    //--------------------------------------------------------------------------
    public static void quicksort(long[] a) {
        quicksort(a, 0, a.length - 1);
    }

    protected static void quicksort(long[] a, int gauche, int droite) {
        if (droite - gauche <= 0) {
            return;
        }
        // on utilise le dernier élément à droite comme pivot
        int indexPivot = partition(a, gauche, droite, droite);
        quicksort(a, gauche, indexPivot - 1);
        quicksort(a, indexPivot + 1, droite);
    }

    protected static int partition(long[] a, int premier, int dernier, int indexPivot) {
        long pivot = a[indexPivot];
        swap(a, indexPivot, dernier);
        int index = premier;
        for (int i = premier; i < dernier; i++) {
            if (a[i] <= pivot) {
                swap(a, i, index);
                if(bugPresent){
                }else{
                	index++;
                }
            }
        }
        swap(a, dernier, index);
        return index;
    }

    //--------------------------------------------------------------------------
    //-- Méthodes utiles
    //--------------------------------------------------------------------------
    // Tableau rempli de valeurs aléatoires
    public static long[] tableauAleatoire(int n) {
        long[] rand = new long[n];
        for (int i = 0; i < n; i++) {
            rand[i] = (int) (Math.random() * n * 10);
        }
        return rand;
    }

    public static void startTimer() {
        timestamp = System.nanoTime();
    }

    public static double endTimer() {
        return (System.nanoTime() - timestamp) / 1000000.0;
    }

    public static void swap(long[] a, int i, int j) {
        long temp = a[i];
    	if (bugPresent){
            a[j] = a[i];
        }else{
            a[i] = a[j];
        }
    	a[j] = temp;
    }

    //--------------------------------------------------------------------------
    //-- Main
    //--------------------------------------------------------------------------
    private static long timestamp;

    public static void main(String[] args) {

        final int BULLE = 0, FUSION_REC = 1, FUSION_NONREC = 2, QUICK = 3;

        int max = 14, runs = 5;
        double[][] stats = new double[4][max];
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case BULLE:
                    System.out.print("Execution tri a bulle...");
                    break;
                case FUSION_REC:
                    System.out.print("Execution tri fusion recursif...");
                    break;
                case FUSION_NONREC:
                    System.out.print("Execution tri fusion non recursif...");
                    break;
                case QUICK:
                    System.out.print("Execution quicksort...");
                    break;
            }

            for (int j = 0; j < max; j++) {
                double avg = 0;
                for (int k = 0; k < runs; k++) {
                    long[] a = tableauAleatoire((int) Math.pow(2, j + 1));
                    startTimer();
                    switch (i) {
                        case BULLE:
                            triABulle(a);
                            break;
                        case FUSION_REC:
                            triFusionRecursif(a);
                            break;
                        case FUSION_NONREC:
                            triFusionNonRecursif(a);
                            break;
                        case QUICK:
                            quicksort(a);
                            break;
                    }
                    avg += endTimer();
                }
                avg /= runs;
                stats[i][j] = avg;
            }
            System.out.println("ok.");
        }

        DecimalFormat format = new DecimalFormat("0.0000");
        System.out.println();
        System.out.println("Temps moyen:");
        System.out.println("    N    |Tri a bulle    |Tri fusion rec |Tri fusion non |Quicksort      |");
        System.out.println("---------|---------------|---------------|---------------|---------------|");
        for (int i = 0; i < stats[0].length; i++) {
            System.out.print((int) Math.pow(2, i + 1) + "\t  ");
            for (double[] stat : stats) {
                System.out.print(format.format(stat[i]) + "\t  ");
            }
            System.out.println();
        }
        System.out.println("---------|---------------|---------------|---------------|---------------|");
    }
}
