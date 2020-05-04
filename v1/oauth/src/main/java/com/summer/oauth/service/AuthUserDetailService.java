package com.summer.oauth.service;

import com.alibaba.fastjson.JSONObject;
import com.summer.common.entity.PermissionEntity;
import com.summer.common.entity.UserInfoEntity;
import com.summer.oauth.dao.AuthDao;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * AuthUserDetailService
 * 用户鉴权 SERVICE
 * Create By hmq
 * @date 2020/4/26 13:51
 */
@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private AuthDao authDao;

    private static Logger logger = LoggerFactory.getLogger(AuthUserDetailService.class);


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfoEntity user = authDao.getUserByLoginName(userName);
        if (null == user){
            return null;
        }

        List<String> roleNames = authDao.loadUserRoles(user.getRole().split(","));
        List<PermissionEntity> roles = authDao.loadPermissionArray(user.getRole().split(","));
        List<String> permission = new ArrayList<>();
        roles.forEach(role -> permission.addAll(Arrays.asList(role.getPermissions().split(","))));
        String[] permissionArray = new String[permission.size()];
        logger.info("permissions ==================: {}",permission.toString());

        return User.withUsername(JSONObject.toJSONString(user))
                .password(user.getPassword())
                .roles(StringUtils.join(roleNames.iterator(),","))
                .disabled(!user.isEnable())
                .authorities(permission.toArray(permissionArray)).build();
    }


}
