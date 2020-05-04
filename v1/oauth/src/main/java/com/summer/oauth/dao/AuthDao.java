package com.summer.oauth.dao;

import com.summer.common.entity.PermissionEntity;
import com.summer.common.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * AuthDao
 * 用户鉴权DAO
 * Create By hmq
 * @date 2020/4/27 14:32
 */
@Repository
public interface AuthDao {

    UserInfoEntity getUserByLoginName(@Param("loginName") String loginName);

    List<PermissionEntity> loadPermissionArray(@Param("array") String[] role);

    List<String> loadUserRoles(@Param("array") String[] array);



}
