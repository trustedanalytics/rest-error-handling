/**
 * Copyright (c) 2015 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trustedanalytics.utils.errorhandling;

import org.junit.Assert;
import org.junit.Test;

public class ErrorIdGeneratorTest {

    @Test
    public void getNewIdReturnsUniqueIdOverTime() throws InterruptedException {
        // Generate few values
        long id1 = ErrorIdGenerator.getNewId();
        Thread.sleep(5); // Ids are time-based, so wait a bit to get different values
        long id2 = ErrorIdGenerator.getNewId();
        Thread.sleep(5);
        long id3 = ErrorIdGenerator.getNewId();

        // Make sure there were no duplicates over time
        Assert.assertFalse(id1 == id2);
        Assert.assertFalse(id1 == id3);
        Assert.assertFalse(id2 == id3);
    }
}
