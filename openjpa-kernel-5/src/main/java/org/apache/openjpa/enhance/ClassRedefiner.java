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
package org.apache.openjpa.enhance;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;

/**
 * Redefines the method bodies of existing classes. Supports Java 5 VMs that
 * have a javaagent installed on the command line as well as newer VMs without
 * any javaagent flag.
 *
 * @since 1.0.0
 */
public class ClassRedefiner {

    private static final Localizer _loc = 
        Localizer.forPackage(ClassRedefiner.class);

    private static Boolean _canRedefine = null;

    /**
     * For each element in <code>classes</code>, this method will redefine
     * all the element's methods such that field accesses are intercepted
     * in-line.
     */
    public static void redefineClasses(OpenJPAConfiguration conf,
        final Map<Class,byte[]> classes) {
        Log log = conf.getLog(OpenJPAConfiguration.LOG_ENHANCE);
        if (classes == null || classes.size() == 0)
            return;

        Instrumentation inst = null;
        ClassFileTransformer t = null;
        try {
            inst = InstrumentationFactory.getInstrumentation();

            Class[] array = classes.keySet().toArray(new Class[classes.size()]);
            if (JavaVersions.VERSION >= 6) {
                log.trace(_loc.get("retransform-types", classes.keySet()));

                t = new ClassFileTransformer() {
                    public byte[] transform(ClassLoader loader, String clsName,
                        Class<?> classBeingRedefined, ProtectionDomain pd,
                        byte[] classfileBuffer) {
                        return classes.get(classBeingRedefined);
                    }
                };
                
                // these are Java 6 methods, and we don't have a Java 6 build
                // module yet. The cost of reflection here is negligible
                // compared to the redefinition / enhancement costs in total,
                // so this should not be a big problem.
                Method meth = inst.getClass().getMethod("addTransformer",
                    new Class[] { ClassFileTransformer.class, boolean.class });
                meth.invoke(inst, new Object[] { t, true });
                meth = inst.getClass().getMethod("retransformClasses",
                    new Class[] { array.getClass() });
                meth.invoke(inst, new Object[] { array });
            } else {
                log.trace(_loc.get("redefine-types", classes.keySet()));
                // in a Java 5 context, we can use class redefinition instead
                ClassDefinition[] defs = new ClassDefinition[array.length];
                for (int i = 0; i < defs.length; i++)
                    defs[i] = new ClassDefinition(array[i],
                        classes.get(array[i]));
                inst.redefineClasses(defs);
            }
        } catch (NoSuchMethodException e) {
            throw new InternalException(e);
        } catch (IllegalAccessException e) {
            throw new InternalException(e);
        } catch (InvocationTargetException e) {
            throw new UserException(e.getCause());
        } catch (IOException e) {
            throw new InternalException(e);
        } catch (ClassNotFoundException e) {
            throw new InternalException(e);
        } catch (UnmodifiableClassException e) {
            throw new InternalException(e);
        } finally {
            if (inst != null && t != null)
                inst.removeTransformer(t);
        }
    }

    /**
     * @return whether or not this VM has an instrumentation installed that
     * permits redefinition of classes. This assumes that all the arguments
     * will be modifiable classes according to
     * {@link java.lang.instrument.Instrumentation#isModifiableClass}, and
     * only checks whether or not an instrumentation is available and
     * if retransformation is possible.
     */
    public static boolean canRedefineClasses() {
        if (_canRedefine == null) {
            try {
                Instrumentation inst = InstrumentationFactory
                    .getInstrumentation();
                if (inst == null) {
                    _canRedefine = Boolean.FALSE;
                } else if (JavaVersions.VERSION == 5) {
                    // if inst is non-null and we're using Java 5,
                    // isRetransformClassesSupported isn't available,
                    // so we use the more basic class redefinition
                    // instead.
                    _canRedefine = Boolean.TRUE;
                } else {
                    _canRedefine = (Boolean) Instrumentation.class.getMethod(
                        "isRetransformClassesSupported").invoke(inst);
                }
            } catch (Exception e) {
                _canRedefine = Boolean.FALSE;
            }
        }
        return _canRedefine.booleanValue();
    }
}