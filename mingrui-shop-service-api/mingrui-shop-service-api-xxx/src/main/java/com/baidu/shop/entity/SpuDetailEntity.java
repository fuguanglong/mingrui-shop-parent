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
@Table(name = "tb_spu_detail")
public class SpuDetailEntity {

    @Id
    private Integer spuId;

    private String description;

    private String genericSpec;

    private String specialSpec;

    private String packingList;

    private String afterService;
}
