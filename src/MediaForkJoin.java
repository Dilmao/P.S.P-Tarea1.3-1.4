import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

/**********************************************************************************************************************************************
 *   APLICACIÓN: "MediaForkJoin"                                                                                                              *
 **********************************************************************************************************************************************
 *   PROGRAMACIÓN DE SERVICIOS Y PROCESOS 2DAM  -  Eclipse IDE for Java Developers v2021-09 (4.21.0)                                          *
 **********************************************************************************************************************************************
 *   @author  D.Lacasa                                                                                                                        *
 *   @version 1.0 - Versión inicial del algoritmo.                                                                                            *
 *   @since   10NOV2023                                                                                                                       *
 **********************************************************************************************************************************************
 *   COMENTARIOS:                                                                                                                             *
 *      - Separa un array en dos mitades para despus calcular la media de cada una                                                            *
 **********************************************************************************************************************************************/
public class MediaForkJoin extends RecursiveTask<Double>
{
    private static final int LIMITE_ARRAY = 1000;
    private static final int UMBRAL = 100;

    private short[] a_Vector = null;
    private int a_Inicio, a_Fin = 0;

    MediaForkJoin() { }
    MediaForkJoin(short[] p_Vector, int p_Inicio, int p_Fin) {
        this.a_Vector = p_Vector;
        this.a_Inicio = p_Inicio;
        this.a_Fin = p_Fin;
    }   //MediaForkJoin()

    private double getHalf() {
        double l_Media = 0;
        for (int l_Contador = a_Inicio; l_Contador < a_Fin; l_Contador++) {
            l_Media += a_Vector[l_Contador];
        }
        l_Media /= (a_Fin - a_Inicio);
        return l_Media;
    }   //getHalf()

    @Override
    protected Double compute() {
        if (a_Inicio - a_Fin <= UMBRAL) {
            return getHalf();
        } else {
            int l_Mid = a_Inicio + (a_Fin - a_Inicio) / 2;
            MediaForkJoin l_MitadInferior = new MediaForkJoin(a_Vector, a_Inicio, l_Mid);
            MediaForkJoin l_MitadSuperior = new MediaForkJoin(a_Vector, l_Mid, a_Fin);

            l_MitadInferior.fork();
            double l_ResultadoSuperior = l_MitadSuperior.compute();
            double l_ResultadoInferior = l_MitadInferior.join();

            return (l_ResultadoSuperior + l_ResultadoInferior) / 2;
        }
    }   //compute()

    public short[] crearArray(int p_Longitud)
    {
        short[] l_Array = new short[p_Longitud];
        for (int l_Contador = 0; l_Contador < p_Longitud; l_Contador++)
        {
            l_Array[l_Contador] = (short) l_Contador;
        }
        return l_Array;
    }   //crearArray()

    public static void main(String[] args) {
        //Declaracion de variables
        MediaForkJoin l_Tarea = new MediaForkJoin();
        short[] l_Data = l_Tarea.crearArray(LIMITE_ARRAY);
        ForkJoinPool l_Pool = new ForkJoinPool();

        //Se calcula la media inferior y superior
        int l_MitadArray = LIMITE_ARRAY / 2;
        double l_ResultadoInferior = l_Pool.invoke(new MediaForkJoin(l_Data, 0, l_MitadArray));
        double l_ResultadoSuperior = l_Pool.invoke(new MediaForkJoin(l_Data, l_MitadArray, LIMITE_ARRAY));

        //Se imprimen los resultados
        System.out.println("Media inferior (0 - " + (l_MitadArray-1) + "): " + l_ResultadoInferior);
        System.out.println("Media superior (" + l_MitadArray + " - " + (LIMITE_ARRAY-1) + "): " + l_ResultadoSuperior);
    }   //main()
}   // MediaForkJoin