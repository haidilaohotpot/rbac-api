package com.wonder4work.service.impl;

import com.wonder4work.mapper.RolePermissionMapper;
import com.wonder4work.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 1.0.0 2020/5/18
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void doAssign(String roleId, String[] permissionIds) {

        // 分配之前先清空

        rolePermissionMapper.removeAll(roleId);

        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("permissionIds", permissionIds);

        if (permissionIds!=null&&permissionIds.length>0){
            // 覆盖
            rolePermissionMapper.doAssign(map);
        }


    }

}
