package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SupDTO;
import com.baidu.shop.entity.SupEntity;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;


@Api(tags = "商品接口")
public interface GoodsService {

    @ApiOperation(value = "获取sup信息")
    @GetMapping(value = "/goods/getSpuInfo")
    Result<PageInfo<SupEntity>> getSpuInfo(SupDTO supDTO);
}