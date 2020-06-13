package com.wonder4work.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @since 1.0.0 2020/5/18
 */
@Data
public class PermissionVO {

    /**
     * 权限主键
     */
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
    private Integer parentId;
    /**
     * 权限状态 0 不可用 1 可用
     */
    private Integer status;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;


    private boolean checked = false;


    /**
     * 类型 1 为菜单 2 为按钮 3 为其他
     */
    private Integer type;

    private List<PermissionVO> children = new ArrayList<>();
}
