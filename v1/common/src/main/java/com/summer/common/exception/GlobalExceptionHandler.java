package com.summer.common.exception;

import com.summer.common.util.Result;
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

    /**
     * errorHandlerOverJson
     *
     * @author hmq
     * @date 2020/4/28 12:51
     */
    @ExceptionHandler(value = GlobalErrorInfoException.class)
    public Result errorHandlerOverJson(HttpServletRequest request, GlobalErrorInfoException exception) {
        exception.printStackTrace();
        return Result.error(exception.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result errorHandlerOverJson(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }



}
