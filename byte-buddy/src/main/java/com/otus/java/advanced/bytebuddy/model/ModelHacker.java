package com.otus.java.advanced.bytebuddy.model;

import aj.org.objectweb.asm.Opcodes;
import aj.org.objectweb.asm.Type;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.jar.asm.AnnotationVisitor;
import net.bytebuddy.jar.asm.FieldVisitor;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.persistence.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.HashSet;

@Slf4j
//@Component
public class ModelHacker {

    public static final String ENTITY_PATH = "com.otus.java.advanced.bytebuddy.model";
    public static final String DICTIONARY = "Dictionary";

    public ModelHacker() {
        try {
            generateModifiedClasses();
        } catch (IOException ex) {
            log.error("It was not possible to modify the classes, please restart");
        }
    }

    private void generateModifiedClasses() throws IOException {
        final HashSet<Class<?>> entityClasses = extractModelClasses();
        ByteBuddyAgent.install();

        log.debug("Start modifying bytecode of entity classes");
        for (final Class<?> entityClass : entityClasses) {
            log.debug("BEFORE {} modifying bytecode: {}", entityClass.getSimpleName(), getByteCode(entityClass));
            ByteBuddy byteBuddy = new ByteBuddy();
            DynamicType.Builder<?> name = byteBuddy
                    .redefine(entityClass)
                    .visit(removeFieldAnnotation(GeneratedValue.class))
                    .field(ElementMatchers.isAnnotatedWith(Access.class))
                    .annotateField(AnnotationDescription.Builder.ofType(Basic.class).build())
                    .visit(removeFieldAnnotation(Access.class))
                    .field(ElementMatchers.isAnnotatedWith(Version.class))
                    .annotateField(AnnotationDescription.Builder.ofType(Basic.class).build())
                    .visit(removeFieldAnnotation(Version.class))
                    .visit(removeMethodAnnotation(PreUpdate.class))
                    .visit(removeMethodAnnotation(PrePersist.class))
                    .name(entityClass.getName());
            DynamicType.Unloaded<?> make = name.make();
            DynamicType.Loaded<?> load =
                    make.load(entityClass.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
            Class<?> hackedClass = load.getLoaded();

            log.debug("AFTER {} modifying bytecode: {}", hackedClass.getSimpleName(), getByteCode(hackedClass));
            log.info("Class {} was been modified", entityClass.getSimpleName());
        }
        log.debug("End modifying bytecode of entity classes");
    }

    private HashSet<Class<?>> extractModelClasses() {
        final Reflections reflections = new Reflections(ENTITY_PATH, new SubTypesScanner(false));
        final HashSet<Class<?>> result = new HashSet<>(reflections.getSubTypesOf(Object.class));
        result.removeIf(
                x -> x.getDeclaredAnnotationsByType(Table.class).length == 0
                        || x.getSimpleName().lastIndexOf(DICTIONARY) != -1
        );
        return result;
    }

    private StringWriter getByteCode(Class<?> classToLog) throws IOException {
        ClassReader reader = new ClassReader(classToLog.getName());
        StringWriter sw = new StringWriter();
        TraceClassVisitor tcv = new TraceClassVisitor(new PrintWriter(sw));
        reader.accept(tcv, 0);
        return sw;
    }

    private AsmVisitorWrapper.ForDeclaredFields removeFieldAnnotation(Class<? extends Annotation> annotationClass) {
        return new AsmVisitorWrapper.ForDeclaredFields()
                .field(ElementMatchers.isAnnotatedWith(annotationClass),
                       (instrumentedType, fieldDescription, fieldVisitor) -> new FieldVisitor(Opcodes.ASM5, fieldVisitor) {
                           @Override
                           public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                               return Type.getDescriptor(annotationClass).equals(descriptor) ? null : super.visitAnnotation(descriptor, visible);
                           }
                       });
    }

    private AsmVisitorWrapper.ForDeclaredMethods removeMethodAnnotation(Class<? extends Annotation> annotationClass) {
        return new AsmVisitorWrapper.ForDeclaredMethods()
                .method(ElementMatchers.isAnnotatedWith(annotationClass),
                        (instrumentedType, instrumentedMethod, methodVisitor, implementationContext, typePool, writerFlags, readerFlags)
                                -> new MethodVisitor(Opcodes.ASM5, methodVisitor) {
                            @Override
                            public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                                return Type.getDescriptor(annotationClass).equals(descriptor) ? null : super.visitAnnotation(descriptor, visible);
                            }
                        });
    }
}