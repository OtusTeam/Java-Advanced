/**
 * jarsigner -verify ./jdeps-output/Jdeps.jar
 * <b>
 * jarsigner ./jdeps-output/Jdeps.jar -keystore ./keytool-certs/our_keystore.p12 cert2
 * password stpass123
 * <b>
 * jarsigner -keystore ./keytool-certs/our_keystore.p12 -storetype PKCS12 -storepass stpass123 ./jdeps-output/Jdeps.jar cert2
 */
package com.otus.java.advanced.jdkinstruments.jarsigner;