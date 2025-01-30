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
package com.example.java17.textblocks;

public class TextBlocksExamplesTest {


    public static void createLineFeedDelimitedString_withTrailingNewLine() {
        // Pre JDK 15 example of how a multi-line string might have been written, in this case to create a readable SQL
        // query string -
        // - each component of the SQL string is implemented as a separate string, which need to be concatenated
        // - line feeds are used to delimit each line of the string
        final String expectedString =
                "SELECT \"first_name\", \"last_name\"\n" +
                        "FROM \"user\"\n" +
                        "WHERE \"city\" = 'London'\n" +
                        "ORDER BY \"last_name\";\n";
        System.out.println(expectedString);

        // From JDK 15 onwards, a Text Block can be used to declare an equivalent multi-line string, as follows -

        // A text block's opening delimiter is sequence of three double quotes followed by zero or more white spaces, and a
        // _mandatory_ new line. (It's a compilation error if the new line isn't present).
        final String sqlString = """
                SELECT "first_name", "last_name"
                FROM "user"
                WHERE "city" = 'London'
                ORDER BY "last_name";
                """;
        System.out.println(sqlString);
        // A text block's closing delimiter is also three double quotes. Its vertical and horizontal positioning relative
        // to the content influences both whether a trailing new line is included, and whether any leading whitespace
        // (indentation) is consider incidental removed from the resulting string, or not. In the above example, a new
        // line is included, with no leading whitespace.

    }

    /**
     * Provides an example of how to use a Text Block to declare a string (over multiple lines in source code) that
     * doesn't include any new line (aka line-feed) characters, i.e. suppress the default behaviour of inserting a new
     * line for each line in the block.
     * <p>
     * Text Block support for suppressing (or escaping) new lines allows them to be used to declare single-line strings
     * across multiple lines in source code. This can be useful to soft wrap a string to improve readability e.g. when
     * the string is very long, or as in this example to delineate it's logical components, e.g. parts of an SQL string.
     */
    public static void suppressNewLines() {
        // Pre JDK 15 example of how a string with no line breaks might have been declared (soft-wrapped) over multiple
        // lines, e.g. to create a readable SQL query (or when the line is very long).
        final String expectedString =
                "SELECT \"first_name\", \"last_name\" " +
                        "FROM \"user\" " +
                        "WHERE \"city\" = 'London' " +
                        "ORDER BY \"last_name\";";
        System.out.println(expectedString);

        // From JDK 15 onwards, a Text Block can be used to declare an equivalent string, as follows -
        // A block can use a "\<line-terminator>" escape sequence to suppress default inclusion of new line (\n) chars.
        final String sqlString = """
                SELECT "first_name", "last_name" \
                FROM "user" \
                WHERE "city" = 'London' \
                ORDER BY "last_name";""";
        System.out.println(sqlString);
        // This example also places the text block's closing delimiter on same line as content to suppress trailing new line
    }

    /**
     * Provides an example of how to control how much of the leading whitespace (indentation) in the lines of a Text Block
     * is included in the resulting string, as opposed to being classified as just incidental (part of the source code
     * formatting) and excluded from the resulting string.
     * <p>
     * The method the compiler uses to work out how much leading whitespace in a text block should be included in the
     * resulting string is referred to as the 're-indentation algorithm', which is described in full in
     * <a href="https://openjdk.java.net/jeps/378">JEP 378: Text Blocks</a>. In summary, the compiler classifies
     * incidental whitespace as the minimum no. of whitespace chars (indentation) across all non-empty lines of the
     * block, including the line containing the closing delimiter. It then removes it by shifting every line of the
     * block to the left that no. of chars.
     * <p>
     * The easiest way to control the amount of indentation in the resulting string is to place the closing delimiter
     * on its own line and left align it relative to the other lines of the text block.
     */

    public static void indentation() {
        // In this example the min no. of leading whitespace chars across all text block lines, classified as incidental is
        // 12. (The closing delimiter is on the same line as the last line of block content so doesn't have an impact).
        final String jsonString1 = """
                {
                  "id": 1,
                  "firstName": "Neil",
                  "lastName": "Brown"
                }""";
        System.out.println(jsonString1);

        // As a result there is no leading whitespace (indentation) in each line of the resulting string


        // In this example the min no. of leading whitespace chars across all text block lines, classified as incidental is
        // 10, due to negative left alignment of the closing delimiter relative to the rest of the text block content
        final String jsonString2 = """
                  {
                    "id": 1,
                    "firstName": "Neil",
                    "lastName": "Brown"
                }\
                """;
        System.out.println(jsonString2);

        // As a result there are 2 (12 minus 10) leading whitespace chars retained in each line of the resulting string

    }

    /**
     * Provides an example of how to use a Text Block to add trailing spaces to the line(s) of the resulting string,
     * aka space padding alignment.
     * <p>
     * By default the compiler ignores all trailing spaces in the lines of a Text Block - they won't appear in the
     * resulting string. However, you can add them using the \s escape sequence.
     */
    public static void insertTrailingSpaces() {
        // When the \s escape sequence is used in a text block, in addition to inserting a single space, for convenience it
        // also preserves any trailing spaces that occur before it. So, in the following example all lines of the
        // resulting string are space padded to 8 chars in length
        final var phoneticsString = """
                alpha  \s
                beta   \s
                charlie\s
                """;

        System.out.println(phoneticsString);
    }

    public static void main(String[] args) {
        createLineFeedDelimitedString_withTrailingNewLine();
        suppressNewLines();
        indentation();
        insertTrailingSpaces();
    }
}