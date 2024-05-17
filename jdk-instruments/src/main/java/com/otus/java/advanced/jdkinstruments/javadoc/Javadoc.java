package com.otus.java.advanced.jdkinstruments.javadoc;

/**
 * Hero is the main entity we'll be using to . . .
 * <p>
 * Please see the {@link java.lang.Object} class for true identity
 * @author java user
 * use javadoc -d javadoc-output .\src\main\java\com\otus\java\advanced\jdkinstruments\javadoc\* -tag city:a:"Notable city is:"
 */
public class Javadoc {

    /**
     * The name
     */
    private String name;

    /**
     * <p>This is a simple description of the method. . .
     * <a href="https://docs.oracle.com/en/java/javase/11/javadoc/javadoc.html">Справка по javadoc</a>
     * </p>
     * @param stroke любое полезное описание параметра метода или входных данных, которые он должен ожидать.
     * @return описание возвращаемого значения
     * @throws Exception описание выбрасываемого исключения
     * @see <a href="http://www.google.com">google</a>
     * @since 1.0
     * @city Moscow
     */
    public String doSomething(String stroke) throws Exception {
        // do things
        return stroke;
    }
}
