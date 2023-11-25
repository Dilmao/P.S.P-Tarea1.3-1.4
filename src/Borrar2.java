import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**********************************************************************************************************************************************
 *   APLICACIÓN: "MaximoPrimoForkJoinV2"                                                                                                      *
 **********************************************************************************************************************************************
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM  -  Eclipse IDE for Java Developers v2021-09 (4.21.0)                                          *
 **********************************************************************************************************************************************
 *   @author D.Lacasa                                                                                                                         *
 *   @version 1.0 - Versión inicial del algoritmo.                                                                                            *
 *   @since 22NOV2023                                                                                                                         *
 **********************************************************************************************************************************************
 *   COMENTARIOS:                                                                                                                             *
 *      - El codigo generara un array de 100 posiciones, cada posicion contendra su numero de indice                                          *
 *      - Recorrera todas las posiciones del array recursivamente para encontrar el mayor multiplo de 7                                       *
 **********************************************************************************************************************************************/

public class Borrar2 extends RecursiveTask<Integer> {
    private static final int UMBRAL = 10;
    private static final int LONGITUD_ARRAY = 100;

    private int[] a_Vector  = null;
    private int a_Inicio, a_Fin = 0;

    Borrar2() { }

    Borrar2(int[] p_Vector, int p_Inicio, int p_Fin) {
        this.a_Vector = p_Vector;
        this.a_Inicio = p_Inicio;
        this.a_Fin = p_Fin;
    }

    private int getMaxRec() {
        int l_Medio = (a_Inicio + a_Fin) / 2;

        Borrar2 l_Tarea1 = new Borrar2(a_Vector, a_Inicio, l_Medio);
        Borrar2 l_Tarea2 = new Borrar2(a_Vector, l_Medio, a_Fin);

        l_Tarea1.fork();
        l_Tarea2.fork();

        int l_Resultado1 = l_Tarea1.join();
        int l_Resultado2 = l_Tarea2.join();

        return (l_Resultado1 + l_Resultado2);
    }

    private int getMaxSec() {
        int l_Maximo = 0;

        for (int l_Contador = a_Inicio; l_Contador < a_Fin; l_Contador++) {
            if (a_Vector[l_Contador] % 7 == 0 && a_Vector[l_Contador] > l_Maximo) {
                l_Maximo = a_Vector[l_Contador];
            }
        }

        return l_Maximo;
    }

    @Override
    protected Integer compute() {
        if (a_Fin - a_Inicio <= UMBRAL) {
            return getMaxSec();
        } else {
            return getMaxRec();
        }
    }

    private int[] crearArray(int p_Longitud) {
        int[] l_Array = new int[p_Longitud];

        for (int l_Contador = 0; l_Contador < p_Longitud; l_Contador++) {
            l_Array[l_Contador] = l_Contador;
        }

        return l_Array;
    }

    public static void main(String[] args) {
        Borrar2 l_Tarea = new Borrar2();
        int[] l_Data = l_Tarea.crearArray(LONGITUD_ARRAY);
        int l_Inicio = 0;
        int l_Fin = l_Data.length;
        int l_Resultado;
        ForkJoinPool l_Pool = new ForkJoinPool();

        l_Tarea = new Borrar2(l_Data, l_Inicio, l_Fin);
        l_Resultado = l_Pool.invoke(l_Tarea);

        System.out.println("Maximo multiplo de 7: " + l_Resultado);
    }
}
