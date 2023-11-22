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
 *      - Separa un array en dos mitades para despus calcular la media de cada una                                                            *                                                                                                                                     *
 **********************************************************************************************************************************************/

public class MaximoPrimoForkJoin extends RecursiveTask<Short>
{
    // private static final int UMBRAL = 10_000;     // Umbral alternativo para pruebas.
    private static final int UMBRAL = 10_000_000;
    private static final int LONGITUD_ARRAY = 100_000_000;
    private static final int DUMMY_MAX = 9_999;
    private short[] a_Vector = null;
    private int a_Inicio, a_Fin = 0;


    public MaximoPrimoForkJoin() { }


    public MaximoPrimoForkJoin(short[] p_Vector, int p_Inicio, int p_Fin)
    {
        this.a_Vector = p_Vector;
        this.a_Inicio = p_Inicio;
        this.a_Fin = p_Fin;
    }   // MaximoPrimoForkJoin()


    private short getMaxRec()     // Ejecución recursiva.
    {
        int l_Medio = ( (a_Inicio + a_Fin) / 2 ) + 1;
        MaximoPrimoForkJoin l_Tarea1 = new MaximoPrimoForkJoin(a_Vector, a_Inicio, l_Medio);
        MaximoPrimoForkJoin l_Tarea2 = new MaximoPrimoForkJoin(a_Vector, l_Medio, a_Fin);

        // No tratamos el caso trivial pues cortaremos la recursividad en el UMBRAL.
        l_Tarea1.fork();
        l_Tarea2.fork();

        return ( (short) Math.max(l_Tarea1.join(), l_Tarea2.join()) );
    }   // getMaxRec()


    private short getMaxSec()     // Ejecución secuencial/iterativa.
    {
        short l_Max = a_Vector[a_Inicio];
        int l_Contador;

        // Realizamos la búsqueda de forma iterativa quedándonos con el valor mayor.
        for (l_Contador = a_Inicio+1; l_Contador < a_Fin; l_Contador++)
            if (a_Vector[l_Contador] > l_Max) l_Max = a_Vector[l_Contador];

        return (l_Max);
    }   // getMaxSec()


    @Override
    protected Short compute()     // Llamado por invoke() e invokeAll().
    {
        short l_Retorno;

        if( (a_Fin - a_Inicio) <= UMBRAL ) l_Retorno = getMaxSec();   // Ejecución secuencial/iterativa.
        else l_Retorno = getMaxRec();   // Ejecución recursiva.

        return (l_Retorno);
    }   // compute()


    short[] crearArray(int p_Longitud)
    {
        short[] l_Array = new short[p_Longitud];
        int l_Contador;

        for (l_Contador = 0; l_Contador < p_Longitud; l_Contador++)
        {
            // Decide un lugar fijo donde insertar el máximo para que
            // el tiempo de cada búsqueda en concreto no dependa de dónde esté.
            if (l_Contador == ((short)(p_Longitud*0.9)))  l_Array[l_Contador] = DUMMY_MAX;
            else l_Array[l_Contador] = (short) (1_000 * Math.random());
        }

        return (l_Array);
    }   // crearArray()


    public static void main(String[] args)
    {
        MaximoPrimoForkJoin l_Tarea = new MaximoPrimoForkJoin();
        short[] l_Data = l_Tarea.crearArray(LONGITUD_ARRAY);
        int l_Inicio = 0;
        int l_Fin = l_Data.length;
        int l_ResultadoInvoke;
        int l_ResultadoJoin;
        long l_TiempoInicial = System.currentTimeMillis();
        ForkJoinPool l_Pool = new ForkJoinPool();

        System.out.println("Inicio del cálculo.");

        // Crea la tarea, la lanza, y obtiene el resultado "invoke".
        l_Tarea = new MaximoPrimoForkJoin(l_Data, l_Inicio, l_Fin);
        l_ResultadoInvoke = l_Pool.invoke(l_Tarea);
        // Obtención del resultado "join".
        l_ResultadoJoin = l_Tarea.join();

        System.out.println("Milisegundos empleados: " + (System.currentTimeMillis() - l_TiempoInicial));
        System.out.println("El máximo según ‘invoke’ es: " + l_ResultadoInvoke);
        System.out.println("Coincide con el máximo según ‘join’ que es: " + l_ResultadoJoin);
    }   // main()

}   // MaximoPrimoForkJoin