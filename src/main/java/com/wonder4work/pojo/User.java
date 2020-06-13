package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@Data
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    @TableId("user_id")
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别 0 女 1 男 2 保密
     */
    private Integer sex;
    /**
     * 头像地址
     */
    private String face;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态 0 禁用 1 可用
     */
    private Integer status;
    @TableField("update_time")
    private Date updateTime;
    @TableField("create_time")
    private Date createTime;


}
