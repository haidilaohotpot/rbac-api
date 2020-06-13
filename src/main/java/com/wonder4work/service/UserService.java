package com.wonder4work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wonder4work.exception.MD5Exception;
import com.wonder4work.pojo.User;
import com.wonder4work.pojo.bo.UserBO;
import com.wonder4work.util.PagedGridResult;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
public interface UserService extends IService<User> {

    /**
     * 分页获取用户用户信息
     * @param queryText
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult getUserList(String queryText, Integer page, Integer pageSize);

    /**
     * 根据用户id禁用用户
     * @param userIds
     */
    void disabled(String[] userIds);


    /**
     * 根据用户id启用用户
     * @param userIds
     */
    void open(String[] userIds);

    /**
     * 新增用户
     * @param userBO
     */
    User insert(UserBO userBO) throws MD5Exception;

    /**
     * 修改用户信息
     * @param userId
     * @param userBO
     * @return
     */
    User update(String userId,UserBO userBO) throws MD5Exception ;

    /**
     * 查询用户是否存在
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);


    /**
     * 根据用户ID查找用户信息
     * @param userId
     * @return
     */
    User queryUserByUserId(String userId);

    /**
     * 给用户分配角色
     * @param userId
     * @param roleIds
     */
    void doAssign(String userId, String[] roleIds);

    /**
     * 取消用户的相关角色
     * @param userId
     * @param roleIds
     */
    void cancelAssign(String userId, String[] roleIds);

    /**
     * 根据用户id查询他已经拥有的角色和未拥有的角色
     * @param userId
     * @return
     */
    Map<String,Object> assign(String userId);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User queryByUsernameAndPassword(String username,String password);
}
