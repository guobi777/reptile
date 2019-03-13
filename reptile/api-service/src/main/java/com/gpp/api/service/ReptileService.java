package com.gpp.api.service;

import com.gpp.api.view.ReptileResponseView;
import com.gpp.api.view.ReptileView;

import java.util.Map;

public interface ReptileService {
    @Deprecated
    Map<String, ReptileResponseView> weiXin(ReptileView reptileView) throws Exception;

    //美食-初始数据抓取
    void checkMeiShis() throws Exception;

    //guokr.com 果壳 学科-初始数据抓取
    void checkGuokr() throws Exception;

    //iFeng-初始数据抓取
    void checkIFeng() throws Exception;

    //http://www.zimeika.com
    void checkZimeika() throws Exception;

    void checkZimeika4Video() throws Exception;

}
