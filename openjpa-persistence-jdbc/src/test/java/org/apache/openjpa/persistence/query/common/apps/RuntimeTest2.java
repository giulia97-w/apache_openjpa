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
package org.apache.openjpa.persistence.query.common.apps;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * <p>Persitent type used in testing.</p>
 *
 * @author Abe White
 */
@Entity
@DiscriminatorValue("RT2")
public class RuntimeTest2 extends RuntimeTest1 {

    private static final long serialVersionUID = 1L;
    private int intField2;

    public RuntimeTest2() {
    }

    public RuntimeTest2(int key) {
        super(key);
    }

    public RuntimeTest2(String str, int i) {
        super(str, i);
    }

    public int getIntField2() {
        return this.intField2;
    }

    public void setIntField2(int intField2) {
        this.intField2 = intField2;
    }

    @Override
    public String toString() {
        return "IntField: " + intField2 + ", StringField: " +
            super.getStringField() + " .";
    }
}
