package com.emucoo.job.handle;

import com.emucoo.job.JobAppTest;
import com.sun.tools.javac.util.Assert;
import com.xxl.job.core.biz.model.ReturnT;
import org.junit.Test;

public class CommonTaskBuildHandlerTest extends JobAppTest {

    @Test
    public void testExecute() {
        CommonTaskBuildHandler handler = context.getBean(CommonTaskBuildHandler.class);
        try {
            ReturnT t = handler.execute(null);
            Assert.check(((ReturnT) t).getCode() == 200);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.error("Test Fail");
        }
    }
}
