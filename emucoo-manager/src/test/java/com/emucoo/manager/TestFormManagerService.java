package com.emucoo.manager;

import com.emucoo.model.TFormMain;
import com.emucoo.model.TOpportunity;
import com.emucoo.service.manage.ChancePointService;
import com.emucoo.service.manage.FormManageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.System;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFormManagerService {

    @Autowired
    private FormManageService formManageService;

    @Autowired
    private ChancePointService chancePointService;

    @Test
    public void testListForms() {
        List<TFormMain> forms = formManageService.findFormsByNameKeyword(null, 1, 50);
        assertThat(forms).isNotEmpty();
        forms.forEach(form -> System.out.println(form.getCreateTime()));
    }

    @Test
    public void testListChancePoint() {
        List<TOpportunity> oppts = chancePointService.listChancePointsByNameKeyword(null, 1, 50);
        assertThat(oppts).isNotEmpty();
        oppts.forEach(oppt -> System.out.println(oppt.getCreateTime()));
    }

    @Test
    public void testEncode() {
        String KEY = "DES/ECB/PKCS5Padding";
       /* userToken = DESUtil.encryptStr(String.valueOf(3), KEY);
        System.out.print(userToken);*/
    }
}
