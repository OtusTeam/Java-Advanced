/**
 * Выпускаем самоподписанный сертификат:
 * keytool -genkeypair -alias <alias> -keypass <keypass> -validity <validity> -storepass <storepass> -keyalg <algorithm>
 * keytool -genkeypair -alias cert1 -keypass pass123 -validity 365 -storepass stpass123 -keyalg RSA
 * <p>
 * ➡️ Хранилище будет в формате PKCS12 (.p12), это дефолтный формат для macOS и JDK 9+.
 * ➡️ Параметр -keypass не нужен (он совпадает со storepass для PKCS12, иначе warning).
 * <p>
 * Явное указание файла keystore:
 * ❌keytool -genkeypair -alias cert1 -keypass pass123 -validity 365 -storepass stpass123 -keyalg RSA -keystore ./keytool-certs/our_keystore
 * ✅keytool -genkeypair -alias cert2 -validity 365 -storepass stpass123 -keyalg RSA -keystore ./keytool-certs/our_keystore.p12 -dname "CN=Maksim Makarenko, OU=Java Advanced, O=OTUS, L=Colombia, ST=Medellin, C=CO"
 * <p>
 * Просмотр содержимого keystore:
 * keytool -list -storepass stpass123 -keystore ./keytool-certs/our_keystore.p12
 * <p>
 * Просмотр сертификата внутри keystore подробно:
 * keytool -list -v -alias cert1 -storepass stpass123 -keystore ./keytool-certs/our_keystore.p12
 * <p>
 * Переименование alias сертификата:
 * keytool -changealias -alias <old_alias> -destalias <new_alias> -keypass <keypass> -storepass <storepass> -keystore ./keytool-certs/our_keystore.p12
 * keytool -changealias -alias cert1 -destalias cert2 -storepass stpass123 -keystore ./keytool-certs/our_keystore.p12
 * <p>
 * Удаление сертификата по alias:
 * keytool -delete -alias cert2 -storepass stpass123 -keystore ./keytool-certs/our_keystore.p12
 * <p>
 * Просмотр сертификата из файла:
 * keytool -printcert -file <cert_file> -<v>
 * keytool -printcert -file ./keytool-certs/ca/russian_trusted_root_ca.cer
 * keytool -printcert -file ./keytool-certs/ca/russian_trusted_sub_ca.cer
 * <p>
 * Импорт CA-сертификатов в keystore:
 * keytool -importcert -file ./keytool-certs/ca/russian_trusted_root_ca.cer -keystore ./keytool-certs/our_keystore.p12 -alias "ru_root_ca" -storepass stpass123
 * keytool -importcert -file ./keytool-certs/ca/russian_trusted_sub_ca.cer -keystore ./keytool-certs/our_keystore.p12 -alias "ru_sub_ca" -storepass stpass123
 * <p>
 * rm ./keytool-certs/our_keystore.p12
 */
package com.otus.java.advanced.jdkinstruments.keytool;