package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/15.
 */
@Data
public class ContactsVo_I {
    private int taskType;
    private int contactsType;
    private List<Long> userIds;
}
