package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.wonder4work.mapper.PermissionMapper;
import com.wonder4work.pojo.Permission;
import com.wonder4work.pojo.bo.PermissionBO;
import com.wonder4work.service.PermissionService;
import com.wonder4work.service.RolePermissionService;
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
 * 权限表 服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void insert(PermissionBO permissionBO) {

        Permission insertPermission = new Permission();
        BeanUtils.copyProperties(permissionBO, insertPermission);
        insertPermission.setUpdateTime(new Date());
        insertPermission.setCreateTime(new Date());
        insertPermission.setStatus(1);
        insertPermission.setType(1);
        this.save(insertPermission);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(String permissionId,PermissionBO permissionBO) {

        Permission updatePermission = new Permission();
        BeanUtils.copyProperties(permissionBO, updatePermission);

        UpdateWrapper<Permission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("permission_id", permissionId);
        updatePermission.setUpdateTime(new Date());
        this.update(updatePermission,updateWrapper);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Permission queryPermissionById(Integer permissionId) {
        return this.getById(permissionId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Integer> queryPermissionIdsByRoleId(String roleId) {

        List<Integer> permissionids = this.baseMapper.queryPermissionIdsByRoleId(roleId);

        return permissionids;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getPermissionList(String queryText, Integer page, Integer pageSize) {

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",queryText);
        queryWrapper.orderByDesc("permission_id");
        PageHelper.startPage(page, pageSize);
        List<Permission> list = this.list(queryWrapper);
        return BaseService.setterPagedGrid(page, list);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Permission> queryPermissionByUser(String userId) {
        return this.baseMapper.queryPermissionByUser(userId);
    }

}
