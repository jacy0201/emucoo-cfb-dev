package com.emucoo.service.sys.imp;

import com.emucoo.common.base.service.impl.BaseServiceImpl;
import com.emucoo.dto.modules.dimension.DimensionVo_O;
import com.emucoo.mapper.DimensionMapper;
import com.emucoo.model.Dimension;
import com.emucoo.service.sys.DimensionService;

import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.emucoo.dto.base.ISystem.SYS_DIMENSION_ROOT;

/**
 * @author: river
 * @description:
 * @date: created at 2018/1/31 15:38
 * @modified by:
 */
@Service
public class DimensionServiceImpl extends BaseServiceImpl<Dimension> implements DimensionService {
    @Resource
    private DimensionMapper dimensionMapper;

    @Override
    public List<Dimension> queryDimensions() {
        Example example = new Example(Dimension.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", SYS_DIMENSION_ROOT);
        return this.selectByExample(example);
    }

    @Override
    public List<DimensionVo_O> listDimensions(Long dimensionId, Long typeId, String name) {
        return dimensionMapper.listDimensions(dimensionId, typeId, name);
    }

    @Override
    public List<Dimension> queryTypes(Long dimensionId) {
        Example example = new Example(Dimension.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", dimensionId);
        return this.selectByExample(example);
    }

    @Override
    public Map<Long, List<Dimension>> dimensions() {
        return dimensionMapper.selectAll().stream().collect(Collectors.groupingBy(Dimension::getType));
    }
}
