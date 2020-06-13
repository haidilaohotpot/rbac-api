package com.wonder4work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.wonder4work.exception.MD5Exception;
import com.wonder4work.mapper.UserMapper;
import com.wonder4work.pojo.Role;
import com.wonder4work.pojo.User;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.service.RoleService;
import com.wonder4work.service.UserRoleService;
import com.wonder4work.service.UserService;
import com.wonder4work.util.MD5Utils;
import com.wonder4work.util.PagedGridResult;
import io.swagger.annotations.ApiParam;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private Sid sid;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult getUserList(String queryText, Integer page, Integer pageSize) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",queryText);
        queryWrapper.orderByDesc("user_id");
        PageHelper.startPage(page, pageSize);
        List<User> list = this.list(queryWrapper);
        if (list != null){
            list.forEach(this::setProperties);
        }
        return BaseService.setterPagedGrid(page, list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void disabled(String[] userIds) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("user_id", userIds);
        User updateUser = new User();
        updateUser.setStatus(0);
        this.update(updateUser,updateWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void open(String[] userIds) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("user_id", userIds);
        User updateUser = new User();
        updateUser.setStatus(1);
        this.update(updateUser,updateWrapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User insert(UserBO userBO) throws MD5Exception {

        User insertUser = new User();
        BeanUtils.copyProperties(userBO, insertUser);
        String userId = sid.nextShort();
        insertUser.setUserId(userId);
        insertUser.setStatus(1);
        insertUser.setCreateTime(new Date());
        insertUser.setUpdateTime(new Date());
        try {
            insertUser.setPassword(MD5Utils.getMD5Str(insertUser.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MD5Exception(e.getMessage(), e);
        }
        this.save(insertUser);
        User user =  this.getById(userId);
        return setProperties(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User update(String userId,UserBO userBO) throws MD5Exception {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId);
        User updateUser = new User();
        BeanUtils.copyProperties(userBO, updateUser);
        updateUser.setUpdateTime(new Date());
        try {
            updateUser.setPassword(MD5Utils.getMD5Str(updateUser.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MD5Exception(e.getMessage(), e);
        }
        this.update(updateUser,updateWrapper);
        User user =  this.getById(userId);
        return setProperties(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        return null != user;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserByUserId(String userId) {
        User user = this.getById(userId);

        return setProperties(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void doAssign(String userId, String[] roleIds) {
        userRoleService.doAssign(userId,roleIds);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void cancelAssign(String userId, String[] roleIds) {
        userRoleService.cancelAssign(userId, roleIds);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> assign(String userId) {

        List<Role> allRoles = roleService.list();
        List<Role> assignRoles = roleService.getRoleByUserId(userId);
        List<Role> unAssignRoles = new ArrayList<>();

        List<Integer> assignRoleIds = new ArrayList<>();

        if (assignRoles != null){
            assignRoles.forEach(role -> {
                assignRoleIds.add(role.getRoleId());
            });
        }

        if (allRoles != null){
            allRoles.forEach(role -> {

                if (!assignRoleIds.contains(role.getRoleId())){
                    unAssignRoles.add(role);
                }

            });
        }

        Map<String, Object> map = new HashMap<>();
        map.put("assignRoles",assignRoles);
        map.put("unAssignRoles", unAssignRoles);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryByUsernameAndPassword(String username, String password) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        queryWrapper.allEq(map);
        return this.getOne(queryWrapper);
    }


    private User setProperties(User user){
        if (null != user){
            user.setPassword("");
        }
        return user;
    }


}
