package com.wonder4work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wonder4work.pojo.Permission;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色id查询权限id
     * @param roleId
     * @return
     */
    List<Integer> queryPermissionIdsByRoleId(String roleId);

    /**
     * 根据用户id查询用户权限
     * @param userId
     * @return
     */
    List<Permission> queryPermissionByUser(String userId);
}
