package com.baidu.shop.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/4
 * @Version V1.0
 **/
@Data
@Table(name = "tb_spec_group")
public class SpecificationEntity {
    @Id
    private Integer id;

    private Integer cid;

    private String name;
}
