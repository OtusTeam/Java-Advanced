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
package com.example.java17;

import com.example.java17.records.Positive;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

/**
 * Provides examples of the use of Records, a new language feature that was finalised in JDK 16 (March 2021),
 * implemented as a JUnit test case.
 * <p>
 * A {@link Record Record} is a new type of Java class that is designed to make it easier to declare
 * immutable data-structures in Java, with less code than has traditionally been necessary, and in so doing also make
 * the code easier to read, interpret and maintain. This is achieved by the Java compiler using a declaration of a
 * Record's state (which is specified in terms of component field names and types) to auto-generate standard
 * (boilerplate) implementations of members including corresponding fields, a constructor, accessor methods, and
 * equals and hashCode methods.
 * <p>
 * Records are implicitly final (cannot be extended) and their state is immutable. They are designed primarily to
 * support implementing 'nominally-typed' (types declared by name, rather than structure) tuples (data-structures).
 * Common use-cases for Records include declaring Value Objects, Data Transfer Objects (DTO), composite return types,
 * and composite Map keys.
 *
 * <h2>Further Reading</h2>
 * 1) <a href="https://openjdk.java.net/jeps/395">JEP 395: Records</a> - The final Java Enhancement Proposal (JEP)
 * for Records, describes the goals and motivation for the feature, plus a detailed description of how it is proposed
 * to work (its specification and design) including examples.
 */
public class RecordsExamplesTest {

  /**
   * Provides a simple, basic example of how a Record is declared, and the members (constructor, fields, accessors and
   * other standard Object methods) that are auto-generated for it.
   */
  @Test
  public void test_declarationAndGeneratedMembers() {

    // Pre JDK 16 an immutable data-structure needed to be declared as a Class. As shown in the following example,
    // which declares a value object representing a permitted range, when you included overriding Object methods, which
    // you're obliged to do to ensure correct behaviour, this requires (writing & generating) a lot of code. The
    // verbosity of this code increases the reading and maintenance burden.
    class RangeV1 {
      private final int min;
      private final int max;

      public RangeV1(int min, int max) {
        this.min = min;
        this.max = max;
      }

      public int getMin() {
        return this.min;
      }

      public int getMax() {
        return this.max;
      }

      @Override
      public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeV1 otherRange = (RangeV1) o;
        return this.min == otherRange.min && this.max == otherRange.max;
      }

      @Override
      public int hashCode() {
        return Objects.hash(this, min, this.max);
      }

      @Override
      public String toString() {
        return "Range[min=" + this.min + ", max=" + this.max + "]";
      }
    }

    // From JDK 16 onwards, an equivalent data structure can be declared far more concisely using the new Record class.
    // Assuming you don't require any customisation, the declaration can be as concise as a single line, as shown below.
    // A Record declaration comprises a mandatory name, a 'header' and a 'body'.
    // - the header, surrounded by parenthesis, declares the type and name of the Record's 'components' (fields) that
    // constitute its state
    // - the body, surrounded by curly braces, supports customising and extending the generated methods
    record RangeV2(int min, int max) {}

    // Instances of Record are instantiated using the new keyword, in the same way as classes.
    // By default a Record provides an implicit constructor (with the same access modifier as the Record class) which
    // accepts values for its declared components and assigns them to the corresponding generated private fields.
    final int min = 1, max = 2;
    final RangeV2 range = new RangeV2(min, max);

    // A Record provides a public accessor method for each declared component (field), of the same name -
    assertThat(range.min()).isEqualTo(min);
    assertThat(range.max()).isEqualTo(max);

    // A Record provides a sensible, default implementation of Object.toString(), that includes the state of every field
    assertThat(range.toString()).isEqualTo("RangeV2[min=" + min +", max=" + max + "]");

    // A Record provides an implementation of Object.equals() that tests for equality based on the value of every
    // component field. (This allows Records to be used to implement Value Objects)
    assertThat(range).isEqualTo(new RangeV2(min, max));
    assertThat(range).isNotEqualTo(new RangeV2(min+1, max+1));
    assertThat(range).isNotEqualTo(new RangeV1(min, max));

