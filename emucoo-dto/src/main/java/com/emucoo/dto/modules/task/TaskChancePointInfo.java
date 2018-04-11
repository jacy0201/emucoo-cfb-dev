package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author river
 * @date 2018/3/13 18:22
 */
@Data
public class TaskChancePointInfo {
    private String chancePointName = "某某机会点";
    private String shopName = "某某店";
    private String ShopownerName = "张三";
    private Integer chancePointFrequency = 2;
    private String checklistName = "某某检查表";
    private String checklistKindName = "美化模块";
    private List checkItemArr = new ArrayList(){
        {
            add(new HashMap<String,Object>(){
                {
                    put("checkItemName","某某检查项");
                    put("dateArr",new ArrayList<Map>(){
                        {
                            add(new HashMap(){
                                {
                                    put("date","20190908");
                                    put("score","5");
                                }
                            });
                            add(new HashMap(){
                                {
                                    put("date","20190908");
                                    put("score","5");
                                }
                            });
                            add(new HashMap(){
                                {
                                    put("date","20190908");
                                    put("score","5");
                                }
                            });
                        }
                    });
                }
            });
            add(new HashMap<String,Object>(){
                {
                    put("checkItemName","某某检查项2");
                    put("dateArr",new ArrayList<Map>(){
                        {
                            add(new HashMap(){
                                {
                                    put("date","20190909");
                                    put("score","5");
                                }
                            });
                            add(new HashMap(){
                                {
                                    put("date","20190909");
                                    put("score","5");
                                }
                            });
                            add(new HashMap(){
                                {
                                    put("date","20190909");
                                    put("score","5");
                                }
                            });
                        }
                    });
                }
            });

        }
    };


}
