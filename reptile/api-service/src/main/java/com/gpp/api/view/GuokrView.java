package com.gpp.api.view;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GuokrView implements Serializable {
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<GuokrSubView> getResult() {
        return result;
    }

    public void setResult(List<GuokrSubView> result) {
        this.result = result;
    }

    private List<GuokrSubView> result;

}

