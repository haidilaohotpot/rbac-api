package com.wonder4work.util;

import lombok.Data;

import java.util.List;

/**
 * @since 1.0.0 2020/4/11
 */
@Data
public class PagedGridResult {

    private int page;  // 当前页数
    private int total; // 总页数
    private long records; // 总记录数
    private List<?> rows; // 每行显示的内容


}
