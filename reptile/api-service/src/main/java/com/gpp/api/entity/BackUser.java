package com.gpp.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 后台用户表
 * </p>
 *
 * @author 朱超
 * @since 2018-11-27
 */
@TableName("back_user")
public class BackUser extends Model<BackUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 后台用户名(手机号)
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 创建者
     */
    private String createby;
    /**
     * 修改时间
     */
    private String updatetime;
    /**
     * 修改者
     */
    private String updateby;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 角色(0管理员,1普通管理员，2普通人）
     */
    private String role;
    /**
     * 状态(0启用，1禁用)
     */
    private String state;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BackUser{" +
                "id=" + id +
                ", phone=" + phone +
                ", password=" + password +
                ", createtime=" + createtime +
                ", createby=" + createby +
                ", updatetime=" + updatetime +
                ", updateby=" + updateby +
                ", realname=" + realname +
                ", role=" + role +
                ", state=" + state +
                "}";
    }
}
