package com.gpp.api.view;

import lombok.Data;

import java.io.Serializable;

@Data
public class GuokrSubView implements Serializable {
    private String titleHide;
    private String resourceUrl;
    private String content;
    private GuokrImageView imageInfo;

    public String getTitleHide() {
        return titleHide;
    }

    public void setTitleHide(String titleHide) {
        this.titleHide = titleHide;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public GuokrImageView getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(GuokrImageView imageInfo) {
        this.imageInfo = imageInfo;
    }
}
