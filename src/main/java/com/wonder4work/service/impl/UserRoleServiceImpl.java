package com.wonder4work.service.impl;

import com.wonder4work.mapper.UserRoleMapper;
import com.wonder4work.service.UserRoleService;
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
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void doAssign(String userId, String[] roleIds) {
        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("roleIds", roleIds);
        userRoleMapper.doAssign(map);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void cancelAssign(String userId, String[] roleIds) {
        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("roleIds", roleIds);
        userRoleMapper.cancelAssign(map);
    }

}
