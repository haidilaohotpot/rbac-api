package com.wonder4work.service;

/**
 * @since 1.0.0 2020/5/18
 */
public interface UserRoleService {

    /**
     * 分配角色
     * @param userId
     * @param roleIds
     */
    void doAssign(String userId, String[] roleIds);

    /**
     * 取消分配角色
     * @param userId
     * @param roleIds
     */
    void cancelAssign(String userId, String[] roleIds);
}
