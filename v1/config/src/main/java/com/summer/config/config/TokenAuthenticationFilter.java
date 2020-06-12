//package com.summer.config.config;
//
//import com.summer.common.Constants;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class TokenAuthenticationFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        //解析出头中的token
//        String json = httpServletRequest.getHeader(Constants.USER_INFO_HEADER);
//        if(json != null){
//            System.out.println(json);
//        }
//        filterChain.doFilter(httpServletRequest,httpServletResponse);
//
//    }
//}
