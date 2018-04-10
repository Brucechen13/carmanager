package com.cc.carmanager.util;

import android.renderscript.ScriptIntrinsicYuvToRGB;

/**
 * Created by chenc on 2017/11/25.
 */

public class NetUrlsSet {
    //首页轮播图
    public final static String URL_IMAGE_LIST = "http://www.wxlovezy.top:8080/spring/news/imageList/0/0";
    //新闻列表
    public final static String URL_NEWS_LIST = "http://www.wxlovezy.top:8080/spring/news/list/%d/%d/%d/%d";

    //首页导航栏
    public final static  String URL_NAV_LIST = "http://www.wxlovezy.top:8080/spring/nav/subNav/2";

    //新闻搜索列表
    public final static String URL_NEWS_SEARCHLIST = "http://www.wxlovezy.top:8080/spring/news/query/%s/%d/%d/%d/%d";
    //新闻详情
    public final static String URL_NEWS_CONTENT = "http://www.wxlovezy.top:8080/spring/news/cont/%d";
    //新闻评论
    public final static String URL_COMMENTS_LIST = "http://www.wxlovezy.top:8080/spring/news/comment/%d/%d/%d";

    //车辆品牌
    public final static String URL_CAR_BRAND = "http://www.wxlovezy.top:8080/spring/carBrand/getList";
    //带关键词车辆品牌
    public final static String URL_CAR_BRANDSEARCH = "http://www.wxlovezy.top:8080/spring/carBrand/queryList/%s";
    //品牌车列表
    public final static String URL_CAR_LIST = "http://www.wxlovezy.top:8080/spring/car/getList/%d/1";

    //车款式列表
    public final static String URL_CAR_SERIES = "http://www.wxlovezy.top:8080/spring/carSeries/getList/%d/%d";
    //车款式年份列表
    public final static String URL_CAR_YEARTYPE = "http://www.wxlovezy.top:8080/spring/carSeries/getYearList/%d";

    //地图锚点数据
    public final static String URL_CAR_MAP = "http://www.wxlovezy.top:8080/spring/station/list";

    //车所有款式配置
    public final static String URL_CAR_CONFIG = "http://www.wxlovezy.top:8080/spring/carSeries/getConfigListByCarId/%d";

    //车某一配置
    public final static String URL_SERIES_CONFIG = "http://www.wxlovezy.top:8080/spring/carSeries/getConfigList/%d/%d";

    //型号车大图
    public final static String URL_CAR_PICBANNER = "http://www.wxlovezy.top:8080/spring/carPic/getCarPic/%d";

    //型号车所有图片
    public final static String URL_CAR_PIC = "http://www.wxlovezy.top:8080/spring/carPic/picList/%d/0";

    //款式车所有图片
    public final static String URL_SERIES_PIC = "http://www.wxlovezy.top:8080/spring/carPic/picList/%d/%d/0";

    //经销商列表
    public final static String URL_CAR_SELLER = "http://www.wxlovezy.top:8080/spring/saleCenter/getSaleCenterList/%d/%s/%s";
    //维修列表
    public final static String URL_CAR_REPAIR = "http://www.wxlovezy.top:8080/spring/saleCenter/getRepairFactoryList/%d/%s/%s";

    //添加评论
    public final static String URL_CAR_COMMENT = "http://www.wxlovezy.top:8080/spring/user/addComment";
    //评论列表
    public final static String URL_COMMENT_LIST = "http://www.wxlovezy.top:8080/spring/user/comments/%d/%d";
    //新闻点赞
    public final static String URL_NEWS_LIKE = "http://www.wxlovezy.top:8080/spring/user/agree/%d";
    //新闻收藏
    public final static String URL_NEWS_COLLECT = "http://www.wxlovezy.top:8080/spring/user/collect/%d";
    //新闻点赞列表
    public final static String URL_NEWS_LIKELIST = "http://www.wxlovezy.top:8080/spring/user/agreeNews/%d/%d";
    //新闻收藏列表
    public final static String URL_NEWS_COLLECTLIST = "http://www.wxlovezy.top:8080/spring/user/collections/%d/%d";
    //用户登录
    public final static String URL_USER_LOGIN = "http://www.wxlovezy.top:8080/spring/Login/login";
    //用户注册
    public final static String URL_USER_SIGN = "http://www.wxlovezy.top:8080/spring/Sign/sign";
    //是否登录
    public final static  String URL_USER_ISLOGIN = "http://www.wxlovezy.top:8080/spring/Login/isLogin";

    //答题
    public final static String URL_QUESTION_LIST = "http://www.wxlovezy.top:8080/spring/questionBank/generateQuestion";

    //答题提交
    public final static String URL_QUESTION_COMMIT = "http://www.wxlovezy.top:8080/spring/questionBank/answerQuestion";

    //咨询
    public final static String URL_CONSUMER_LIST = "http://www.wxlovezy.top:8080/spring/question/getQuestionList/%d/%d";

    //添加咨询
    public final static String URL_CONSUMER_ADD = "http://www.wxlovezy.top:8080/spring/question/addQuestion";

    //推荐
    public final static String URL_NEW = "http://47.95.112.170:5000/news";
    //http://app.api.autohome.com.cn/autov4.8.8/news/newslist-pm1-c0-nt0-p1-s30-l0.json

    //车型
    public final static String URL_CAR = "http://47.95.112.170:5000/brands";
    //https://comm.app.autohome.com.cn/comm_v1.0.0/ashx/brand-pm1-ts636195739525494900.json



}
