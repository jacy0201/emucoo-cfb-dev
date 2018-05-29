package com.emucoo.service;

import com.emucoo.dto.modules.task.TaskCommonDetailIn;
import com.emucoo.dto.modules.task.TaskCommonDetailOut;
import com.emucoo.restApi.RestApp;
import com.emucoo.service.task.TaskCommonService;
import com.sun.tools.javac.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestApp.class})
public class TestTaskCommonService {

    @Autowired
    private TaskCommonService taskCommonService;

    @Test
    public void testDetail() {
        TaskCommonDetailIn voi = new TaskCommonDetailIn();
        voi.setWorkID("15275833826215083");
        voi.setSubID("15275833826259170");
        voi.setWorkType(1);
        TaskCommonDetailOut out = taskCommonService.detail(voi);
        Assert.checkNonNull(out);
        System.out.println(out.getTaskStatement().getTaskTitle());
    }

}
