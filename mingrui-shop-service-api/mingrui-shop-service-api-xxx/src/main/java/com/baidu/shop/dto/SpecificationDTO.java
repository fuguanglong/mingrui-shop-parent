package com.baidu.shop.dto;

import com.baidu.shop.base.BaseDTO;
import com.baidu.shop.validate.group.MingruiOperation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @ClassName TestSpringBootApplication
 * @Description: TODO
 * @Author fuguanglong
 * @Date 2021/1/4
 * @Version V1.0
 **/
@Data
@ApiModel(value = "参数DTO")
public class SpecificationDTO extends BaseDTO {

    @Id
    @ApiModelProperty(value = "参数id",example = "1")
    @NotNull(message = "ID不能为空",groups = {MingruiOperation.update.class})
    private Integer id;

    @ApiModelProperty(value = "参数cid",example = "1")
    @NotNull(message = "cid不能为空",groups = {MingruiOperation.Add.class})
    private Integer cid;

    @ApiModelProperty(value = "参数名称")
    @NotEmpty(message = "参数名称不能为空",groups = {MingruiOperation.Add.class})
    private String name;

}
