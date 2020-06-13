package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.wonder4work.mapper.RoleMapper;
import com.wonder4work.pojo.Role;
import com.wonder4work.pojo.bo.RoleBO;
import com.wonder4work.service.RolePermissionService;
import com.wonder4work.service.RoleService;
import com.wonder4work.util.PagedGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RolePermissionService rolePermissionService;



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getRoleList(String queryText, Integer page, Integer pageSize) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",queryText);
        queryWrapper.orderByDesc("role_id");
        PageHelper.startPage(page, pageSize);
        List<Role> list = this.list(queryWrapper);
        return BaseService.setterPagedGrid(page, list);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Role queryRoleByRoleId(String roleId) {
        return this.getById(roleId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryNameIsExist(String name) {

        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        Role role = this.getOne(queryWrapper);
        return null != role;
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Role insert(RoleBO roleBO) {
        Role insertRole = new Role();
        BeanUtils.copyProperties(roleBO, insertRole);
        insertRole.setStatus(1);
        insertRole.setCreateTime(new Date());
        insertRole.setUpdateTime(new Date());
        this.save(insertRole);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", insertRole.getName());
        return this.getOne(queryWrapper);
    }



    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Role update(String roleId, RoleBO roleBO) {
        UpdateWrapper<Role> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id",roleId);
        Role updateRole = new Role();
        BeanUtils.copyProperties(roleBO, updateRole);
        updateRole.setUpdateTime(new Date());
        this.update(updateRole,updateWrapper);
        return this.getById(roleId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Role> getRoleByUserId(String userId) {
        return this.baseMapper.getRoleByUserId(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void doAssign(String roleId, String[] permissionIds) {
        rolePermissionService.doAssign(roleId,permissionIds);
    }
}
