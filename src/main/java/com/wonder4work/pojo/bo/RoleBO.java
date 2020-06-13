package com.wonder4work.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @since 1.0.0 2020/5/18
 */
@Data
@ApiModel(value="角色对象", description="前端传入的数据封装在此对象中")
public class RoleBO {

    /**
     * 角色名
     */
    @ApiModelProperty(value="角色名", name="name", example="json", required = true)
    private String name;

}
