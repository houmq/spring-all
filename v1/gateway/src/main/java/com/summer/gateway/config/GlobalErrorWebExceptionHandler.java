package com.summer.gateway.config;

import com.summer.common.util.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalErrorWebExceptionHandler
 *
 * Create By hmq
 * @date 2020/5/5 10:12
 */
public class GlobalErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalErrorWebExceptionHandler.class);

    public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }


    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        logger.error(error.getMessage());
        return build(error);
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this :: renderErrorResponse);
    }


    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.OK.value();
    }


    private Map<String, Object> build(Throwable error){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", ResponseEnum.EXCEPTION.getStatus());
        map.put("message", error.getMessage());
        return map;
    }

}
