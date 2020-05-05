package com.summer.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.summer.common.Constants;
import com.summer.common.annotation.CurrentUser;
import com.summer.common.entity.UserInfoEntity;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(CurrentUser.class) && parameter.getParameterType().isAssignableFrom(UserInfoEntity.class)){
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        String header64 = request.getHeader(Constants.USER_INFO_HEADER);
        String info = new String(Base64.decodeBase64(header64), IOUtils.UTF8);
        return JSONObject.parseObject(info, UserInfoEntity.class);
    }
}
