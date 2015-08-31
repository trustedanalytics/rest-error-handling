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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@RunWith(MockitoJUnitRunner.class)
public class RestErrorHandlerTest {

    @Test
    public void exceptionMarkedWithResponseStatusSendsProperErrorCode() throws Exception {
        RestErrorHandler handler = new RestErrorHandler();
        Exception ex = new ExceptionWithResponseStatus();
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        handler.handleException(ex, httpResponse);

        verify(httpResponse, times(1)).sendError(eq(HttpStatus.BAD_REQUEST.value()), anyString());
    }

    @Test
    public void exceptionNotMarkedWithResponseStatusSendsError500() throws Exception {
        RestErrorHandler handler = new RestErrorHandler();
        Exception ex = new Exception("Some ex");
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        handler.handleException(ex, httpResponse);

        verify(httpResponse, times(1)).sendError(eq(500), anyString());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Very bad request")
    private static class ExceptionWithResponseStatus extends Exception {
    }
}