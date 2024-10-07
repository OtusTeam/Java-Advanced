package com.otus.java.advanced;

import java.util.ArrayList;

public class GcTest {
    private static final ArrayList al = new ArrayList();

    /*
    1) Pause Young
    2) Concurrent Mark Cycle
    3) Pause Cleanup
    4) Pause Young (Prepare Mixed)
     */
    public static void main(
            String[] args
    ) {
        System.out.println("Start");

        while (true) {
            for (int i = 0; i < 4096; i++) {
                al.add(new byte[1024 * 512]);
            }
        }

        //System.out.println("End");
    }

    /*
    добавить в параметры запуска VM Options флаг -XX:+PrintGCDetails или -Xlog:gc*

    1 Basic Command Line
    To enable the G1 Collector use: -XX:+UseG1GC
        Here is a sample command line for starting the demo:
        java -Xmx50m -Xms50m -XX:+UseG1GC -XX:MaxGCPauseMillis=200

    Key Command Line Switches
    -XX:+UseG1GC - Tells the JVM to use the G1 Garbage collector.
    -XX:MaxGCPauseMillis=200 - Sets a target for the maximum GC pause time. This is a soft goal,
        and the JVM will make its best effort to achieve it. Therefore, the pause time goal will sometimes not be met.
        The default value is 200 milliseconds.
    -XX:InitiatingHeapOccupancyPercent=45 - Percentage of the (entire) heap occupancy to start a concurrent GC cycle.
        It is used by G1 to trigger a concurrent GC cycle based on the occupancy of the entire heap, not just one of
        the generations. A value of 0 denotes 'do constant GC cycles'.
        The default value is 45 (i.e., 45% full or occupied).

    Опции -XX:ParallelGCThreads=? и -XX:ConcGCThreads=? задают количество потоков, которые будут использоваться для
        сборки мусора и для выполнения цикла пометок соответственно.

    Если вас не устраивает автоматический выбор размера региона, вы можете задать его вручную с помощью опции
        -XX:G1HeapRegionSize=?. Значение должно быть степенью двойки, если мерить в мегабайтах.
        Например, -XX:G1HeapRegionSize=16m.

    При желании можно изменить порог заполненности кучи, при достижении которого инициируется выполнение цикла
        пометок и переход в режим смешанных сборок. Это делается опцией
        -XX:InitiatingHeapOccupancyPercent=?, принимающей значение в процентах. По умолчанию, этот порог равен 45%.

    Если же вы решите залезть в дебри настроек G1 по-глубже, то можете включить дополнительные функции опциями
        -XX:+UnlockExperimentalVMOptions и -XX:+AggressiveOpts и поиграть с экспериментальными настройками.
     */
}