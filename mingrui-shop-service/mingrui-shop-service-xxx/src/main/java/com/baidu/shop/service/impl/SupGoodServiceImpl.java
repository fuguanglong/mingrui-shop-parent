package com.baidu.shop.service.impl;

import com.baidu.shop.ObjectUtil.ObjectUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SupDTO;
import com.baidu.shop.entity.SupEntity;
import com.baidu.shop.mapper.SupMapper;
import com.baidu.shop.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/5
 * @Version V1.0
 **/
@RestController
public class SupGoodServiceImpl extends BaseApiService implements GoodsService {

    @Resource
    private SupMapper supMapper;

    @Override
    public Result<PageInfo<SupEntity>> getSpuInfo(SupDTO supDTO) {
        if(ObjectUtil.isNotNull(supDTO.getPage()) && ObjectUtil.isNotNull(supDTO.getRows()))
            PageHelper.startPage(supDTO.getPage(),supDTO.getRows());

        Example example = new Example(SupEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtil.isNotNull(supDTO.getSaleable()) && supDTO.getSaleable() < 2)
            criteria.andEqualTo("saleable",supDTO.getSaleable());
        if(!StringUtils.isEmpty(supDTO.getTitle()))
            criteria.andLike("title","%" + supDTO.getTitle() + "%");

        List<SupEntity> supEntities = supMapper.selectByExample(example);
        PageInfo<SupEntity> supEntityPageInfo = new PageInfo<>(supEntities);
        return this.setResultSuccess(supEntityPageInfo);
    }
}
