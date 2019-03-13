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
@TableName("share_article")
public class ShareArticle extends Model<ShareArticle> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标题
     */
    private String title;
    /**
     * 封面图
     */
    @TableField("cover_plan")
    private String coverPlan;
    private String creator;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    private String updator;
    /**
     * 正文
     */
    private String content;
    /**
     * 0不显示/1显示
     */
    private Integer status;
    /**
     * 布局类型:0上图下文字；1左图右文字
     */
    private Integer layout;

    public ShareArticle() {
    }

    public ShareArticle(Integer id) {
        this.id = id;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPlan() {
        return coverPlan;
    }

    public void setCoverPlan(String coverPlan) {
        this.coverPlan = coverPlan;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShareArticle{" +
                "id=" + id +
                ", title=" + title +
                ", coverPlan=" + coverPlan +
                ", creator=" + creator +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                ", content=" + content +
                ", status=" + status +
                "}";
    }
}
