package com.summer.common.exception;

import com.summer.common.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 *
 * Create By hmq
 * @date 2020/4/28 12:51
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * errorHandlerOverJson
     *
     * @author hmq
     * @date 2020/4/28 12:51
     */
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public Result errorHandlerOverJson(GlobalErrorInfoException e) {
        logger.error(e.getMessage());
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result errorHandlerOverJson(Exception e) {
        logger.error(e.getMessage());
        return Result.error(e.getMessage());
    }



}
