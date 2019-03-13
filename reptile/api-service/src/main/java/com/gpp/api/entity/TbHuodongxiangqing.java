package com.gpp.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 活动(文章)表
 * </p>
 *
 * @author 朱超
 * @since 2018-11-27
 */
@TableName("tb_huodongxiangqing")
public class TbHuodongxiangqing extends Model<TbHuodongxiangqing> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 活动标题
     */
    private String title;
    /**
     * 分类（热门点击、我的城市、店庆活动）
     */
    private String fenlei;
    /**
     * app页面上的是否修改
     */
    private String updatetime;
    /**
     * 发布时间
     */
    private String createtime;
    /**
     * 阅读次数
     */
    private String readnum;
    /**
     * 封面图
     */
    private String covermap;
    /**
     * 详情图
     */
    private String detaildiagram;
    /**
     * 正文
     */
    private String introduce;
    /**
     * 剩余件数
     */
    private String remainingpieces;
    /**
     * 剩余百分比
     */
    private String remainbaifenbi;
    /**
     * 开始时间
     */
    private String opentime;
    /**
     * 是否提醒（0不提醒1提醒）
     */
    private String shifoutixing;
    /**
     * 状态（进行中，开始，结束）
     */
    private String state;
    /**
     * 创建人
     */
    private String createpeople;
    /**
     * 修改人
     */
    private String updatepeople;
    /**
     * 城市
     */
    private String zhuanfaurl;
    /**
     * 发布时间
     */
    private String releasetime;
    /**
     * 截止时间
     */
    private String deadline;
    /**
     * 纷享链接
     */
    private String type;
    /**
     * 纷享受益
     */
    private String money;
    /**
     * 有效阅读次数
     */
    @TableField("huodong_type")
    private String huodongType;

    @TableField("video_during")
    private Integer videoDuring;
    /**
     * 最多分享次数
     */
    private String sharingmaxnum;
    /**
     * 公司
     */
    private String company;
    /**
     * 文章链接
     */
    @TableField("article_url")
    private String articleUrl;
    /**
     * 是否提醒（推送）
     */
    private String remind;
    /**
     * 上传类型
     */
    @TableField("upload_type")
    private String uploadType;
    private Integer layout;
    @TableId(value = "hd_id", type = IdType.AUTO)
    private Integer hdId;

    public Integer getVideoDuring() {
        return videoDuring;
    }

    public void setVideoDuring(Integer videoDuring) {
        this.videoDuring = videoDuring;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    public Integer getHdId() {
        return hdId;
    }

    public void setHdId(Integer hdId) {
        this.hdId = hdId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getReadnum() {
        return readnum;
    }

    public void setReadnum(String readnum) {
        this.readnum = readnum;
    }

    public String getCovermap() {
        return covermap;
    }

    public void setCovermap(String covermap) {
        this.covermap = covermap;
    }

    public String getDetaildiagram() {
        return detaildiagram;
    }

    public void setDetaildiagram(String detaildiagram) {
        this.detaildiagram = detaildiagram;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getRemainingpieces() {
        return remainingpieces;
    }

    public void setRemainingpieces(String remainingpieces) {
        this.remainingpieces = remainingpieces;
    }

    public String getRemainbaifenbi() {
        return remainbaifenbi;
    }

    public void setRemainbaifenbi(String remainbaifenbi) {
        this.remainbaifenbi = remainbaifenbi;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getShifoutixing() {
        return shifoutixing;
    }

    public void setShifoutixing(String shifoutixing) {
        this.shifoutixing = shifoutixing;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatepeople() {
        return createpeople;
    }

    public void setCreatepeople(String createpeople) {
        this.createpeople = createpeople;
    }

    public String getUpdatepeople() {
        return updatepeople;
    }

    public void setUpdatepeople(String updatepeople) {
        this.updatepeople = updatepeople;
    }

    public String getZhuanfaurl() {
        return zhuanfaurl;
    }

    public void setZhuanfaurl(String zhuanfaurl) {
        this.zhuanfaurl = zhuanfaurl;
    }

    public String getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(String releasetime) {
        this.releasetime = releasetime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getHuodongType() {
        return huodongType;
    }

    public void setHuodongType(String huodongType) {
        this.huodongType = huodongType;
    }

    public String getSharingmaxnum() {
        return sharingmaxnum;
    }

    public void setSharingmaxnum(String sharingmaxnum) {
        this.sharingmaxnum = sharingmaxnum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TbHuodongxiangqing{" +
                "id=" + id +
                ", title=" + title +
                ", fenlei=" + fenlei +
                ", updatetime=" + updatetime +
                ", createtime=" + createtime +
                ", readnum=" + readnum +
                ", covermap=" + covermap +
                ", detaildiagram=" + detaildiagram +
                ", introduce=" + introduce +
                ", remainingpieces=" + remainingpieces +
                ", remainbaifenbi=" + remainbaifenbi +
                ", opentime=" + opentime +
                ", shifoutixing=" + shifoutixing +
                ", state=" + state +
                ", createpeople=" + createpeople +
                ", updatepeople=" + updatepeople +
                ", zhuanfaurl=" + zhuanfaurl +
                ", releasetime=" + releasetime +
                ", deadline=" + deadline +
                ", type=" + type +
                ", money=" + money +
                ", huodongType=" + huodongType +
                ", sharingmaxnum=" + sharingmaxnum +
                ", company=" + company +
                ", articleUrl=" + articleUrl +
                ", remind=" + remind +
                ", uploadType=" + uploadType +
                "}";
    }
}
