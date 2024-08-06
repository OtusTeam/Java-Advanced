package com.otus.java.advanced.bytebuddy;

import com.otus.java.advanced.bytebuddy.tracing.annotation.CustomTrace;
import com.otus.java.advanced.bytebuddy.tracing.annotation.CustomTracer;
import lombok.ToString;

@CustomTracer(injectMode = CustomTracer.InjectMode.EXTENDS)
@ToString
public class ExampleServiceImpl implements ExampleService {

    @CustomTrace
//    @CustomTrace(logLevel = Level.INFO, exceptionLogLevel = Level.INFO) // LESSON
//    @CustomTrace(mode = CustomTrace.TraceMode.EXCEPTION, exceptionLogLevel = Level.ERROR) // LESSON
    public String reverse(String value) {
        return new StringBuilder(value).reverse().toString();
    }
}
