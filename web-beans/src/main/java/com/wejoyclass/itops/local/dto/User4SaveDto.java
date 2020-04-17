package com.wejoyclass.itops.local.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class User4SaveDto {

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "生日")
    private String birthday;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户姓名")
    private String fullName;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "用户所属组织ID列表")
    private List<Integer> orgIdList;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "用户所属角色ID列表")
    private List<Integer> roleIdList;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "用户名")
    private String username;

}
