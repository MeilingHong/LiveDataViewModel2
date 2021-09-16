package com.meiling.livedata.viewmodel.data;

import com.meiling.component.utils.gson.ToString;

/**
 * @Author marisareimu
 * @time 2021-07-07 09:33
 */
public class DataEntity extends ToString {
    private String name;
    private int age;

    public DataEntity() {
    }

    public DataEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
