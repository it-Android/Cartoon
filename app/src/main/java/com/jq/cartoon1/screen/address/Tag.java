package com.jq.cartoon1.screen.address;

/**
 * @作者(author)： JQ
 * @创建时间(date)： 2020/2/19 16:33
 **/
public class Tag {
    private String path = "";
    private String name = "";

    public Tag(String name, String path) {
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
