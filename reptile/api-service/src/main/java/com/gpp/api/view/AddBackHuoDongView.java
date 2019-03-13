package com.gpp.api.view;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AddBackHuoDongView implements Serializable {
    //@NotBlank(message = "开始时间不能为空")
    private String opentime;
    @NotBlank(message = "正文不能为空")
    private String introduce;
    //@NotBlank(message = "截止时间不能为空")
    private String deadline;
    @NotBlank(message = "活动标题不能为空")
    private String title;
    @NotBlank(message = "最大分享数不能为空")
    private String sharingmaxnum;
    @NotBlank(message = "分享金额不能为空")
    private String money;
    @NotBlank
    private String company;
    @NotBlank(message = "创建人不能为空")
    private String createpeople;
    //@NotBlank(message = "分类不能为空")
    private String fenlei;
    @NotBlank(message = "封面图不能为空")
    private String covermap;

    @ApiModelProperty(value = "关联类目必须选择")
    @NotEmpty(message = "关联类目必须选择")
    private Set<Integer> tagids;

    @ApiModelProperty(value = "关联广告必须选择")
    private Set<Integer> advIds = new HashSet<>();

    @ApiModelProperty(value = "布局类型:0上图下文字；1左图右文字")
    @Range(min = 0, max = 1, message = "布局类型:0上图下文字；1左图右文字")
    private Integer layout;

    private Integer second;

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Set<Integer> getTagids() {
        return tagids;
    }

    public void setTagids(Set<Integer> tagids) {
        this.tagids = tagids;
    }

    public Set<Integer> getAdvIds() {
        return advIds;
    }

    public void setAdvIds(Set<Integer> advIds) {
        this.advIds = advIds;
    }

    public String getSharingmaxnum() {
        return sharingmaxnum;
    }

    public void setSharingmaxnum(String sharingmaxnum) {
        this.sharingmaxnum = sharingmaxnum;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public String getCovermap() {
        return covermap;
    }

    public void setCovermap(String covermap) {
        this.covermap = covermap;
    }


    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatepeople() {
        return createpeople;
    }

    public void setCreatepeople(String createpeople) {
        this.createpeople = createpeople;
    }
}
