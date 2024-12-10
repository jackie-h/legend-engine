//  Copyright 2022 Goldman Sachs
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

package org.finos.legend.pure.code.core;

import junit.framework.TestSuite;
import org.finos.legend.pure.m3.execution.test.PureTestBuilder;
import org.finos.legend.pure.m3.execution.test.TestCollection;
import org.finos.legend.pure.runtime.java.compiled.execution.CompiledExecutionSupport;
import org.finos.legend.pure.runtime.java.compiled.testHelper.PureTestBuilderCompiled;

public class Test_Pure_Relational_DbSpecific_Oracle
{
    public static TestSuite suite()
    {
        // All tests related to Oracle that are not defined as parameterized DB specific tests should be here
        String testPackage = "meta::relational::functions::sqlQueryToString::oracle::tests";
        CompiledExecutionSupport executionSupport = PureTestBuilderCompiled.getClassLoaderExecutionSupport();
        return PureTestBuilderCompiled.buildSuite(TestCollection.collectTests(testPackage, executionSupport.getProcessorSupport(), ci -> PureTestBuilder.satisfiesConditions(ci, executionSupport.getProcessorSupport())), executionSupport);
    }
}
