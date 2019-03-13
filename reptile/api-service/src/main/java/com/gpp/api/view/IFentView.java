package com.gpp.api.view;

import lombok.Data;

import java.io.Serializable;

@Data
public class IFentView implements Serializable {
    private String id;
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public IFentView(String id, String tag) {
        this.id = id;
        this.tag = tag;
    }

}