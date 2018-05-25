package com.emucoo.job.handle;

import com.emucoo.job.JobAppTest;
import com.sun.tools.javac.util.Assert;
import com.xxl.job.core.biz.model.ReturnT;
import org.junit.Test;

public class TaskExpiredHandlerTest extends JobAppTest {

    @Test
    public void testExecute()  {
        TaskExpiredHandler handler = context.getBean(TaskExpiredHandler.class);
        try {
            ReturnT t = handler.execute(null);
            Assert.check(t.getCode() == 200);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.error("Test Fail");
        }
    }
}
