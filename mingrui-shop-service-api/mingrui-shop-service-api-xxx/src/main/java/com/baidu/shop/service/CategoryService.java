package com.baidu.shop.service;

import com.baidu.shop.base.Result;
import com.baidu.shop.entity.CategoryEntity;
import com.baidu.shop.validate.group.MingruiOperation;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品分类接口")
public interface CategoryService {
    @ApiOperation(value = "通过pid查询商品分类")
    @GetMapping(value = "category/list")
    Result<List<CategoryEntity>> getCategoryByPid(Integer pid);

    @ApiOperation(value = "通过id删除分类")
    @DeleteMapping(value = "category/delete")
    Result<JsonObject> deleteCategory(Integer id);

    @ApiOperation(value = "修改")
    @PutMapping(value = "category/edit")
    Result<JsonObject> editCategory(@Validated({MingruiOperation.update.class}) @RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "新增")
    @PostMapping(value = "category/save")
    Result<JsonObject> saveCategory(@Validated({MingruiOperation.Add.class}) @RequestBody CategoryEntity categoryEntity);

    @ApiOperation(value = "通过品牌id查询商品分类")
    @GetMapping(value = "category/getByBrand")
    Result<List<CategoryEntity>> getByBrand(Integer brandId);
}
