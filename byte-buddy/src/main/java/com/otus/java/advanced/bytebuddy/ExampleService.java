package com.otus.java.advanced.bytebuddy;

import com.otus.java.advanced.bytebuddy.annotation.OurLog;
import com.otus.java.advanced.bytebuddy.annotation.OurLogger;
import lombok.ToString;

@OurLogger
@ToString
public class ExampleService {

    @OurLog
    public String doSomething(String testValue) {
        return testValue;
    }

    @OurLog(isShowError = true)
    public String doSomethingWithError(String testValue) {
        return testValue;
    }
}
