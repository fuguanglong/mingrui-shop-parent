package com.baidu.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.dto.BrandDTO;
import com.baidu.shop.entity.BrandEntity;
import com.baidu.shop.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.github.pagehelper.PageInfo;

@Api(tags = "品牌接口")
public interface BrandService {
    @GetMapping(value = "brand/list")
    @ApiOperation(value = "查询品牌列表")
    Result<PageInfo<BrandEntity>> getBrandInfo(BrandDTO brandDTO);

    @PostMapping(value = "brand/save")
    @ApiOperation(value = "新增品牌")
    Result<JSONObject> saveBrandInfo(@RequestBody BrandDTO brandDTO);

    @PutMapping(value = "brand/save")
    @ApiOperation(value = "修改品牌列表")
    Result<JSONObject> editBrandInfo(@RequestBody BrandDTO brandDTO);

    @DeleteMapping(value = "brand/delete")
    @ApiOperation(value = "删除品牌")
    Result<JSONObject> deleteBrandInfo(Integer id);
}
