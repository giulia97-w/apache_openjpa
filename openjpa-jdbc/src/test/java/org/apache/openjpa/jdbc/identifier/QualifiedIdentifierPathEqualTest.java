/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.jdbc.identifier;

import org.apache.openjpa.jdbc.identifier.DBIdentifier.DBIdentifierType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class QualifiedIdentifierPathEqualTest {
    
    @Parameters
    public static Object[][] data() {
        DBIdentifier[] path1 = {DBIdentifier.newTable("schema"), DBIdentifier.newTable("table"),  DBIdentifier.newTable("column")};
        DBIdentifier[] path2 = {DBIdentifier.newTable("schema"), DBIdentifier.newTable("table"),DBIdentifier.newTable("column")};
        DBIdentifier[] path3 = {DBIdentifier.newTable("schema"), DBIdentifier.newTable("table"),DBIdentifier.newTable("otherColumn")};
        DBIdentifier[] path5 = {DBIdentifier.newTable("otherSchema"), DBIdentifier.newTable("table"), DBIdentifier.newTable("column")};
        
        QualifiedDBIdentifier p1 = new QualifiedDBIdentifier(path1);
        QualifiedDBIdentifier p2 = new QualifiedDBIdentifier(path2);
        QualifiedDBIdentifier p3 = new QualifiedDBIdentifier(path3);
        QualifiedDBIdentifier p5 = new QualifiedDBIdentifier(path5);
        QualifiedDBIdentifier p6 = null;
        QualifiedDBIdentifier p8 = null;
        QualifiedDBIdentifier p7 = new QualifiedDBIdentifier(new DBIdentifier[]{DBIdentifier.newTable("schema")});
        
        return new Object[][] {
            { p1, p2, true },
            { p1, p3, false },
            { p1, p5, false },
            { p6, p1, false },
            { p1, p6, false },
            { p7, p1, false },
            { p6, p8,true},
        };
    }
    
    private QualifiedDBIdentifier identifier1;
    private QualifiedDBIdentifier identifier2;
    private boolean expectedEquality;
    
    public QualifiedIdentifierPathEqualTest(QualifiedDBIdentifier identifier1, QualifiedDBIdentifier identifier2, boolean expectedEquality) {
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;
        this.expectedEquality = expectedEquality;
    }
    
    @Test
    public void testPathEqual() {
        boolean actualEquality = identifier1.pathEqual(identifier2, identifier1);
        assertEquals(expectedEquality, actualEquality);
    }
}
