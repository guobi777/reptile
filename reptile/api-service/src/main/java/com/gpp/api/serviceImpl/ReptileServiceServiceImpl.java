package com.gpp.api.serviceImpl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.gpp.api.service.ReptileService;
import com.gpp.api.service.ShareArticleService;
import com.gpp.api.util.*;
import com.gpp.api.view.*;
import ognl.Ognl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ReptileServiceServiceImpl implements ReptileService {

    private static final String weiXinArticle = "weiXinArticle";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private ShareArticleService tbHuodongxiangqingService;

    public static void main(String[] args) throws Exception {
        /////////////////////////////////////////////////////////////

        //////////////////////////////////////////////////////
    }


    private String getHrefByName4WeiXin(String name) {
        try {
            String url = "https://weixin.sogou.com/weixin?type=1&s_from=input&query=" + name + "&ie=utf8&_sug_=n&_sug_type_=";
            Document document = JsoupUtils.getConnection(url).get();
            Element element = document.body();
            Elements elements = element.getElementsByClass("news-list2");
            if (!elements.isEmpty()) {
                element = elements.get(0);
                elements = element.select("a");//拿第一个链接
                element = elements.get(0);
                String href = StringEscapeUtils.unescapeHtml(element.attr("href"));
                return href;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    @Transactional
    public Map<String, ReptileResponseView> weiXin(ReptileView reptileView) {
        Map<String, ReptileResponseView> map = new HashMap<>();
        try {
            String href = getHrefByName4WeiXin(reptileView.getName());
            if (StringUtils.isBlank(href)) {
                return null;
            }
            Document document = JsoupUtils.getConnection(href).get();
            Element element = document.body();
            Elements elements = element.getElementsByTag("script");
            element = elements.get(7);
            String jsContent = element.html().replace("seajs.use(\"sougou/profile.js\");", "");
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine js = manager.getEngineByName("javascript");
            js.eval(jsContent.concat("function reObj(){return msgList ;}"));
            Invocable jsInvo = (Invocable) js;
            Object object = jsInvo.invokeFunction("reObj");
            String retText = JSONUtils.toJSONString(object);
            //转换为Map对象
            Gson gson = new Gson();
            Map result = gson.fromJson(retText, new TypeToken<Map>() {
            }.getType());
            result = MapUtils.getMap(result, "list");
            for (int i = 0; i < result.size(); i++) {
                String title = Ognl.getValue("app_msg_ext_info.title", result.get(i + "")).toString();
                Date datetime = DateUtil.stampToDate(new BigDecimal(Ognl.getValue("comm_msg_info.datetime", result.get(i + "")).toString()).toPlainString());
                String cover = Ognl.getValue("app_msg_ext_info.cover", result.get(i + "")).toString();
                String content_url = StringEscapeUtils.unescapeHtml("https://mp.weixin.qq.com" + Ognl.getValue("app_msg_ext_info.content_url", result.get(i + "")));
                //处理详情
                document = JsoupUtils.getConnection(content_url).get();
                element = document.body();
                Element element0 = element.getElementById("meta_content");
                Element element1 = element.getElementById("js_content");
                StringBuilder content = new StringBuilder();
                ReptileResponseView weiXinView = new ReptileResponseView();
                weiXinView.setTitle(title);
                weiXinView.setDatetime(datetime);
                weiXinView.setCover(cover);
                weiXinView.setContent(content.append(element0).append(element1).toString());
                weiXinView.setCid(reptileView.getCid());
                map.put(title, weiXinView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //redisTemplate.opsForHash().putAll(weiXinArticle, map);
        return map;
    }

    @Override
    public void checkMeiShis() throws Exception {
        //健康类目链接
        Set<String> healths = new HashSet<>();
        healths.add("https://www.meishichina.com/News/HangYe/");
        healths.add("https://www.meishichina.com/News/ChuFang/");
        healths.add("https://www.meishichina.com/News/CanYin/");
        healths.add("https://www.meishichina.com/News/GuoJi/");
        healths.add("https://www.meishichina.com/News/XiaoFei/");
        healths.add("https://www.meishichina.com/News/ZhiLiang/");
        healths.add("https://www.meishichina.com/News/RenWu/");
        healths.add("https://www.meishichina.com/News/ZhanHui/");
        healths.add("https://www.meishichina.com/Eat/RMenu/");
        healths.add("https://www.meishichina.com/Eat/LMenu/");
        healths.add("https://www.meishichina.com/Eat/TMenu/");
        healths.add("https://www.meishichina.com/Eat/Nosh/");
        healths.add("https://www.meishichina.com/Eat/Liao/");
        healths.add("https://www.meishichina.com/Eat/WeiBo/");
        healths.add("https://www.meishichina.com/Eat/WSFood/");
        healths.add("https://www.meishichina.com/Eat/Drink/");
        healths.add("https://www.meishichina.com/Eat/Magic/");
        healths.add("https://www.meishichina.com/Eat/Culture/");
        healths.add("https://www.meishichina.com/Eat/Cookroom/");
        healths.add("https://www.meishichina.com/Health/CommonSense/");
        healths.add("https://www.meishichina.com/Health/Pulchritude/");
        healths.add("https://www.meishichina.com/Health/Baby/");
        healths.add("https://www.meishichina.com/Health/Virtue/");
        healths.add("https://www.meishichina.com/Health/Food/");
        healths.add("https://www.meishichina.com/Health/Nutrition/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/fangfushe/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/duikangwumai/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/zaoxie/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/qianliexianyan/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/tongjing/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/yuejingbutiao/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/bianmi/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/ganmaofashao/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/shimian/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/yanyan/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/gaoxueya/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/tangniaobing/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/xuetanggao/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/xuezhigao/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/fangai/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/zhichuang/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/weiyan/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/jiejiu/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/shoushen/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/fengxiong/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/yangyan/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/paidu/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/bugai/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/pinxue/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/tigaomianyili/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/qixueshuangbu/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/ziyin/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/zhuangyang/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/yangwei/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/yizhibunao/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/xiaoshujieke/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/kangshuailao/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/byun/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/brqi/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/yunqian/");
        healths.add("https://www.meishichina.com/YuanLiao/gongxiao/cuiru/");
        //需要处理的URL地址
        Set<String> urlSet = new HashSet<>();
        for (String href : healths) {
            Document document;
            try {
                document = JsoupUtils.getConnection(href).get();
            } catch (Exception e) {
                //logger.error("URL ERROR10:::".concat(href).concat(e.toString()));
                continue;
            }
            Element element = document.selectFirst(".ui-page-inner > a[href]");
            String url = element.attr("href");
            urlSet.add(url);//暂时都放集合里备用
        }
        List<String> urlList = new ArrayList<>(urlSet);
        Map<String, Integer> hrefMap = new HashMap<>();
        for (int i = 0; i < urlList.size(); i++) {
            String u = urlList.get(i);
            if (u.endsWith("/")) {
                String num = u.replaceAll("[^0-9]+", "");
                String baseU = u.replaceAll("/" + num + "/", "");
                Document document = JsoupUtils.getConnection(u).get();
                Elements elements = document.select(".ui-page-inner > a");
                Element element = elements.get(elements.size() - 2);
                Integer pageMaxNum = Integer.valueOf(element.text());//原始数据抓取
                pageMaxNum = 1;
                for (int ii = 1; ii <= pageMaxNum; ii++) {
                    u = baseU + "/" + ii + "/";
                    hrefMap.put(u, 0);
                }
            }
        }
        Map<String, Integer> meiShiArticleMap = new HashMap<>();
        for (Object key : hrefMap.keySet()) {
            Document document = JsoupUtils.getConnection(key.toString()).get();
            Element element = document.body();
            Elements elements = element.select(".detail > h2 > a");
            for (Element e : elements) {
                String href = e.attr("href");
                meiShiArticleMap.put(href, 0);
            }
        }
        for (Object key : meiShiArticleMap.keySet()) {
            Document document = JsoupUtils.getConnection(key.toString()).get();
            Element element = document.body();
            String title = element.select(".recipe_De_title >a").text();
            //标题存在的不抓取了
            synchronized (this) {
                if (redisTemplate.opsForSet().isMember("titles", title)) {
                    //logger.error("已存在:::".concat(title));
                    continue;
                }
            }
            String content = element.select(".space_box_home").html();
            Set<String> imgs = StringUtils4Share.getImgStr(content);
            if (imgs.isEmpty()) {
                //没图片的不要
                continue;
            }
            String cover = new ArrayList<>(imgs).get(0);
            //整理结构
            AddBackHuoDongView addBackHuoDongView = new AddBackHuoDongView();
            addBackHuoDongView.setTitle(title);
            addBackHuoDongView.setCreatepeople("爬虫-朱超");
            addBackHuoDongView.setOpentime(DateUtil.getCurrentTime());
            addBackHuoDongView.setDeadline("2020-12-31 23:59:59");
            addBackHuoDongView.setMoney("500");
            addBackHuoDongView.setCovermap(cover);
            addBackHuoDongView.setSharingmaxnum("10000");
            addBackHuoDongView.setIntroduce(content);
            addBackHuoDongView.setCompany("www.meishichina.com");
            addBackHuoDongView.setFenlei("无");
            addBackHuoDongView.setLayout(0);
            Set<Integer> tags = new HashSet<>();
            tags.add(5);//美食
            addBackHuoDongView.setTagids(tags);
            try {
                tbHuodongxiangqingService.add(addBackHuoDongView);
                logger.info(JSON.toJSONString(addBackHuoDongView));
                redisTemplate.opsForSet().add("titles", title);
            } catch (Exception e) {
                //logger.error("URL ERROR7:::".concat(e.toString()));
                continue;
            }
        }
    }

    @Override
    public void checkGuokr() throws Exception {
        Map<String, Integer> map = new TreeMap();
        map.put("chemistry", 0);
        map.put("biology", 0);
        map.put("environment", 0);
        map.put("astronomy", 0);
        map.put("medicine", 0);
        map.put("food", 0);
        map.put("forensic", 0);
        map.put("sex", 0);
        map.put("earth", 0);
        map.put("psychology", 0);
        map.put("sci-fiction", 0);
        map.put("math", 0);
        map.put("diy", 0);
        map.put("agronomy", 0);
        map.put("engineering", 0);
        map.put("electronics", 0);
        map.put("atmosphere", 0);
        map.put("education", 0);
        map.put("communication", 0);
        map.put("society", 0);
        map.put("internet", 0);
        map.put("aerospace", 0);
        map.put("others", 0);
        String apiLink = "https://www.guokr.com/apis/minisite/article.json?retrieve_type=by_subject&subject_key=%s&limit=%s&offset=%s";
        Integer pageSize = 20;
        for (String key : map.keySet()) {
            String url = String.format(apiLink, key, 1, 0);
            String retText = OKHttpUtils.client(url, 10);
            GuokrView guokrView;
            try {
                guokrView = JSON.parseObject(retText, GuokrView.class);
            } catch (Exception e) {
                //logger.error("URL ERROR0:::".concat(url).concat(e.toString()));
                continue;
            }
            Integer total = guokrView.getTotal();
            Integer pageNum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;//原始数据抓取
            pageNum = 1;//每小时抓一页
            for (int i = 0; i < pageNum; i++) {
                url = String.format(apiLink, key, 20, 20 * i);
                retText = OKHttpUtils.client(url, 10);
                try {
                    guokrView = JSON.parseObject(retText, GuokrView.class);
                } catch (Exception e) {
                    //logger.error("URL ERROR1:::".concat(url).concat(e.toString()));
                    continue;
                }
                List<GuokrSubView> guokrSubViews = guokrView.getResult();
                for (GuokrSubView guokrSubView : guokrSubViews) {
                    url = guokrSubView.getResourceUrl();
                    retText = OKHttpUtils.client(url, 10);
                    try {
                        guokrView = JSON.parseObject(retText, GuokrView.class);
                    } catch (Exception e) {
                        //logger.error("URL ERROR2:::".concat(url).concat(e.toString()));
                        continue;
                    }
                    GuokrSubView subView = guokrView.getResult().get(0);
                    if (subView.getImageInfo() == null) {
                        //logger.error("URL ERROR3:::".concat(url));
                        continue;
                    }
                    //标题存在的不抓取了
                    synchronized (this) {
                        if (redisTemplate.opsForSet().isMember("titles", subView.getTitleHide())) {
                            //logger.error("已存在:::".concat(subView.getTitleHide()));
                            continue;
                        }
                    }
                    AddBackHuoDongView addBackHuoDongView = new AddBackHuoDongView();
                    addBackHuoDongView.setTitle(subView.getTitleHide());
                    addBackHuoDongView.setCreatepeople("爬虫-朱超");
                    addBackHuoDongView.setOpentime(DateUtil.getCurrentTime());
                    addBackHuoDongView.setDeadline("2020-12-31 23:59:59");
                    addBackHuoDongView.setMoney("500");
                    addBackHuoDongView.setCovermap(subView.getImageInfo().getUrl());
                    addBackHuoDongView.setSharingmaxnum("10000");
                    addBackHuoDongView.setIntroduce(subView.getContent());
                    addBackHuoDongView.setCompany("www.guokr.com");
                    addBackHuoDongView.setFenlei("无");
                    addBackHuoDongView.setLayout(0);
                    Set<Integer> tags = new HashSet<>();
                    tags.add(18);//科技
                    addBackHuoDongView.setTagids(tags);
                    try {
                        tbHuodongxiangqingService.add(addBackHuoDongView);
                        logger.info(JSON.toJSONString(addBackHuoDongView));
                        redisTemplate.opsForSet().add("titles", subView.getTitleHide());
                    } catch (Exception e) {
                        //logger.error("URL ERROR7:::".concat(e.toString()));
                        continue;
                    }
                }
            }
        }
    }

    @Override
    public void checkIFeng() throws Exception {
        String head = "http://ent.ifeng.com/";
        Set<IFentView> iFentViews = new HashSet();
        iFentViews.add(new IFentView("star", "20074"));
        iFentViews.add(new IFentView("movie", "20075"));
        iFentViews.add(new IFentView("tv", "20076"));
        iFentViews.add(new IFentView("music", "20077"));
        List<LinkedTreeMap> linkedTreeMaps = new ArrayList<>();
        Set<String> targetUrls = new HashSet<>(iFentViews.size());
        for (IFentView iFentView : iFentViews) {
            Document document = JsoupUtils.getConnection(head + iFentView.getId()).get();
            Element element = document.head();
            Elements elements = element.getElementsByTag("script");
            element = elements.get(5);
            String jsContent = element.html();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine js = manager.getEngineByName("javascript");
            js.eval(jsContent.concat("function reObj(){return allData  ;}"));
            Invocable jsInvo = (Invocable) js;
            Object object = jsInvo.invokeFunction("reObj");
            String retText = JSONUtils.toJSONString(object);
            //转换为Map对象
            Gson gson = new Gson();
            Map result = gson.fromJson(retText, new TypeToken<Map>() {
            }.getType());
            object = result.get("newsstream");
            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) object;
            linkedTreeMap = (LinkedTreeMap) linkedTreeMap.get("0");
            String id = (String) linkedTreeMap.get("id");
            String targetUrl = String.format("http://shankapi.ifeng.com/shanklist/_/getColumnInfo/_/default/%s/-/10000/4-%s-/", id, iFentView.getTag());
            targetUrls.add(targetUrl);
        }

        for (String u : targetUrls) {
            String retText = OKHttpUtils.client(u, 10);
            Gson gson = new Gson();
            Map result = gson.fromJson(retText, new TypeToken<Map>() {
            }.getType());
            List<LinkedTreeMap> linkedSubTreeMaps = (List<LinkedTreeMap>) Ognl.getValue("data.newsstream", result);
            linkedTreeMaps.addAll(linkedSubTreeMaps);
        }

        //处理
        for (LinkedTreeMap linkedTreeMap : linkedTreeMaps) {
            String title = MapUtils.getString(linkedTreeMap, "title");
            //标题存在的不抓取了
            synchronized (this) {
                if (redisTemplate.opsForSet().isMember("titles", title)) {
                    //logger.error("已存在:::".concat(title));
                    continue;
                }
            }
            //String newsTime = MapUtils.getString(linkedTreeMap, "newsTime");
            Map imagesMap = MapUtils.getMap(linkedTreeMap, "thumbnails");
            List<LinkedTreeMap> images = (List<LinkedTreeMap>) MapUtils.getObject(imagesMap, "image");
            if (null == images || images.isEmpty()) {
                continue;
            }
            LinkedTreeMap imageTree = images.get(0);
            String cover = MapUtils.getString(imageTree, "url");
            String url = MapUtils.getString(linkedTreeMap, "url");
            Document document = JsoupUtils.getConnection(url).get();
            Element element = document.head();
            Elements elements = element.getElementsByTag("script");
            try {
                element = elements.get(5);
            } catch (Exception e) {
                //logger.error("URL ERROR4:::".concat(url).concat(e.toString()));
                continue;
            }
            String jsContent = element.html();
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine js = manager.getEngineByName("javascript");
            try {
                js.eval(jsContent.concat("function reObj(){return allData  ;}"));
            } catch (Exception e) {
                //logger.error("URL ERROR5:::".concat(url).concat(e.toString()));
                continue;
            }

            Invocable jsInvo = (Invocable) js;
            Object object;
            try {
                object = jsInvo.invokeFunction("reObj");
            } catch (Exception e) {
                //logger.error("URL ERROR6:::".concat(url).concat(e.toString()));
                continue;
            }
            String retText = JSONUtils.toJSONString(object);
            Gson gson = new Gson();
            Map result = gson.fromJson(retText, new TypeToken<Map>() {
            }.getType());
            LinkedTreeMap content = (LinkedTreeMap) Ognl.getValue("docData.contentData.contentList", result);
            content = (LinkedTreeMap) content.get("0");
            String data = MapUtils.getString(content, "data");
            //整理结构
            AddBackHuoDongView addBackHuoDongView = new AddBackHuoDongView();
            addBackHuoDongView.setTitle(title);
            addBackHuoDongView.setCreatepeople("爬虫-朱超");
            addBackHuoDongView.setOpentime(DateUtil.getCurrentTime());
            addBackHuoDongView.setDeadline("2020-12-31 23:59:59");
            addBackHuoDongView.setMoney("500");
            addBackHuoDongView.setCovermap(cover);
            addBackHuoDongView.setSharingmaxnum("10000");
            addBackHuoDongView.setIntroduce(data);
            addBackHuoDongView.setCompany("ent.ifeng.com");
            addBackHuoDongView.setFenlei("无");
            addBackHuoDongView.setLayout(0);
            Set<Integer> tags = new HashSet<>();
            tags.add(4);//娱乐
            addBackHuoDongView.setTagids(tags);
            try {
                tbHuodongxiangqingService.add(addBackHuoDongView);
                logger.info(JSON.toJSONString(addBackHuoDongView));
                redisTemplate.opsForSet().add("titles", title);
            } catch (Exception e) {
                //logger.error("URL ERROR7:::".concat(e.toString()));
                continue;
            }
        }
    }

    @Override
    public void checkZimeika() {
        String site = "http://www.zimeika.com";
        Document document;
        try {
            document = JsoupUtils.getConnection("http://www.zimeika.com/article/lists/toutiao.html", 600000).get();
        } catch (Exception e) {
            return;
        }
        Set<String> firstHrefs = new HashSet<>();
        Element element = document.body();
        Elements elements = element.select(".nav-item > a");
        for (Element e : elements) {
            String href = e.attr("href");
            firstHrefs.add(site.concat(href));
        }
        for (String href : firstHrefs) {
            //取每个分类前100页面最新的，后续再看看
            for (int i = 1; i <= 100; i++) {
                String h = href.concat("?p=" + i);
                try {
                    document = JsoupUtils.getConnection(h, 600000).get();
                } catch (Exception e) {
                    //logger.info("ERROR0:::".concat(h).concat(e.toString()));
                    continue;
                }
                elements = document.getElementsByClass("list-url");
                for (Element e : elements) {
                    String title = e.text();
                    //标题存在的不抓取了
                    synchronized (this) {
                        if (redisTemplate.opsForSet().isMember("titles", title)) {
                            //logger.error("已存在:::".concat(title));
                            continue;
                        }
                    }
                    e = e.getElementsByTag("a").first();
                    h = site.concat(e.attr("href"));
                    try {
                        document = JsoupUtils.getConnection(h, 600000).get();
                    } catch (Exception ee) {
                        //logger.info("ERROR1:::".concat(h).concat(ee.toString()));
                        continue;
                    }
                    e = document.getElementById("content");
                    String content = e.html();
                    Set<String> imgs = PicUtil.getImgStr(content);
                    if (imgs.isEmpty()) {
                        continue;
                    }
                    String cover = (String) imgs.toArray()[0];
                    //整理结构
                    AddBackHuoDongView addBackHuoDongView = new AddBackHuoDongView();
                    addBackHuoDongView.setTitle(title);
                    addBackHuoDongView.setCreatepeople("爬虫-朱超");
                    addBackHuoDongView.setOpentime(DateUtil.getCurrentTime());
                    addBackHuoDongView.setDeadline("2020-12-31 23:59:59");
                    addBackHuoDongView.setMoney("500");
                    addBackHuoDongView.setCovermap(cover);
                    addBackHuoDongView.setSharingmaxnum("10000");
                    addBackHuoDongView.setIntroduce(content);
                    addBackHuoDongView.setCompany("www.zimeika.com");
                    addBackHuoDongView.setFenlei("无");
                    addBackHuoDongView.setLayout(0);
                    Set<Integer> tags = new HashSet<>();
                    addBackHuoDongView.setTagids(tags);
                    try {
                        if (tbHuodongxiangqingService.add(addBackHuoDongView)) {
                            redisTemplate.opsForSet().add("titles", title);
                            logger.info(JSON.toJSONString(addBackHuoDongView));
                        }
                    } catch (Exception eee) {
                        //logger.error("URL ERROR7:::".concat(h).concat(eee.toString()));
                        continue;
                    }
                }
            }
        }
    }

    @Override
    public void checkZimeika4Video() throws Exception {
        String site = "http://www.zimeika.com";
        Set<String> firstHrefs = new HashSet<>();
        Document document = JsoupUtils.getConnection("http://www.zimeika.com/video/lists/xigua.html", 600000).get();
        Element element = document.body();
        Elements elements = element.select(".nav-item > a");
        for (Element e : elements) {
            String href = e.attr("href");
            firstHrefs.add(site.concat(href));
        }
        for (String href : firstHrefs) {
            //取每个分类前100页面最新的，后续再看看
            for (int i = 1; i <= 100; i++) {
                String h = href.concat("?p=" + i);
                try {
                    document = JsoupUtils.getConnection(h, 600000).get();
                } catch (Exception e) {
                    //logger.info("ERROR11:::".concat(h).concat(e.toString()));
                    continue;
                }
                elements = document.select(".video-matrix > a");
                for (Element e : elements) {
                    String detailHref = site.concat(e.attr("href"));
                    String title = e.attr("title");
                    e = e.selectFirst(".video-during");
                    String videoDuring = e.text();
                    //标题存在的不抓取了
                    synchronized (this) {
                        if (redisTemplate.opsForSet().isMember("titles", title)) {
                            //logger.error("已存在:::".concat(title));
                            continue;
                        }
                    }
                    try {
                        document = JsoupUtils.getConnection(detailHref, 600000).get();
                    } catch (Exception ee) {
                        //logger.info("ERROR12:::".concat(detailHref).concat(ee.toString()));
                        continue;
                    }
                    e = document.getElementById("video_thumb");
                    String cover = e.attr("src");
                    e = document.selectFirst(".thumbnail > a");
                    if (null == e) {
                        continue;
                    }
                    String content = e.attr("href");
                    //整理结构
                    AddBackHuoDongView addBackHuoDongView = new AddBackHuoDongView();
                    addBackHuoDongView.setSecond(StringUtils4Share.getSecond(videoDuring));
                    addBackHuoDongView.setTitle(title);
                    addBackHuoDongView.setCreatepeople("爬虫-朱超");
                    addBackHuoDongView.setOpentime(DateUtil.getCurrentTime());
                    addBackHuoDongView.setDeadline("2020-12-31 23:59:59");
                    addBackHuoDongView.setMoney("500");
                    addBackHuoDongView.setCovermap(cover);
                    addBackHuoDongView.setSharingmaxnum("10000");
                    addBackHuoDongView.setIntroduce(content);
                    addBackHuoDongView.setCompany("www.zimeika.com");
                    addBackHuoDongView.setFenlei("无");
                    addBackHuoDongView.setLayout(0);
                    Set<Integer> tags = new HashSet<>();
                    addBackHuoDongView.setTagids(tags);
                    try {
                        if (tbHuodongxiangqingService.add(addBackHuoDongView)) {
                            redisTemplate.opsForSet().add("titles", title);
                            logger.info(JSON.toJSONString(addBackHuoDongView));
                        }
                    } catch (Exception eee) {
                        //logger.error("URL ERROR13:::".concat(h).concat(eee.toString()));
                        continue;
                    }
                }
            }
        }
    }
}
