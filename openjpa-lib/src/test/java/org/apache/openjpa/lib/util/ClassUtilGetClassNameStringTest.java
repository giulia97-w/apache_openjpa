/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.openjpa.lib.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class ClassUtilGetClassNameStringTest {

    private final String input;
    private final String expectedOutput;

    public ClassUtilGetClassNameStringTest(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null, null},
                {"", ""},
                {"int", "int"},
                {"java.lang.String", "String"},
                {"[Lorg.example.MyClass;", "MyClass[]"},
                {"[Lorg.example.MyClass[][]", "MyClass[][][]"},
                {"[Ljava.lang.Object;", "Object[]"},
                {"[B", "byte[]"},
                {"[C", "char[]"},
                {"[D", "double[]"},
                {"[F", "float[]"},
                {"[I", "int[]"},
                {"[J", "long[]"},
                {"[S", "short[]"},
                { "java.util.Map.Entry", "Entry" },
                { "[[[I", "int[][][]" },
                { "[[[Ljava.util.Map.Entry;", "Entry[][][]" },
        });
    }

    @Test
    public void testGetClassName() {
        assertEquals(expectedOutput, ClassUtil.getClassName(input));
        String test = null;
        assertNull(ClassUtil.getClassName(test));
    }
    

    
}
