package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@Data
@Accessors(chain = true)
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色主键
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    /**
     * 角色名
     */
    private String name;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 状态 0 禁用 1 可用
     */
    private Integer status;


}
