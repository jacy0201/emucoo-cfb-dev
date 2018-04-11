package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.mapper.LabelMapper;
import com.emucoo.model.Label;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tk.mybatis.mapper.entity.Example;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/31 13:41
 * @modified by:
 */
@Service
public class LabelServiceImpl extends BaseServiceImpl<Label> implements com.emucoo.service.sys.LabelService {

  @Resource
  private LabelMapper labelMapper;

  @Override
  public Map<Long, List<Label>> dimensions() {
    return labelMapper.selectAll().stream()
        .collect(Collectors.groupingBy(Label::getDimenType));
  }

  @Override
  public List<Long> listIdByNames(String names){
    if(StringUtils.isBlank(names)){
      return findAll().stream().map(Label::getId)
              .collect(Collectors.toList());
    }
    List<String> deptLbNames = Arrays.asList(names.split(","));
    //通过标签名字，模糊查询标签id
    Example example = new Example(Label.class);
    Example.Criteria criteria = example.createCriteria();
    deptLbNames.forEach(t->{
      criteria.orLike("name", "%" + t + "%");
    });
    List<Label> labels = this.selectByExample(example);
    return labels.stream().map(Label::getId).collect(Collectors.toList());
  }
}
