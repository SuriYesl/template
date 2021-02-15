package cn.su.dao.entity.test;

import java.time.LocalDateTime;

/**
 * @AUTHOR: sr
 * @DATE: Create In 20:10 2021/2/14 0014
 * @DESCRIPTION: 测试
 */
public class TestModel {
    private String name;
    private Integer age;
    private LocalDateTime createTime;
    private String someMessage;
    private String someNote;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getSomeMessage() {
        return someMessage;
    }

    public void setSomeMessage(String someMessage) {
        this.someMessage = someMessage;
    }

    public String getSomeNote() {
        return someNote;
    }

    public void setSomeNote(String someNote) {
        this.someNote = someNote;
    }

    @Override
    public String toString() {
        return this.name + "****" + this.age + "****" + this.createTime + "****" + this.someMessage + "****" + this.someNote;
    }
}
