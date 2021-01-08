package com.baidu.shop.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/6
 * @Version V1.0
 **/
@Data
@Table(name = "tb_stock")
public class StockEntity {

    @Id
    private Long skuId;

    private Integer seckillStock;

    private Integer seckillTotal;

    private Integer stock;
}
