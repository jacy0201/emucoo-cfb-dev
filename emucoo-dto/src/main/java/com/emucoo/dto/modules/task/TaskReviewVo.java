package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/13 18:08
 */
@Data
public class TaskReviewVo {
    private Integer reviewResult = 1;
    private Long reviewID = 1L;
    private String reviewOpinion = "very Good";
    private Long reviewTime = 31536000000L;
    private Long auditorID = 1L;
    private String auditorName = "张三";
    private String auditorHeadUrl = "/api/123.jpg";
    private List<Map<String,String>> reviewImgArr = new ArrayList<Map<String,String>>(){
        {
            add(new HashMap<String,String>(){
                {
                    put("imgUrl","/api/123.jpg");
                }
            });
            add(new HashMap<String,String>(){
                {
                    put("imgUrl","/api/123.jpg");
                }
            });
            add(new HashMap<String,String>(){
                {
                    put("imgUrl","/api/123.jpg");
                }
            });
            add(new HashMap<String,String>(){
                {
                    put("imgUrl","/api/123.jpg");
                }
            });
        }
    };

}
