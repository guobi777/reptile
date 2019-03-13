package com.gpp.api.service;

import com.baomidou.mybatisplus.service.IService;
import com.gpp.api.entity.TbHuodongxiangqing;
import com.gpp.api.view.AddBackHuoDongView;

/**
 * <p>
 * 活动(文章)表 服务类
 * </p>
 *
 * @author 朱超
 * @since 2018-11-27
 */
public interface ShareArticleService extends IService<TbHuodongxiangqing> {
    Boolean add(AddBackHuoDongView addBackHuoDongView);
}
