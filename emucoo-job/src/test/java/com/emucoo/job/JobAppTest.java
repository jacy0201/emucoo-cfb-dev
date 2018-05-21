package com.emucoo.job;

import com.emucoo.mapper.SysUserMapper;
import com.emucoo.model.SysUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobAppTest {

    @Autowired
    private MsgPushTool msgPushTool;

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void testPushMessage() {
        SysUser user = userMapper.selectByPrimaryKey(67L);
        List<String> tokens = new ArrayList<>();
        tokens.add(user.getPushToken());
        Map<String, String> extra = new HashMap<>();
        int count = msgPushTool.pushMessage("test", "test", extra, tokens);
        Assert.assertTrue(count > 0);
    }

}
