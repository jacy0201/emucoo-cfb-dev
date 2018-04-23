package com.emucoo.service.form.impl;

import com.emucoo.mapper.TFormMainMapper;
import com.emucoo.model.TFormMain;
import com.emucoo.service.form.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by sj on 2018/4/20.
 */
@Service
public class FormServiceImpl implements FormService{
    @Autowired
    TFormMainMapper tFormMainMapper;
    public List<TFormMain> listForm() {
        Example example = new Example(TFormMain.class);
        example.createCriteria().andEqualTo("isDel", false).andEqualTo("isUse", true);
        List<TFormMain> tFormMains = tFormMainMapper.selectByExample(example);
        return tFormMains;
    }
}
