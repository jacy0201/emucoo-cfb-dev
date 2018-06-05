package com.emucoo.manager;

import com.emucoo.service.task.TaskCommonService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTaskManagerService {

    @Autowired
    private TaskCommonService taskCommonService;

    @Test
    public void testSwitchCommonTask() {
        List<Long> ids = new ArrayList<>();
        ids.add(215L);

        taskCommonService.switchCommonTask(ids, true);
        Assert.assertTrue(true);
    }
}
