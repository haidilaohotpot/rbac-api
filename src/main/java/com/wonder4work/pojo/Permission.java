package com.wonder4work.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
@Data
@Accessors(chain = true)
@TableName("t_permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限主键
     */
    @TableId(value = "permission_id", type = IdType.AUTO)
    private Integer permissionId;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限地址
     */
    private String url;
    /**
     * 权限图标
     */
    private String icon;
    /**
     * 上一级目录的id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 权限状态 0 不可用 1 可用
     */
    private Integer status;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 类型 1 为菜单 2 为按钮 3 为其他
     */
    private Integer type;

}
