package com.baidu.shop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/7
 * @Version V1.0
 **/
@Data
@ApiModel(value = "库存数据传输类")
public class StockDTO {

    @ApiModelProperty(value = "sku之间",example = "1")
    private Integer skuId;

    @ApiModelProperty(value = "可秒杀库存",example = "1")
    private Integer seckillStock;

    @ApiModelProperty(value = "秒杀总数量",example = "1")
    private Integer seckillTotal;

    @ApiModelProperty(value = "库存数量",example = "1")
    private Integer stock;
}
