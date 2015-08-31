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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ErrorLoggerTest {

    @Test
    public void logErrorIsUsingGivenLogger() throws Exception {
        Logger logger = mock(Logger.class);
        Exception ex1 = new Exception("Some ex");
        ArgumentCaptor<String> capturedMessage = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Throwable> capturedEx = ArgumentCaptor.forClass(Throwable.class);

        ErrorLogger.logError(logger, "Some message", ex1);

        verify(logger, times(1)).error(capturedMessage.capture(), capturedEx.capture());
        Assert.assertEquals("Some message", capturedMessage.getValue());
        Assert.assertEquals("Some ex", capturedEx.getValue().getMessage());
    }
}