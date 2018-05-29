package com.emucoo.service;

import com.emucoo.model.TLoopWork;
import com.emucoo.restApi.RestApp;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.DateUtil;
import com.sun.tools.javac.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestApp.class})
public class TestLoopWorkService {

    @Autowired
    private LoopWorkService loopWorkService;

    @Test
    public void testListPendingExecute() {
        List<TLoopWork> list = loopWorkService.listPendingExecute(61L, DateUtil.currentDate());
        Assert.check(list != null && list.size() > 0);
        for(TLoopWork work : list) {
            System.out.println(work.getId());
        }
    }
}
