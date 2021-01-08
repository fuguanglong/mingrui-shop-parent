package com.baidu.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.shop.ObjectUtil.ObjectUtil;
import com.baidu.shop.base.BaseApiService;
import com.baidu.shop.base.Result;
import com.baidu.shop.dto.SkuDTO;
import com.baidu.shop.dto.SpuDetailDTO;
import com.baidu.shop.dto.SpuDTO;
import com.baidu.shop.entity.*;
import com.baidu.shop.mapper.*;
import com.baidu.shop.service.GoodsService;
import com.baidu.shop.status.HTTPStatus;
import com.baidu.shop.utils.BaiduBeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/5
 * @Version V1.0
 **/
@RestController
@Slf4j
public class SupGoodServiceImpl extends BaseApiService implements GoodsService {

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private BrandMapper brandMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private StockMapper stockMapper;

    @Resource
    private SpuDetailMapper spuDetailMapper;

    @Override
    public Result<PageInfo<SpuEntity>> getSpuInfo(SpuDTO spuDTO) {
        if(ObjectUtil.isNotNull(spuDTO.getPage()) && ObjectUtil.isNotNull(spuDTO.getRows()))
            PageHelper.startPage(spuDTO.getPage(),spuDTO.getRows());

        if(!ObjectUtils.isEmpty(spuDTO.getSort()) && !ObjectUtils.isEmpty(spuDTO.getOrder()))
            PageHelper.orderBy(spuDTO.getOrder());

        Example example = new Example(SpuEntity.class);
        Example.Criteria criteria = example.createCriteria();

        if(ObjectUtil.isNotNull(spuDTO.getSaleable()) && spuDTO.getSaleable() < 2)
            criteria.andEqualTo("saleable",spuDTO.getSaleable());
        if(!StringUtils.isEmpty(spuDTO.getTitle()))
            criteria.andLike("title","%" + spuDTO.getTitle() + "%");

        List<SpuEntity> supEntities = spuMapper.selectByExample(example);

        List<SpuDTO> supDTOList = supEntities.stream().map(supEntity -> {
            SpuDTO supDTO1 = BaiduBeanUtil.copyProperties(supEntity, SpuDTO.class);
            List<CategoryEntity> categoryEntities = categoryMapper.selectByIdList(Arrays.asList(supEntity.getCid1(), supEntity.getCid2(), supEntity.getCid3()));
            String categoryName = categoryEntities.stream().map(categoryEntity -> categoryEntity.getName()).collect(Collectors.joining("/"));
            supDTO1.setCategoryName(categoryName);

            BrandEntity brandEntity = brandMapper.selectByPrimaryKey(supEntity.getBrandId());
            supDTO1.setBrandName(brandEntity.getName());
            return supDTO1;
        }).collect(Collectors.toList());

        PageInfo<SpuEntity> supEntityPageInfo = new PageInfo<>(supEntities);
        return this.setResult(HTTPStatus.OK,supEntityPageInfo.getTotal() + "",supDTOList);
    }

    @Override
    @Transactional
    public Result<JSONObject> saveGoods(SpuDTO spuDTO) {

        final Date date = new Date();
        //新增spu
        SpuEntity supEntity = BaiduBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        supEntity.setSaleable(1);
        supEntity.setValid(1);
        supEntity.setCreateTime(date);
        supEntity.setLastUpdateTime(date);
        spuMapper.insertSelective(supEntity);

        //新增spuDetail
        SpuDetailDTO spuDetail = spuDTO.getSpuDetail();
        SpuDetailEntity spuDetailEntity = BaiduBeanUtil.copyProperties(spuDetail, SpuDetailEntity.class);
        spuDetailEntity.setSpuId(supEntity.getId());
        spuDetailMapper.insertSelective(spuDetailEntity);

        this.saveSkusAndStockInfo(spuDTO,supEntity.getId(),date);
        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<SpuDetailEntity> getSpuDetailBySpuId(Integer spuId) {
        SpuDetailEntity spuDetailEntity = spuDetailMapper.selectByPrimaryKey(spuId);
        return this.setResultSuccess(spuDetailEntity);
    }

    @Transactional
    @Override
    public Result<List<SkuDTO>> getSkuBySpuId(Integer spuId) {
        List<SkuDTO> list = skuMapper.selectSkuAndStockBySpuId(spuId);
        return this.setResultSuccess(list);
    }

    @Transactional
    @Override
    public Result<JSONObject> editGoods(SpuDTO spuDTO) {
        final Date date = new Date();
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        spuEntity.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuEntity);

        spuDetailMapper.updateByPrimaryKeySelective(BaiduBeanUtil.copyProperties(spuDTO.getSpuDetail(),SpuDetailEntity.class));

        this.deleteSkusAndStock(spuEntity.getId());

        this.saveSkusAndStockInfo(spuDTO,spuEntity.getId(),date);

        return this.setResultSuccess();
    }

    @Transactional
    @Override
    public Result<JSONObject> deleteGoods(Integer spuId) {
        spuMapper.deleteByPrimaryKey(spuId);
        spuDetailMapper.deleteByPrimaryKey(spuId);
        this.deleteSkusAndStock(spuId);
        return this.setResultSuccess();
    }

    @Override
    public Result<JSONObject> getSaleableItem(SpuDTO spuDTO) {
        SpuEntity spuEntity = BaiduBeanUtil.copyProperties(spuDTO, SpuEntity.class);
        if(ObjectUtil.isNotNull(spuEntity.getSaleable()) && spuDTO.getSaleable() != 2){
            if(spuEntity.getSaleable() == 1){
                spuEntity.setSaleable(0);
                spuMapper.updateByPrimaryKey(spuEntity);
                return this.setResultSuccess("已下架");
            }else{
                spuEntity.setSaleable(1);
                spuMapper.updateByPrimaryKey(spuEntity);
                return this.setResultSuccess("已上架");
            }
        }
        return this.setResultError("错误");
    }

    private void deleteSkusAndStock(Integer spuId){
        Example example = new Example(SkuEntity.class);
        example.createCriteria().andEqualTo("spuId",spuId);
        List<SkuEntity> skuEntities = skuMapper.selectByExample(example);

        List<Long> collect = skuEntities.stream().map(skuEntity -> skuEntity.getId()).collect(Collectors.toList());
        skuMapper.deleteByIdList(collect);
        stockMapper.deleteByIdList(collect);
    }

    private void saveSkusAndStockInfo(SpuDTO spuDTO,Integer spuId,Date date){
        //新增sku
        List<SkuDTO> skus = spuDTO.getSkus();
        skus.stream().forEach(skuDTO -> {
            SkuEntity skuEntity = BaiduBeanUtil.copyProperties(skuDTO, SkuEntity.class);
            skuEntity.setSpuId(spuId);
            skuEntity.setCreateTime(date);
            skuEntity.setLastUpdateTime(date);
            skuMapper.insertSelective(skuEntity);

            //新增stock
            StockEntity stockEntity = new StockEntity();
            stockEntity.setSkuId(skuEntity.getId());
            stockEntity.setStock(skuDTO.getStock());
            stockMapper.insertSelective(stockEntity);
        });
    }
}
