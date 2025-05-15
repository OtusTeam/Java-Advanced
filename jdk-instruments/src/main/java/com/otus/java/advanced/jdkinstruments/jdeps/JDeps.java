package com.otus.java.advanced.jdkinstruments.jdeps;

// 1. Компилируем исходники в .class файлы (иначе jdeps нечего анализировать):
// javac -d jdeps-target ./src/main/java/com/otus/java/advanced/jdkinstruments/jdeps/*.java

// 2. Создаём обычный JAR-архив для анализа зависимостей (без точки входа):
// jar cf ./jdeps-output/JDeps.jar ./jdeps-target/com/otus/java/advanced/jdkinstruments/jdeps/*.class

// 3. (Опционально) Создаём исполняемый JAR с указанием main-класса для запуска через java -jar:
// jar cfe ./jdeps-output/JDeps.jar com.otus.java.advanced.jdkinstruments.jdeps.JDeps ./jdeps-target/com/otus/java/advanced/jdkinstruments/jdeps/*.class

// 4. Запускаем jdeps для анализа зависимостей JAR-файла (по умолчанию — только прямые зависимости):
// jdeps ./jdeps-output/JDeps.jar

// 5. То же самое, но с анализом всех транзитивных зависимостей рекурсивно:
// jdeps -recursive  ./jdeps-output/JDeps.jar

// 6. Анализируем стороннюю библиотеку
// jdeps -recursive -summary ./libs/guava-32.1.2-jre.jar
/* wget -P ./libs \
https://repo1.maven.org/maven2/org/springframework/spring-core/6.1.6/spring-core-6.1.6.jar \
https://repo1.maven.org/maven2/org/springframework/spring-jcl/6.1.6/spring-jcl-6.1.6.jar */

// jdeps -recursive -summary ./libs/spring-core-6.1.6.jar ./libs/spring-jcl-6.1.6.jar
// jdeps --multi-release 21 -recursive -summary ./libs/spring-core-6.1.6.jar ./libs/spring-jcl-6.1.6.jar
public class JDeps {

    public static void main(String[] args) {
        printGreeting("JDeps demo");
    }

    @Deprecated
    private static void printGreeting(String greeting) {
        System.out.println(greeting);

        System.out.println(new Double("1"));
    }
}
