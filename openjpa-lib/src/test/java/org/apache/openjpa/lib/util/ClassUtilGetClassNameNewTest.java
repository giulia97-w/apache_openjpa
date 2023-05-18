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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClassUtilGetClassNameNewTest {

    private final Class<?> inputClass;
    private final String expectedOutput;

    public ClassUtilGetClassNameNewTest(Class<?> inputClass, String expectedOutput) {
        this.inputClass = inputClass;
        this.expectedOutput = expectedOutput;
    }

    @Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {null, null},
                {String[][].class, "String[][]"},
                {List.class, "List"},
                {Object.class, "Object"},
                {Map.Entry.class, "Map$Entry"}
        });
    }

    @Test
    public void testGetClassNameFromClass() {
        assertEquals(expectedOutput, ClassUtil.getClassName(inputClass));
    }

    
}


