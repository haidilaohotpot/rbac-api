package com.wonder4work.pojo.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @since 1.0.0 2020/5/18
 */
@Data
@ApiModel(value="权限对象", description="前端传入的数据封装在此对象中")
public class PermissionBO {

    /**
     * 权限名称
     */
    @ApiModelProperty(value="权限名称", name="name", example="json", required = true)
    private String name;
    /**
     * 权限地址
     */
    @ApiModelProperty(value="权限url", name="url", example="/user/1", required = false)
    private String url;
    /**
     * 权限图标
     */
    @ApiModelProperty(value="权限图标", name="icon", required = false)
    private String icon;
    /**
     * 上一级目录的id
     */
    @ApiModelProperty(value="上一级目录的id,0表示没有上一级目录", name="parentId", example="1", required = true)
    private Integer parentId;

    /**
     * 类型 1 为菜单 2 为按钮 3 为其他
     */
    @ApiModelProperty(value="类型 1 为菜单 2 为按钮 3 为其他", name="type", example="1", required = false)
    private Integer type;
}
