package com.gpp.api.view;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ReptileView implements Serializable {
    @NotNull(message = "类目编号不能为空")
    @ApiModelProperty(value = "类目编号")
    private Integer cid;
    @NotBlank(message = "公众号名必须填写")
    @ApiModelProperty(value = "公众号名，只抓取第一个匹配")
    private String name;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
