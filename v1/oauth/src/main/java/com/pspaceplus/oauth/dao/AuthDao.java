package com.pspaceplus.oauth.dao;

import com.pspaceplus.common.entity.RoleEntity;
import com.pspaceplus.common.entity.UsersEntity;
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

    UsersEntity getUserByUserName(@Param("userName") String userName);

    List<RoleEntity> loadPermissionArray(@Param("array") String[] role);

}
