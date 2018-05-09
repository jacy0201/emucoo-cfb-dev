package com.emucoo.dto.modules.calendar;

import lombok.Data;

import java.util.List;

/**
 * Created by jacy .
 * 行事历列表查询结果
 */
@Data
public class ListWorkOut {
    //月份
    private String month;
    //用户ID
    private Long userId;
    //任务集合
    private List<WorkVO> workList;
}
