/*
 *  Copyright 2021-present the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.java17.patternmatching;


/**
 * Provides examples of Java's instanceof operator's support for pattern matching, which was finalised in JDK 16
 * (March 2021), implemented in a JUnit test case.
 * <p>
 * Pattern matching is the term given to the common need to combine testing whether an object has a certain type, and
 * then conditionally extracting components of its state for further processing. It comprises three steps - 1) a test
 * to determine whether variable is of the given type; 2) if it is, a conversion (cast) of the variable to the more
 * specific type, and 3) the declaration of a new local variable of the more specific type so that its behaviour can
 * be used.
 * <p>
 * Java's longstanding instanceof operator has been extended to provide built-in language support for expressing
 * pattern matching. Prior to JDK 16 the operator was already being used to implement pattern matching, but only
 * offered partial support - the operator performed the first step (the type test), but the other two steps (cast
 * and declaration) needed to be manually coded in full. The instanceof operator is now extended to additionally
 * support the other two steps also by adding support for declaring a pattern variable when the object is matched.
 * This reduces the verbosity (boilerplate) of the Java code.
 * <p>
 * A pattern variable is to all intents and purposes a local variable, but with some differences in how its scope
 * is defined.
 * <p>
 * In its first final release in JDK 16 the instanceof operator only supports extracting a single pattern variable -
 * a single variable of the matched type.
 *
 * <h2>Further Reading</h2>
 * 1) <a href="https://openjdk.java.net/jeps/394">JEP 394: Pattern Matching for instanceof</a> - The final Java
 * Enhancement Proposal (JEP) for extending the instanceof operator to support pattern matching, describes the
 * motivation for the enhancement and provides more details, with examples.
 */
public class InstanceOfPatternMatchingExamplesTest {

    /**
     * Provides a simple example of using instanceof pattern matching, in this case to match an object of type String.
     */

    public static void matchString() {
        final Object obj = "foo";

        // For comparison, a pre JDK 16 example of manually implementing pattern matching using instanceof
        if (obj instanceof String) {
            final String s = (String) obj; // boilerplate
            System.out.println("foo is instanceof String class");
            final var anotherString = s.toUpperCase();
        } else {
            throw new IllegalStateException("Expected obj to be of type string");
        }

        // From JDK 16 onwards, the above example can be reimplemented using instanceof support for pattern matching -
        if (obj instanceof String s) { // declares pattern variable 's', in scope within the if block
            final var anotherString = s.toUpperCase();
            System.out.println("foo is instanceof String class");
        } else {
            throw new IllegalStateException("Expected obj to be of type string");
        }
    }

    /**
     * Provides an example which shows that pattern variables created by instanceof are also in scope within an
     * expression used in the condition of an if statement, as well as the block, but only when the earlier part of the
     * expression has definitely matched the pattern.
     */

    public static void patternVariableScopeIfCondition() {
        final Object obj = "foo2";

        // A pattern variable can be used in the right-hand side of an if condition expression that uses a logical AND
        // operator because the pattern has definitely been matched on the left hand side -
        if (obj instanceof String s && s.length() > 1) {
            final var anotherString = s.toUpperCase();
            System.out.println("foo2 is instanceof String class");
        } else {
            throw new IllegalStateException("Expected obj to be of type string");
        }

        // However, a pattern variable can NOT be used in the right-hand sie of an if condition expression that uses a
        // logical OR operator, because the pattern may not have been matched on the left-hand side. The following will not
        // compile -
    /*
    if (obj instanceof String s || s.length() > 1) {
      final var anotherString = s.toUpperCase();
    }
    */
    }

    public static void main(String[] args) {
        matchString();
        patternVariableScopeIfCondition();
    }

}