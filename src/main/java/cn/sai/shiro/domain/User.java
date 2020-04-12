package cn.sai.shiro.domain;

import java.util.Date;

public class User {
    private Integer id;
    private String username;
    private String password;
    private Date createtime;

    public User(Integer id, String username, String password, Date createtime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createtime = createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
