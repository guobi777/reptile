package com.gpp.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 朱超
 * @since 2018-12-12
 */
@TableName("share_advertising")
public class ShareAdvertising extends Model<ShareAdvertising> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 0图片/1视频
     */
    private Integer type;
    /**
     * 内部链接地址
     */
    private String link;
    @TableField("create_time")
    private Date createTime;
    private String creator;
    @TableField("update_time")
    private Date updateTime;
    private String updator;
    /**
     * 分享币
     */
    private Integer coin;
    /**
     * 外部链接地址
     */
    @TableField("target_link")
    private String targetLink;
    private String title;
    /**
     * 广告布局类型【0详情下/1详情上部/2浮动】
     */
    @TableField("layout_type")
    private Integer layoutType;
    /**
     * 是否显示/0:显示,1:不显示
     */
    @TableField("is_title_show")
    private Integer isTitleShow;

    public ShareAdvertising() {
    }

    public ShareAdvertising(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getTargetLink() {
        return targetLink;
    }

    public void setTargetLink(String targetLink) {
        this.targetLink = targetLink;
    }

    public Integer getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(Integer layoutType) {
        this.layoutType = layoutType;
    }

    public Integer getIsTitleShow() {
        return isTitleShow;
    }

    public void setIsTitleShow(Integer isTitleShow) {
        this.isTitleShow = isTitleShow;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShareAdvertising{" +
                "id=" + id +
                ", type=" + type +
                ", link=" + link +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                ", coin=" + coin +
                ", targetLink=" + targetLink +
                ", layoutType=" + layoutType +
                ", isTitleShow=" + isTitleShow +
                "}";
    }
}
