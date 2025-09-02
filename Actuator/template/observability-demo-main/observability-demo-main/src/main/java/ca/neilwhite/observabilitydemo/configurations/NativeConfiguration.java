package ca.neilwhite.observabilitydemo.configurations;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.dialect.PostgreSQLPGObjectJdbcType;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(NativeConfiguration.RuntimeHints.class)
public class NativeConfiguration {

    static class RuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(org.springframework.aot.hint.RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerPattern("logback-spring.xml");

            hints.reflection().registerType(ObservationRegistry.class, MemberCategory.values());
            hints.reflection().registerType(ObservedAspect.class, MemberCategory.values());

            hints.reflection().registerTypeIfPresent(classLoader, "org.hibernate.dialect.PostgreSQLDialect",
                    hint -> hint.withMembers(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS, MemberCategory.INTROSPECT_PUBLIC_METHODS)
                            .onReachableType(PostgreSQLDialect.class));

            hints.reflection().registerTypeIfPresent(classLoader, "org.postgresql.util.PGobject",
                    hint -> hint.withMembers(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS, MemberCategory.INTROSPECT_PUBLIC_METHODS)
                            .onReachableType(PostgreSQLPGObjectJdbcType.class));
        }
    }
}
