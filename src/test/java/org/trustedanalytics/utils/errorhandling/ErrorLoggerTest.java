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
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

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

    @Test
    public void logAndSendErrorResponseIsUsingGivenLoggerAndResponse() throws Exception {
        Logger logger = mock(Logger.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Exception ex1 = new Exception("Some ex");
        ArgumentCaptor<String> capturedMessage = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Throwable> capturedEx = ArgumentCaptor.forClass(Throwable.class);
        ArgumentCaptor<Integer> capturedStatus = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> capturedReason = ArgumentCaptor.forClass(String.class);

        ErrorLogger.logAndSendErrorResponse(logger, response, HttpStatus.BAD_REQUEST, ex1);

        verify(logger, times(1)).error(capturedMessage.capture(), capturedEx.capture());
        Assert.assertTrue(capturedMessage.getValue().contains(HttpStatus.BAD_REQUEST.getReasonPhrase()));
        Assert.assertEquals("Some ex", capturedEx.getValue().getMessage());

        verify(response, times(1)).sendError(capturedStatus.capture(), capturedReason.capture());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), capturedStatus.getValue().intValue());
        Assert.assertTrue(capturedReason.getValue().contains(HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }
}