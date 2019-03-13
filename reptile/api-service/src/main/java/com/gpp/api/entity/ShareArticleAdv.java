package com.gpp.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 朱超
 * @since 2018-12-12
 */
@TableName("share_article_adv")
public class ShareArticleAdv extends Model<ShareArticleAdv> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("article_id")
    private Integer articleId;
    @TableField("adv_id")
    private Integer advId;


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

    public Integer getAdvId() {
        return advId;
    }

    public void setAdvId(Integer advId) {
        this.advId = advId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShareArticleAdv{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", advId=" + advId +
                "}";
    }
}
