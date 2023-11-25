
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
 *      - El codigo generara un array de 10 posiciones, cada posicion contendra su numero de indice                                           *
 *      - Recorrera todas las posiciones del array paralelizando las siguientes dos operaciones:                                              *
 *          - Multiplica el número por -1                                                                                                     *
 *          - Multiplicar el número por 2                                                                                                     *
 *      - Se debera mostrar por pantalla la posicion del array '=' operacion1 '+' operacion2 '=' suma de ambas operaciones                    *
 *      - El resultado final debera ser del tipo: 1 = -1 + 2 = 1                                                                              *
 **********************************************************************************************************************************************/

public class Borrar1 {
    private static final int LONGITUD_ARRAY = 10;

    private Borrar1(){  }

    private static int[] crearArray() {
        int[] l_Array = new int[Borrar1.LONGITUD_ARRAY];

        for (int l_Contador = 0; l_Contador < Borrar1.LONGITUD_ARRAY; l_Contador++) {
            l_Array[l_Contador] = l_Contador;
        }

        return l_Array;
    }

    public static void main(String[] args) {
        Borrar1 l_Borrar = new Borrar1();
        int[] l_Array = crearArray();
        int l_Operacion1=1;
        int l_Operacion2=1;



        for (int l_Numero : l_Array) {
            System.out.println(l_Numero + " = " + l_Operacion1 + " + " + l_Operacion2 + " = " + (l_Operacion1+l_Operacion2));
        }
    }
}
