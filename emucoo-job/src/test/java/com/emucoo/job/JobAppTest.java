package com.emucoo.job;

import com.sun.tools.javac.util.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JobAppTest {

    @Autowired
    protected Environment environment;

    @Autowired
    protected ConfigurableApplicationContext context;

    @Test
    public void testStart(){
        Assert.check(true);
    }

}
