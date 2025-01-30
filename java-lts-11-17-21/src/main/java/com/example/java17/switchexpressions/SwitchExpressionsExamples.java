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
package com.example.java17.switchexpressions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.util.Random;

/**
 * Provides examples of using Switch Expressions, implemented as a JUnit test case.
 * <p>
 * From JDK 14 (March 2020) onwards, Java allows the switch keyword to be used to declare an expression, whilst
 * continuing to support the original switch statement. In addition to making it easier to write and maintain switch
 * statements by reducing some of the associated boilerplate for common cases, this change is also part of a broader
 * project to enhance Java to support pattern matching. (See below for more details)
 * <p>
 * In summary, switch expressions provide the following new features to address problems with switch statements -
 * 1) The ability to write a single case label to match multiple values, rather than needing one case per value.
 * 2) Variables declared in a case block are now private in scope to that block rather than global for all
 * subsequent case blocks.
 * 3) Execution of case blocks no longer fall through to the next case block by default, which removes the need to
 * write break statements at the end of case blocks in the most common cases, removing a common source of bugs.
 * 4) You can return a value from a case block that can be assigned to a local var, i.e. switch is now an expression.
 *
 * <h2>Further Reading</h2>
 * 1) <a href="https://openjdk.java.net/jeps/361">Java Enhancement Proposal (JEP) 361: Switch Expressions</a>
 * - The original proposal for adding switch expressions to the language, includes motivation and examples.
 * <p>
 * 2) One of the motives for adding Switch Expressions to the language is to allow 'pattern matching' to be coded more
 * concisely and safely. Pattern matching is logic that combines testing whether an object has a certain type or
 * structure, and then conditionally extracting components of its state for further processing. For more details see
 * <a href="https://cr.openjdk.java.net/~briangoetz/amber/pattern-match.html">Pattern Matching for Java, Gavin Bierman and Brian Goetz, September 2018</a>
 */
public class SwitchExpressionsExamples {

    private static final Logger logger = LoggerFactory.getLogger(SwitchExpressionsExamples.class);

    /**
     * Provides an example of a switch expression that uses an enum for its selector variable, which serves to
     * illustrate a number of the new syntax and features of switch expressions, including -
     * <p>
     * 1) The ability to write a single case label to match multiple values, rather than needing one case label per value.
     * <p>
     * 2) Execution of case blocks no longer fall through to the next case block by default, which removes the need to
     * write break statements at the end of case blocks in the most common cases, removing a common source of bugs.
     * <p>
     * 3) You can return a value from a case block that can be assigned to a local var, i.e. switch is now an expression.
     * <p>
     * For comparison purposes the example is first implemented using a switch statement.
     */
    public static void switchOnEnum() {
        final var dayOfWeek = DayOfWeek.of(new Random().nextInt(1, 7));

        // For comparison, example of a traditional switch _statement_, the only option prior to JDK 14
        int numberOfLetters1;
        switch (dayOfWeek) {
            // Handling multiple constants in same way can be done but requires repeating the 'case' label -
            case MONDAY:
            case FRIDAY:
            case SUNDAY:
                numberOfLetters1 = 6;
                break; // By default logic falls through to next case block, so breaks are typically needed
            case TUESDAY:
                numberOfLetters1 = 7;
                break;
            case THURSDAY:
            case SATURDAY:
                numberOfLetters1 = 8;
                break;
            case WEDNESDAY:
                numberOfLetters1 = 9;
                break;
            // Optional default statement, typically needed to check for unhandled cases.
            default:
                throw new IllegalStateException("Logic error. Unknown day of week [" + dayOfWeek + "].");
        }


        // Rewrite of the above example as a switch expression from JDK 14 onwards -

        // Expression returns a result, allowing assigned variable to be declared final
        final int numberOfLetters2 = switch (dayOfWeek) {
            // Matching & handling multiple values in the same way only now requires a single 'case' label with csv
            case MONDAY, FRIDAY, SUNDAY -> 6;
            // New case label syntax '->' signifies to execute only code in this case block and return result, no break needed
            case TUESDAY -> 7;
            // Case blocks with a single statement don't require a return (yield) statement
            case THURSDAY, SATURDAY -> 8;
            case WEDNESDAY -> 9;
            // default statement not needed when switching on enum as compilation error now reported if constant not handled
        };

    }

    /**
     * Provides an example of how to write a switch expression that supports a case block comprising more than one
     * statement.
     */
    public static void caseBlockWithYield() {
        final var phonetics = new String[]{"alfa", "bravo", "charlie", "delta", "echo", "foxtrot"};
        final var phonetic = phonetics[new Random().nextInt(0, phonetics.length - 1)];

        int phoneticLength = switch (phonetic) {
            // Case blocks consisting of multiple statements are supported. They need to be declared with braces, and must
            // return a value using the new 'yield' statement. (The yield keyword is used instead of return to
            // differentiate between returning to the switch expression and returning from the enclosing method).
            case "alfa" -> {
                System.out.println("Matched alfa");
                yield 4;
            }
            case "bravo" -> 5;
            // The default clause also supports blocks of code
            // (The default clause is mandatory in this case as when switching on an arbitrary string the compile can't
            // perform an exhaustive check that all possible values are handled).
            default -> {
                System.out.println("Matched phonetic [" + phonetic + "]");
                yield phonetic.length();
            }

        };
        System.out.println("RESULT: " + phoneticLength);
    }

    public static void main(String[] args) {
        caseBlockWithYield();
    }
}