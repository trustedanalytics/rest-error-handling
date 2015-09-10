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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServletResponse;

public class ErrorLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void logError(String message, Throwable e) {
        logError(LOGGER, message, e);
    }

    public static void logError(Logger logger, String message, Throwable e) {
        logger.error(message, e);
    }

    public static void logAndSendErrorResponse(Logger logger, HttpServletResponse response, HttpStatus status, Exception ex) throws IOException {
        logAndSendErrorResponse(logger, response, status, status.getReasonPhrase(), ex);
    }

    public static void logAndSendErrorResponse(Logger logger, HttpServletResponse response, HttpStatus status, String reason, Exception ex) throws IOException {
        long errorId = ErrorIdGenerator.getNewId();
        String errorMessage = ErrorFormatter.formatErrorMessage(reason, errorId);

        logger.error(errorMessage, ex);
        response.sendError(status.value(), errorMessage);
    }
}
