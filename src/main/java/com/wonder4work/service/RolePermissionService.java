package com.wonder4work.service;

/**
 * @since 1.0.0 2020/5/18
 */
public interface RolePermissionService {
    /**
     * 为角色分配权限
     * @param roleId
     * @param permissionIds
     */
    void doAssign(String roleId, String[] permissionIds);
}
