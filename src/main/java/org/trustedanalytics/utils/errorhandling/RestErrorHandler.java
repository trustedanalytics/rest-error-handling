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

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class RestErrorHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletResponse response) throws Exception {
        // Generate error reference id (to be used both in logs and in error sent from the service)
        long errorId = new Date().getTime();

        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            ErrorLogger.logError("Controller error, reference id: " + errorId, e);
            response.sendError(responseStatus.value().value(), ErrorFormatter.formatErrorMessage(responseStatus.reason(), errorId));
            return;
        }

        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorLogger.logError("Unhandled controller error, reference id: " + errorId, e);
        response.sendError(errorStatus.value(), ErrorFormatter.formatErrorMessage(errorStatus, errorId));
    }
}
