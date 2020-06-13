package com.wonder4work.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wonder4work.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author wonder4work
 * @since 2020-05-18
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取角色
     * @param userId
     * @return
     */
    List<Role> getRoleByUserId(@Param("userId") String userId);

}
