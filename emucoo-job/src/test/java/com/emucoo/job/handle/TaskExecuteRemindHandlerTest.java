package com.emucoo.job.handle;

import com.alibaba.fastjson.JSONObject;
import com.emucoo.job.JobAppTest;
import com.sun.tools.javac.util.Assert;
import com.xxl.job.core.biz.model.ReturnT;
import org.junit.Test;

public class TaskExecuteRemindHandlerTest extends JobAppTest {

    @Test
    public void testExecute() {
        TaskExecuteRemindHandler handler = context.getBean(TaskExecuteRemindHandler.class);
        JSONObject jsonobj = JSONObject.parseObject("{'aheadMinutes': 60, 'cycleMinutes': 30}");
        try {
            ReturnT t = handler.execute(jsonobj.toJSONString());
            Assert.check(t.getCode() ==  200);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.error("Test Fail!");
        }
    }
}