    // A Record provides a correct implementation of Object.hashCode(), as required when overriding Object.equals()
    assertThat(range.hashCode()).isEqualTo(new RangeV2(min, max).hashCode());
  }

  /**
   * Provides an example of how a Record's canonical constructor can be overridden by being explicitly specified.
   * <p>
   * By default a Record provides an implicit, so-called canonical constructor, with the same access modifier as
   * declared for the Record class, which accepts values for its declared components and assigns them to the
   * corresponding generated private (final) fields. However, this so-called canonical constructor can also be declared
   * explicitly, effectively overriding the one provided by default, but it still has to adhere to strict rules, as
   * shown in this example.
   * <p>
   * Note the number of use cases in which you need to or should override the canonical constructor are few.
   * In many cases using a compact constructor, or declaring a custom constructor, is a better alternative. See
   * examples {@link #test_compactConstructor()} and {@link #test_alternativeConstructor()}. One possible use-case where
   * you might choose override a canonical constructor instead is to make defensive copies of the parameters, as
   * shown in this example.
   */
  @Test
  public void test_overrideCanonicalConstructor() {

    record PagedResult(List<String> items, boolean moreItems) {

      // Explicit declaration (override) of the Record's canonical constructor -
      // - Parameters of the constructor match the Record header (as would the default canonical constructor).
      // - Like other supported types of Record constructor, the method's access modifier can be specified but must
      // provide at least as much access as the Record class itself.
      PagedResult(List<String> items, boolean moreItems) {
        // In this example, the only reason to override the canonical constructor is to support defensively copying
        // one of the args before assigning it to the corresponding field -
        this.items = List.copyOf(items);
        // If you override the canonical constructor you assume responsibility for assigning the value of all
        // other fields. It's a compile-time error not to.
        this.moreItems = moreItems;
      }
    }

    final PagedResult result = new PagedResult(List.of("item1"), true);
    assertThat(result.moreItems()).isTrue();
  }

  /**
   * Provides an example of using a Record's compact constructor to execute additional logic prior to the constructor
   * arguments being assigned to the Record's corresponding fields (initialising its state).
   * <p>
   * Compact constructors are typically used to support adding custom validation logic for the params, or some other
   * such preprocessing / treatment of those params, before they're stored as fields. Whilst this can be done by
   * overriding the default record constructor, a compact constructor requires less code as they do not require
   * constructor args to be respecified in the declaration of the constructor, and if the constructor returns
   * successfully the value of the constructor args continue to be automatically assigned to their corresponding fields.
   * <p>
   * This example also illustrates that Records can declare additional custom methods.
   */
  @Test
  public void test_compactConstructor() {

    record Range(int min, int max) {
      // Example declaration of a compact constructor - constructor params do not need to be respecified
      Range {
        // Logic included in a compact constructor is executed prior to assigning params to fields
        validateMinAndMaxArgs(min, max);

        // Note - When using a compact constructor it's unnecessary to explicitly code assigning parameters to fields
        // as this continues to be done for you automatically when the constructor executes and returns successfully.
        // Indeed, it's a compile-time error to attempt to assign fields in a compact constructor.
      }

      // As shown in this example, custom (instance or static) methods can also be included in Record declarations -
      private static void validateMinAndMaxArgs(int min, int max) {
        if (min < 0)
          throw new IllegalArgumentException("min must be zero or greater.");
        if (max < min)
          throw new IllegalArgumentException("max must be greater than min.");
      }
    }

    final Throwable actualThrowable = catchThrowable(() -> new Range(-1, 2));
    assertThat(actualThrowable)
      .as("Expected IllegalArgumentException to be thrown by validation in Range record's compact constructor.")
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("min");

    final int min = 1, max = 2;
    final Range range = new Range(1, 2);
    assertThat(range.min())
      .as("Expected constructor arg to be automatically assigned to Record's 'min' field even when using compact " +
        "constructor.")
      .isEqualTo(min);
    assertThat(range.max()).isEqualTo(max);
  }

  /**
   * Provides an example of Record support for declaring alternative (or custom) constructors.
   * <p>
   * In addition to compact constructors (see {@link #test_compactConstructor()}) and overriding canonical
   * constructors (see {@link #test_overrideCanonicalConstructor()}, Records also support declaring alternative
   * constructors. However, restrictions apply. For example, an alternative constructor _must_ delegate to another
   * constructor (and ultimately) the canonical constructor.
   * <p>
   * Common use-cases for using alternative constructors include providing default values for one or more constructor
   * args (static factory methods could also be used to support this); or using a composite value argument to supply
   * the value for multiple record fields.
   */
  @Test
  public void test_alternativeConstructor() {

    record Range(int min, int max) {
      // By the way, Records support declaring static members, including constants, e.g. -
      static final int DEFAULT_MAX = Integer.MAX_VALUE;

      // Example of an alternative Record constructor to support applying a default value for one of args
      Range(int min) {
        // Delegation to another constructor (in this case the canonical constructor) is mandated by the compiler
        this(min, DEFAULT_MAX);
      }
    }

    final int min = 0;
    final Range range = new Range(0);
    assertThat(range.min()).isEqualTo(min);
    assertThat(range.max()).isEqualTo(Range.DEFAULT_MAX);
  }

  /**
   * Provides an example that shows Records are permitted to implement interfaces.
   * <p>
   * Records are implicitly final classes so cannot be declared as (abstract or) extending other classes. (This
   * restriction emphasises that a Record's state is defined solely by its declaration and cannot be enhanced by
   * another class). However, Records can implement one or more interfaces. As shown in this example, this includes
   * inheriting behaviour in the form of an implemented interface's default methods.
   */
  @Test
  public void test_implementInterface() {

    interface Range {
      int min();
      int max();
      default int calculateSize(int min, int max) {
        return max - min;
      }
    }

    record DefaultRange(int min, int max) implements Range { }

    final DefaultRange range = new DefaultRange(1, 5);
    assertThat(range.calculateSize(range.min(), range.max())).isEqualTo(range.max() - range.min());
  }

  /**
   * Provides an example of how Records support applying annotations to its members, and explains how you can control
   * which ones.
   * <p>
   * The required annotation(s) are always declared on the component(s) in the Record header. As shown in this
   * example, by default the annotation(s) will be applied to ALL of the generated members of the Record, including
   * its canonical constructor, accessor methods, and fields. This behaviour may not always be desirable. You can
   * control which of the generated members the annotations are applied to by specifying the
   * {@link java.lang.annotation.ElementType} in the declaration of the annotation, e.g.
   * {@code @Target(ElementType.PARAMETERS)} for constructor params, {@code @Target(ElementType.METHOD)} for accessor
   * methods, and/or {@code @Target(ElementType.FIELD)}. However, this is only possible if you are writing you're own
   * annotations, and not if you're using third party library of annotations.
   *
   * @throws Exception if an unexpected error occurs on execution of this test.
   */
  @Test
  public void test_annotationsAreByDefaultAppliedToAllGeneratedRecordMembers() throws Exception {

    // Annotation(s) can only be declared for the components in Record header, e.g.
    record Range(@Positive(message = "min must be greater than zero.") int min,
                 @Positive(message = "max must be greater than zero.") int max) { }

    // By default, annotations declared on Record components are applied to all generated members including -

    // Parameters of the Record's canonical constructor
    final Constructor<Range> constructor = Range.class.getDeclaredConstructor(int.class, int.class);
    assertThat(constructor.getParameters()[0].getDeclaredAnnotations()[0].annotationType()).isEqualTo(Positive.class);
    assertThat(constructor.getParameters()[1].getDeclaredAnnotations()[0].annotationType()).isEqualTo(Positive.class);

    // The Record's accessor methods
    final Method minMethod = Range.class.getDeclaredMethod("min");
    assertThat(minMethod.getDeclaredAnnotations()[0].annotationType()).isEqualTo(Positive.class);

    // The Record's fields
    final Field minField = Range.class.getDeclaredField("min");
    assertThat(minField.getDeclaredAnnotations()[0].annotationType()).isEqualTo(Positive.class);
  }
}