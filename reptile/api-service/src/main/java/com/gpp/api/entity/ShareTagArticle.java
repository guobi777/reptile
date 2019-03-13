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
@TableName("share_tag_article")
public class ShareTagArticle extends Model<ShareTagArticle> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 文章id
     */
    @TableField("article_id")
    private Integer articleId;
    /**
     * 标签id
     */
    @TableField("tag_id")
    private Integer tagId;
    @TableField("create_time")
    private Date createTime;
    private String creator;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShareTagArticle{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", tagId=" + tagId +
                ", createTime=" + createTime +
                ", creator=" + creator +
                "}";
    }
}
