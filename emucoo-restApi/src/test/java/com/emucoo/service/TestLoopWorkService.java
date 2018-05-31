package com.emucoo.service;

import com.emucoo.dto.modules.task.WorkVo_O;
import com.emucoo.restApi.RestApp;
import com.emucoo.service.task.LoopWorkService;
import com.emucoo.utils.DateUtil;
import com.sun.tools.javac.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RestApp.class})
public class TestLoopWorkService {

    @Autowired
    private LoopWorkService loopWorkService;

    @Test
    public void testListPendingExecute() {
        Date dt = DateUtil.strToSimpleYYMMDDDate(DateUtil.simple(DateUtil.currentDate()));
        WorkVo_O workVo = loopWorkService.viewPendingExecuteWorks(dt,61L);
        Assert.check(workVo != null);
        System.out.println(workVo.getWorkArr().size());
    }
}
