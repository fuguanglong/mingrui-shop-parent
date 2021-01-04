package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SpecParamDTO;
import com.baidu.shop.dto.SpecificationDTO;
import com.baidu.shop.entity.SpecParamEntity;
import com.baidu.shop.entity.SpecificationEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "规格参数")
public interface SpecificationService {

    @ApiOperation(value = "查询规格参数")
    @GetMapping(value = "specgroup/getSpecGroupInfo")
    Result<List<SpecificationEntity>> listSpecGroup(SpecificationDTO specificationDTO);

    @ApiOperation(value = "新增规格参数")
    @PostMapping(value = "specgroup/save")
    Result<JSONObject> saveSpecGroup(@RequestBody SpecificationDTO specificationDTO);

    @ApiOperation(value = "修改规格参数")
    @PutMapping(value = "specgroup/save")
    Result<JSONObject> editSpecGroup(@RequestBody SpecificationDTO specificationDTO);

    @ApiOperation(value = "删除规格参数")
    @DeleteMapping(value = "specgroup/delete")
    Result<JSONObject> delSpecGroup(Integer id);

    @ApiOperation(value = "查询规格参数")
    @GetMapping(value = "specparam/getSpecParamInfo")
    Result<List<SpecParamEntity>> getSpecParam(SpecParamDTO specParamDTO);

    @ApiOperation(value = "新增规格参数")
    @PostMapping(value = "specparam/saveSpecParam")
    Result<JSONObject> saveSpecParam(@RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "修改规格参数")
    @PutMapping(value = "specparam/saveSpecParam")
    Result<JSONObject> editSpecParam(@RequestBody SpecParamDTO specParamDTO);

    @ApiOperation(value = "删除规格参数")
    @DeleteMapping(value = "specparam/delSpecParam")
    Result<JSONObject> delSpecParam(Integer id);
}
