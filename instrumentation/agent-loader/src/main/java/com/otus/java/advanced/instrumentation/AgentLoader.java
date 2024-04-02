package com.otus.java.advanced.instrumentation;

import com.sun.tools.attach.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

public class AgentLoader {

    private static final Logger log = LoggerFactory.getLogger(AgentLoader.class);

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            log.error("Usage: java AgentLoader <target-jvm-pid> <agent-file-path>");
            System.exit(-1);
        }

        String targetJvmPid = args[0];
        String agentFilePath = args[1];

        String argsToAgent = null;
        if (args.length > 2) {
            argsToAgent = args[2];
        }

        if (targetJvmPid == null || targetJvmPid.isBlank()) {
            log.error("JVM PID is not specified");
        }

        if (agentFilePath == null || agentFilePath.isBlank()) {
            log.error("Path to agent lib is not specified");
        }

        log.info("targetJvmPid={}, agentFilePath={}", targetJvmPid, agentFilePath);
        run(targetJvmPid, agentFilePath, argsToAgent);
    }

    private static void run(String jvmIdentifier, String agentFilePath, String agentArgs)
            throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {

        Optional<String> jmvId =
                VirtualMachine.list()
                        .stream()
                        .filter(getPredicate(jvmIdentifier))
                        .findFirst()
                        .map(VirtualMachineDescriptor::id);

        if (jmvId.isEmpty()) {
            log.error("Application {} is not found", jvmIdentifier);
            return;
        }

        File agentFile = new File(agentFilePath);
        String jvmPid = jmvId.get();
        log.info("Attaching to target JVM with PID: {}", jvmPid);

        VirtualMachine jvm = VirtualMachine.attach(jvmPid);
        jvm.loadAgent(agentFile.getAbsolutePath(), agentArgs);
        jvm.detach();

        log.info("Attached to target JVM {} and loaded Java agent successfully", jvmPid);
    }

    private static Predicate<VirtualMachineDescriptor> getPredicate(String jmvIdentifier) {
        Predicate<VirtualMachineDescriptor> predicateByApplicationName;

        try {
            Long.parseLong(jmvIdentifier);
            predicateByApplicationName = jvm -> {
                log.info("jvm:{}", jvm.displayName());
                return jvm.id().equals(jmvIdentifier);
            };

        } catch (NumberFormatException e) {
            predicateByApplicationName = jvm -> {
                log.info("jvm:{}", jvm.displayName());
                return jvm.displayName().contains(jmvIdentifier);
            };
        }

        return predicateByApplicationName;
    }
}
