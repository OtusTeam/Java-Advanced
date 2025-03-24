/**
 * выпускаем сертификат
 * keytool -genkeypair -alias <alias> -keypass <keypass> -validity <validity> -storepass <storepass> -keyalg <algorythm>
 * keytool -genkeypair -alias cert1 -keypass pass123 -validity 365 -storepass stpass123 -keyalg RSA
 * хранилище для ключей создастся автоматически
 * или
 * keytool -genkeypair -alias cert1 -keypass pass123 -validity 365 -storepass stpass123 -keyalg RSA -keystore .\keytool-certs\our_keystore
 * <p>
 * просмотр хранилища сертификатов
 * keytool -list -storepass stpass123 -keystore .\keytool-certs\our_keystore
 * <p>
 * смотрим подробно сертификат
 * keytool -list -v -alias cert1 -storepass stpass123 -keystore .\keytool-certs\our_keystore
 * <p>
 * переименовать сертификат
 * keytool -changealias -alias <alias> -destalias <new_alias> -keypass <keypass> -storepass <storepass>
 * keytool -changealias -alias cert1 -destalias cert2 -keypass pass123 -storepass stpass123 -keystore .\keytool-certs\our_keystore
 * <p>
 * удалить сертификат
 * keytool -delete -alias cert2 -storepass stpass123 -keystore .\keytool-certs\our_keystore
 * <p>
 * <p>
 * просмотр сертификатов
 * keytool -printcert {-file cert_file} {-v}
 * keytool -printcert -file .\keytool-certs\ca\russian_trusted_root_ca.cer
 * keytool -printcert -file .\keytool-certs\ca\russian_trusted_sub_ca.cer
 * <p>
 * импорт CA сертификата
 * keytool -importcert -file .\keytool-certs\ca\russian_trusted_root_ca.cer -keystore .\keytool-certs\our_keystore -alias "ru_root_ca" -storepass stpass123
 * keytool -importcert -file .\keytool-certs\ca\russian_trusted_sub_ca.cer -keystore .\keytool-certs\our_keystore -alias "ru_sub_ca" -storepass stpass123
 */
package com.otus.java.advanced.jdkinstruments.keytool;