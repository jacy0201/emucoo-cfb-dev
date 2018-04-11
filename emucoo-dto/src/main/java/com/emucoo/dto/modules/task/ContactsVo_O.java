package com.emucoo.dto.modules.task;

import lombok.Data;

import java.util.List;

/**
 * Created by tombaby on 2018/3/15.
 */
@Data
public class ContactsVo_O {
    private List<ContactVo> topContactsArr;
    private List<ContactVo> allContactsArr;
}
