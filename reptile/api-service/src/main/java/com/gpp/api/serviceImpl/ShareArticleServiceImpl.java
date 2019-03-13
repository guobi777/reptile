package com.gpp.api.serviceImpl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gpp.api.entity.ShareArticleAdv;
import com.gpp.api.entity.ShareTagArticle;
import com.gpp.api.entity.TbHuodongxiangqing;
import com.gpp.api.mapper.TbHuodongxiangqingDao;
import com.gpp.api.service.ShareArticleAdvService;
import com.gpp.api.service.ShareArticleService;
import com.gpp.api.service.ShareTagArticleService;
import com.gpp.api.util.DataHandleUtil;
import com.gpp.api.util.DateUtil;
import com.gpp.api.util.UUID;
import com.gpp.api.view.AddBackHuoDongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 活动(文章)表 服务实现类
 * </p>
 *
 * @author 朱超
 * @since 2018-11-27
 */
@Service
public class ShareArticleServiceImpl extends ServiceImpl<TbHuodongxiangqingDao, TbHuodongxiangqing> implements ShareArticleService {

    @Value("${app.singlePage}")
    private String appDomin;

    @Autowired
    private ShareArticleAdvService shareArticleAdvService;

    @Autowired
    private ShareTagArticleService shareTagArticleService;

    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public Boolean add(AddBackHuoDongView addBackHuoDongView) {
        TbHuodongxiangqing tbHuodongxiangqing = new TbHuodongxiangqing();
        String huodongid = UUID.getUUID();
        tbHuodongxiangqing.setId(huodongid);
        tbHuodongxiangqing.setCreatetime(DateUtil.getCurrentTime());
        tbHuodongxiangqing.setReadnum("0");
        tbHuodongxiangqing.setDetaildiagram("");
        tbHuodongxiangqing.setUpdatetime("");
        tbHuodongxiangqing.setShifoutixing("0");//新增默认禁止
        tbHuodongxiangqing.setUpdatepeople("");
        tbHuodongxiangqing.setType(appDomin + "/articleDetails.share?alipayId=" + "&id=" + huodongid + "&isShow=0&isActivity=1");
        tbHuodongxiangqing.setZhuanfaurl("");
        tbHuodongxiangqing.setReleasetime("");
        tbHuodongxiangqing.setHuodongType("");
        tbHuodongxiangqing.setRemind("");
        tbHuodongxiangqing.setUploadType("");
        //SET
        tbHuodongxiangqing.setCreatepeople(addBackHuoDongView.getCreatepeople());
        tbHuodongxiangqing.setOpentime(addBackHuoDongView.getOpentime());
        tbHuodongxiangqing.setDeadline(addBackHuoDongView.getDeadline());
        tbHuodongxiangqing.setMoney("500");//爬虫来的都500
        tbHuodongxiangqing.setCovermap(addBackHuoDongView.getCovermap());
        tbHuodongxiangqing.setSharingmaxnum(addBackHuoDongView.getSharingmaxnum());
        tbHuodongxiangqing.setIntroduce(addBackHuoDongView.getIntroduce());
        tbHuodongxiangqing.setTitle(addBackHuoDongView.getTitle());
        tbHuodongxiangqing.setCompany(addBackHuoDongView.getCompany());
        tbHuodongxiangqing.setRemainingpieces(addBackHuoDongView.getSharingmaxnum());
        tbHuodongxiangqing.setFenlei(addBackHuoDongView.getFenlei());
        tbHuodongxiangqing.setLayout(addBackHuoDongView.getLayout());
        tbHuodongxiangqing.setVideoDuring(addBackHuoDongView.getSecond());
        //SET
        tbHuodongxiangqing.setRemainbaifenbi(DataHandleUtil.division(Integer.parseInt(tbHuodongxiangqing.getRemainingpieces()), Integer.parseInt(tbHuodongxiangqing.getSharingmaxnum())));
        boolean flag = insert(tbHuodongxiangqing);
        if (flag) {
            //文章编号
            Integer articleId = tbHuodongxiangqing.getHdId();
            //建立文章与广告的关联
            for (Integer i : addBackHuoDongView.getAdvIds()) {
                ShareArticleAdv shareArticleAdv = new ShareArticleAdv();
                shareArticleAdv.setAdvId(i);
                shareArticleAdv.setArticleId(articleId);
                shareArticleAdvService.insert(shareArticleAdv);
            }
            //建立文章与类目的关联
            for (Integer i : addBackHuoDongView.getTagids()) {
                ShareTagArticle shareTagArticle = new ShareTagArticle();
                shareTagArticle.setCreateTime(new Date());
                shareTagArticle.setTagId(i);
                shareTagArticle.setArticleId(articleId);
                shareTagArticle.setCreator(addBackHuoDongView.getCreatepeople());
                shareTagArticleService.insert(shareTagArticle);
            }
        }
        return true;
    }


}
