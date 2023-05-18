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

import org.apache.openjpa.jdbc.identifier.DBIdentifier;
import org.apache.openjpa.jdbc.identifier.QualifiedDBIdentifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class QualifiedIdentifierEqualTest {
    
    @Parameters
    public static Collection<Object[]> data() {
 
        DBIdentifier s1 = DBIdentifier.newTable("table");
        DBIdentifier s2 = DBIdentifier.newSchema("schema");
        DBIdentifier s3 = DBIdentifier.newTable("table");
        QualifiedDBIdentifier path1 = QualifiedDBIdentifier.newPath(s2, s1);
        QualifiedDBIdentifier path2 = QualifiedDBIdentifier.newPath(s2, s1);
        QualifiedDBIdentifier path3 = QualifiedDBIdentifier.newPath(s2, s3);
        QualifiedDBIdentifier path4 = QualifiedDBIdentifier.newPath(s1);
        String pathString = s2.toString() + "." + s3.toString();

        return Arrays.asList(new Object[][] {
            { path3, s2, false },
            { path3, pathString, true },
            { path1, path1, true },
            { path1, path2, true },
            //{ path2, path1, true },
            { path1, path3, true },
            { path2, path3, true },
            //{ path3, path1, true },
            //{ path3, path2, true },
            { path1, path4, false },
            { path1, null, false },
            { path1, s1, false },
        });
    }
    
    private QualifiedDBIdentifier identifier1;
    private Object identifier2;
    private boolean expectedEquality;
    
    public QualifiedIdentifierEqualTest(QualifiedDBIdentifier identifier1, Object identifier2, boolean expectedEquality) {
        this.identifier1 = identifier1;
        this.identifier2 = identifier2;
        this.expectedEquality = expectedEquality;
    }
    
    @Test
    public void testEquals() {
        boolean actualEquality = identifier1.equals(identifier2);
        assertEquals(expectedEquality, actualEquality);
        try {
        	Object obj = new Object();
        	identifier1.equals(obj);
        }catch(IllegalArgumentException e){
        	
        }
        QualifiedDBIdentifier id1 = new QualifiedDBIdentifier();
        QualifiedDBIdentifier id2 = null;
        boolean result1 = id1.equals(id2);
        assertFalse(result1);
        
        String str = "test";
        boolean result2 = id1.equals(str);
        assertFalse(result2);
        DBIdentifier dbId1 = DBIdentifier.newSchema("testSchema");
        DBIdentifier dbId2 = DBIdentifier.newSchema("testSchema");
        QualifiedDBIdentifier qdbId = new QualifiedDBIdentifier(dbId1);
        
        boolean result3 = qdbId.equals(dbId2);
        
        assertTrue(result3);
        
    }
    
    
    
    

    
}
