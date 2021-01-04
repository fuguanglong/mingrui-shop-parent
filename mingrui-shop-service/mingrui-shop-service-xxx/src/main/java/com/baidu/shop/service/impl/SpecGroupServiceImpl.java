package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecificationDTO;
import com.baidu.shop.entity.SpecificationEntity;
import com.baidu.shop.mapper.SpecGroupMapper;
import com.baidu.shop.service.SpecificationService;
import com.baidu.shop.utils.BaiduBeanUtil;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/4
 * @Version V1.0
 **/
@RestController
public class SpecGroupServiceImpl extends BaseApiService implements SpecificationService {

    @Resource
    private SpecGroupMapper specGroupMapper;

    @Override
    public Result<List<SpecificationEntity>> listSpecGroup(SpecificationDTO specificationDTO) {
        Example example = new Example(SpecificationEntity.class);
        example.createCriteria().andEqualTo("cid",
                BaiduBeanUtil.copyProperties(specificationDTO,SpecificationEntity.class).getCid());
        List<SpecificationEntity> specificationEntities = specGroupMapper.selectByExample(example);
        return this.setResultSuccess(specificationEntities);
    }

    @Override
    public Result<JSONObject> saveSpecGroup(SpecificationDTO specificationDTO) {
        specGroupMapper.insertSelective(BaiduBeanUtil.copyProperties(specificationDTO,SpecificationEntity.class));
        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> editSpecGroup(SpecificationDTO specificationDTO) {
        specGroupMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(specificationDTO,SpecificationEntity.class));
        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> delSpecGroup(Integer id) {
        specGroupMapper.deleteByPrimaryKey(id);
        return this.setResultSuccess();
    }


}
