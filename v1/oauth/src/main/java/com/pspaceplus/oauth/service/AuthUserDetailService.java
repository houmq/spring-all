package com.pspaceplus.oauth.service;

import com.pspaceplus.oauth.dao.AuthDao;
import com.pspaceplus.common.entity.RoleEntity;
import com.pspaceplus.common.entity.UsersEntity;
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
        UsersEntity user = authDao.getUserByUserName(userName);
        if (null == user){
            return null;
        }

        List<RoleEntity> roles = authDao.loadPermissionArray(user.getGroup_name().split(","));
        List<String> permission = new ArrayList<>();
        roles.forEach(role -> permission.addAll(Arrays.asList(role.getPowers().split(","))));
        String[] permissionArray = new String[permission.size()];
        logger.info("permission ==================: {}",permission.toString());


        // TODO 用户信息待扩展
        return User.withUsername(user.getName())
                .password(user.getPassword())
                .roles(user.getGroup_name().split(","))
                .authorities(permission.toArray(permissionArray)).build();
    }


}
