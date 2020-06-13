package com.wonder4work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Permission;
import com.wonder4work.pojo.bo.PermissionBO;
import com.wonder4work.util.PagedGridResult;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 新增权限信息
     * @param permissionBO
     * @return
     */
    void insert(PermissionBO permissionBO);

    /***
     * 更新权限信息
     * @param permissionBO
     * @return
     */
    void update(String id,PermissionBO permissionBO);

    /**
     * 根据id查询权限信息
     * @param permissionId
     * @return
     */
    Permission queryPermissionById(Integer permissionId);


    /**
     * 根据角色id获取他的权限id
     * @param roleId
     * @return
     */
    List<Integer> queryPermissionIdsByRoleId(String roleId);

    /**
     * 分页获取权限信息
     * @param queryText
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getPermissionList(String queryText, Integer page, Integer pageSize);

    /**
     * 根据用户id查询用户权限
     * @param userId
     * @return
     */
    List<Permission> queryPermissionByUser(String userId);

}
