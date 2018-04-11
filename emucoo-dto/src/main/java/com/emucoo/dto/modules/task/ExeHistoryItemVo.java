package com.emucoo.dto.modules.task;

import lombok.Data;

/**
 * Created by tombaby on 2018/3/25.
 */
@Data
public class ExeHistoryItemVo {
    private String workId;
    private String subWorkId;
    private String name;
    private int status;
    private long date;

}
