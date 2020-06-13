package com.wonder4work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.pojo.Role;
import com.wonder4work.pojo.bo.RoleBO;
import com.wonder4work.util.PagedGridResult;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
public interface RoleService extends IService<Role> {

    /**
     * 分页查询所有角色信息
     * @param queryText
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getRoleList(String queryText, Integer page, Integer pageSize);

    /**
     * 根据角色ID获取角色信息
     * @param roleId
     * @return
     */
    Role queryRoleByRoleId(String roleId);

    /**
     * 根据角色名查询是否已经存在
     * @param name
     * @return
     */
    boolean queryNameIsExist(String name);

    /**
     * 新增角色
     * @param roleBO
     * @return
     */
    Role insert(RoleBO roleBO);

    /**
     * 更新角色信息
     * @param roleId
     * @param roleBO
     * @return
     */
    Role update(String roleId, RoleBO roleBO);

    /**
     * 根据用户id获取角色信息
     * @param userId
     * @return
     */
    List<Role> getRoleByUserId(String userId);

    /**
     * 为角色分配权限
     * @param roleId
     * @param permissionIds
     */
    void doAssign(String roleId, String[] permissionIds);

}
