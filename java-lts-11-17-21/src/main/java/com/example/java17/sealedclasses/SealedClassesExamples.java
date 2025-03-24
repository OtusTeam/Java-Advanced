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
package com.example.java17.sealedclasses;

/**
 * Provides examples of the use of Sealed Classes, a new language feature that was finalised in JDK 17 (Sept 2021).
 * <p>
 * An author of a class or interface may want to restrict the classes which can extend (subclass) or implement it, for
 * example to limit the impact of potential breaking changes in the future, or to avoid the need to write additional
 * code to defend against unknown, future subclasses. Prior to JDK 17 there were only two ways in which developers
 * could achieve this.
 * <br>
 * - 1) You could (and still can) prevent any class from extending your class by declaring it as final; or
 * <br>
 * - 2) You could (and still can) limit the classes that can extend your class to only those in the same package by
 * either declaring the class, or all of its constructors package-private (the default when no accessor modifier is
 * specified). The downside of this approach is that it not only restricts which classes can extend from the class,
 * but it also restricts which classes can use the class, which is often not desirable.
 * <p>
 * Sealed Classes provide a new way for developers to restrict which classes can extend or implement a class or
 * interface, without constraining access / usage of said class or interface, by optionally declaring a class or
 * interface as 'sealed' and specifying an explicit list of classes that may extend or implement it. Restrictions
 * apply to those permitted classes. See the example below for more details.
 * <p>
 * Usage of the Sealed Class feature is limited to the static declaration of classes and interfaces. As a result this
 * example, unlike others in the project is _not_ runnable (as a JUnit test), but merely provides an example of how
 * to declare a Sealed Class and its subclasses.
 *
 * <h2>Further Reading</h2>
 * 1) <a href="https://openjdk.java.net/jeps/409">JEP 409: Sealed Classes</a> - The final Java Enhancement Proposal
 * (JEP) for Sealed Classes, describes the goals and motivation for the feature, plus a detailed description of how it
 * is proposed to work (its specification and design) including examples.
 */
public class SealedClassesExamples {

    /**
     * Declares an abstract superclass Shape (to support polymorphism and inheritance of state (fields) and/or behaviour
     * (methods)). Additionally restricts the classes which can extend it by declaring itself as 'sealed' and using the
     * 'permits' keyword to specify the list of one or more classes.
     * <p>
     * A sealed class such as this imposes several constraints on its permitted subclasses, including -
     * <p>
     * - 1) A permitted subclass must either be in the same module as the sealed class (if it has one, which is not
     * the case in this example) or else the same package.
     * <br>
     * - 2) A permitted subclass must extend the sealed class (otherwise the compiler will report an error).
     * <br>
     * - 3) A permitted subclass must itself specify how it propagates the sealed nature of its superclass in one of
     * the following ways a) Declare itself as final, preventing itself being extended further; b) Also declaring
     * itself as sealed, allowing itself to be extended further but only by a further list of permitted classes; or c)
     * Declaring itself as non-sealed, reverting to allowing itself to be further extended by any unknown class. Examples
     * of each of these options are shown below.
     */
    abstract sealed class Shape permits Circle, Rectangle, Square {
    }

    // This permitted subclass declares itself as final, preventing itself being extended further.
    final class Circle extends Shape {
    }

    // This permitted subclass declares itself as sealed, allowing itself to be extended further but only by a further
    // specified list of permitted classes.
    sealed class Rectangle extends Shape permits TransparentRectangle, FilledRectangle {
    }

    // This permitted subclass declares itself as non-sealed, reverting to allowing itself to be further extended by any
    // unknown class
    non-sealed class Square extends Shape {
    }

    // Proves that declaring the Square class as non-sealed allows it to extended by any class, such as this one.
    class FilledSquare extends Square {
    }

    final class TransparentRectangle extends Rectangle {
    }

    final class FilledRectangle extends Rectangle {
    }
}