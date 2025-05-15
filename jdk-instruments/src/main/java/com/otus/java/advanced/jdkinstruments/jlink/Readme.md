### JDK Instruments Demo — jlink Example

1. Компилируем module-info.java (определяет модуль)  
javac -d jlink-target ./src/main/java/module-info.java
2. Компилируем основной класс  
javac -d jlink-target --module-path jlink-target ./src/main/java/com/otus/java/advanced/jdkinstruments/jlink/Jlink.java
3. Запускаем с указанием module-path и модуля  
java --module-path jlink-target --module java.advanced.course.jdk.instruments.jlink/com.otus.java.advanced.jdkinstruments.jlink.Jlink
### Анализируем зависимости нашего модуля (summary mode)  
jdeps --module-path jlink-target -s --module java.advanced.course.jdk.instruments.jlink

1. Собираем кастомный JRE с нашим модулем  
// jlink [options] –module-path modulepath –add-modules module [, module…] --output <target-directory>    
jlink --module-path "/Users/maksimmakarenko/Library/Java/JavaVirtualMachines/ms-21.0.7/Contents/Home/jmods:./jlink-target" --add-modules java.advanced.course.jdk.instruments.jlink --output jlink-customjre
### Пример для Windows:  
jlink --module-path "C:\Users\mmakarenko\.jdks\openjdk-21.0.1\jmods;C:\Users\SYBERIAN\Desktop\Java-Advanced\jdk-instruments\jlink-target" --add-modules java.advanced.course.jdk.instruments.jlink --output jlink-customjre

# 2. Собираем кастомный JRE с автогенерацией launch-скрипта
rm -rf jlink-customjre
jlink --launcher customjrelauncher=java.advanced.course.jdk.instruments.jlink/com.otus.java.advanced.jdkinstruments.jlink.Jlink --module-path ./jlink-target:/Users/maksimmakarenko/Library/Java/JavaVirtualMachines/ms-21.0.7/Contents/Home/jmods --add-modules java.advanced.course.jdk.instruments.jlink --output jlink-customjre

## Run Custom JRE
## ❌ Неправильный способ (будет ошибка)  
java -classpath ./jlink-customjre/bin --module java.advanced.course.jdk.instruments.jlink/com.otus.java.advanced.jdkinstruments.jlink.Jlink

## ✅ Правильный способ — запуск через custom launcher  
./jlink-customjre/bin/customjrelauncher.bat  # Windows  
./jlink-customjre/bin/customjrelauncher      # Unix/macOS

### jlink позволяет собрать минимальный runtime только с нужными модулями.
### Это уменьшает размер JRE, ускоряет запуск, оптимизирует дистрибутивы для контейнеров и embedded-решений.