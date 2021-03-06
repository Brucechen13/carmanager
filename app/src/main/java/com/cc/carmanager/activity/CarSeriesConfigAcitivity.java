package com.cc.carmanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.carmanager.R;
import com.cc.carmanager.activity.base.BarBaseActivity;
import com.cc.carmanager.bean.CarsConfigListBean;
import com.cc.carmanager.comparisoncar.bean.ComparisonCarItem;
import com.cc.carmanager.comparisoncar.vhtableview.VHBaseAdapter;
import com.cc.carmanager.comparisoncar.vhtableview.VHTableView;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by chenc on 2018/3/22.
 */

public class CarSeriesConfigAcitivity extends BarBaseActivity{

    private static final String IDS = "ids";

    public String CC = "●标配 ○选配 -无";

    private VHTableView vht_table;
    private ImageView mImgLeft;
    private ImageView mImgRight;

    //可以得到屏幕的宽度  按 屏幕的比例去划分  后续修改
    private int title0Width;
    private int titleWidth;
    private int titleHieght;
    private int cellHieght;
    private int titleLeftPadding;

    ArrayList<ArrayList<ComparisonCarItem>> contentAllData;//显示数据
    ArrayList<ArrayList<ComparisonCarItem>> contentData;//隐藏相同项时  零时保存数据
    private ArrayList<ComparisonCarItem> titleData;
    private boolean isComparsionSame;//对比中
    private int mScrollX;

    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_seriesconfig);

        setHeader("车型配置");

        Bundle bundle = getIntent().getExtras();
        carId = bundle.getInt("carId");

        initView();
        initData();
    }


    protected void initView() {
        vht_table = (VHTableView) findViewById(R.id.vht_table);
        mImgLeft = (ImageView) findViewById(R.id.img_left);
        mImgRight = (ImageView) findViewById(R.id.img_right);
        mImgLeft.setVisibility(View.GONE);
        mImgRight.setVisibility(View.GONE);
        vht_table.setOnUIScrollChanged(new VHTableView.OnUIScrollChanged() {
            @Override
            public void onUIScrollChanged(int l, int oldl, int maxScrollX, int getScrollX) {
                mScrollX = l;
                mImgRight.setVisibility(View.VISIBLE);
                mImgLeft.setVisibility(View.VISIBLE);
                //滑到最左
                if (getScrollX < 3) {
                    mImgLeft.setVisibility(View.GONE);
                } else if (getScrollX + 3 >= maxScrollX) {  //滑到最右
                    mImgRight.setVisibility(View.GONE);
                } else {  //滑到中间
                }
            }
        });
        mImgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollLastPoint(mScrollX + titleWidth);
            }
        });
        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollLastPoint(mScrollX - titleWidth);
            }
        });
    }

    protected void initData() {
        if (isComparsionSame && !contentAllData.isEmpty()) {
            toggleData();
        }
        String url_news = String.format(Locale.CHINA, NetUrlsSet.URL_SERIES_CONFIG, 9, 30);
        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarsConfigListBean mRecommendBean = gson.fromJson(resultStr, CarsConfigListBean.class);
                if (mRecommendBean.isSuccess()) {
                    try {
                        vht_table.setVisibility(View.VISIBLE);
                        //设置数据源
                        titleData = new ArrayList<>();
                        //title 第一个空格
                        ComparisonCarItem comparisonCarItem = new ComparisonCarItem();
                        comparisonCarItem.setName("");
                        titleData.add(comparisonCarItem);

                        List<CarsConfigListBean.CarConfigBean> carConfigBeans = mRecommendBean.getData();
                        Iterator<CarsConfigListBean.CarConfigBean> sListIterator = carConfigBeans.iterator();
                        while(sListIterator.hasNext()){
                            CarsConfigListBean.CarConfigBean bean = sListIterator.next();
                            if(bean.getCarConfig().getBasisConfig().equals("") || bean.getCarConfig().getLightingConfig().equals("")){
                                sListIterator.remove();
                            }
                        }

                        for(CarsConfigListBean.CarConfigBean bean :carConfigBeans ){
                            ComparisonCarItem comparisonCarItem2 = new ComparisonCarItem();
                            comparisonCarItem2.setName(bean.getCarSeries().getSeriesName());
                            //comparisonCarItem2.setId(bean.getCarSeries().getId());
                            titleData.add(comparisonCarItem2);
                        }
                        //title 最后的+号
                        ComparisonCarItem comparisonCarItem3 = new ComparisonCarItem();
                        comparisonCarItem3.setImgBackgroud(R.drawable.icon_tianjia);
                        titleData.add(comparisonCarItem3);


                        //车的具体参数
                        contentAllData = new ArrayList<>();
                        String[] carConfigs = new String[]{
                                "基本参数", "车身", "发动机", "变速箱", "底盘转向", "车轮制动", "主/被动安全装备", "辅助/操控配置", "外部/防盗配置", "内部配置", "座椅配置", "多媒体配置", "灯光配置", "玻璃/后视镜", "空调/冰箱"
                        };
                        String[][] carConfigMore = new String[][]{
                                {"厂商指导价(元)", "厂商", "级别", "上市时间", "发动机", "变速箱", "长*宽*高(mm)", "车身结构", "最高车速(km/h)", "官方0-100km/h加速(s)", "实测0-100km/h加速(s)", "实测100-0km/h制动(m)", "实测油耗(L/100km)", "工信部综合油耗(L/100km)", "实测离地间隙(mm)", "整车质保"},
                                {"长度(mm)", "宽度(mm)", "高度(mm)", "轴距(mm)", "前轮距(mm)", "后轮距(mm)", "最小离地间隙(mm)", "整备质量(kg)", "车身结构", "车门数(个)", "座位数(个)", "油箱容积(L)", "行李厢容积(L)"},
                                {"发动机型号", "排量(mL)", "排量(L)", "进气形式", "气缸排列形式", "气缸数(个)", "每缸气门数(个)", "压缩比", "配气机构", "缸径(mm)", "行程(mm)", "最大马力(Ps)", "最大功率(kW)", "最大功率转速(rpm)", "最大扭矩(N·m)", "最大扭矩转速(rpm)", "发动机特有技术", "燃料形式", "燃油标号", "供油方式", "缸盖材料", "缸体材料", "环保标准"},
//                                {"电机类型", "电动机总功率(kW)", "电动机总扭矩(N·m)", "前电动机最大功率(kW)", "前电动机最大扭矩(N·m)", "后电动机最大功率(kW)", "后电动机最大扭矩(N·m)", "系统综合功率(kW)", "系统综合扭矩(N·m)", "电池类型", "工信部续航里程(km)", "电池容量(kWh)", "百公里耗电量(kWh/100km)", "电池组质保", "电池充电时间", "快充电量(%)", "充电桩价格"},
                                {"简称", "挡位个数", "变速箱类型"},
                                {"驱动方式", "四驱形式", "中央差速器结构", "前悬架类型", "后悬架类型", "助力类型", "车体结构"},
                                {"前制动器类型", "后制动器类型", "驻车制动类型", "前轮胎规格", "后轮胎规格", "备胎规格"},
                                {"主/副驾驶座安全气囊", "前/后排侧气囊", "前/后排头部气囊(气帘)", "膝部气囊", "胎压监测装置", "零胎压继续行驶", "安全带未系提示", "ISOFIX儿童座椅接口", "ABS防抱死", "制动力分配(EBD/CBC等)", "刹车辅助(EBA/BAS/BA等)", "牵引力控制(ASR/TCS/TRC等)", "车身稳定控制(ESC/ESP/DSC等)", "并线辅助", "车道偏离预警系统", "主动刹车/主动安全系统", "夜视系统", "疲劳驾驶提示"},
                                {"前/后驻车雷达", "倒车视频影像", "全景摄像头", "定速巡航", "自适应巡航", "自动泊车入位", "发动机启停技术", "自动驾驶技术", "上坡辅助", "自动驻车", "陡坡缓降", "可变悬架", "空气悬架", "电磁感应悬架", "可变转向比", "前桥限滑差速器/差速锁", "中央差速器锁止功能", "后桥限滑差速器/差速锁", "整体主动转向系统"},
                                {"电动天窗", "全景天窗", "多天窗", "运动外观套件", "铝合金轮圈", "电动吸合门", "侧滑门", "电动后备厢", "感应后备厢", "车顶行李架", "发动机电子防盗", "车内中控锁", "遥控钥匙", "无钥匙启动系统", "无钥匙进入系统", "远程启动"},
                                {"皮质方向盘", "方向盘调节", "方向盘电动调节", "多功能方向盘", "方向盘换挡", "方向盘加热", "方向盘记忆", "行车电脑显示屏", "全液晶仪表盘", "HUD抬头数字显示", "内置行车记录仪", "主动降噪", "手机无线充电"},
                                {"座椅材质", "运动风格座椅", "座椅高低调节", "腰部支撑调节", "肩部支撑调节", "主/副驾驶座电动调节", "第二排靠背角度调节", "第二排座椅移动", "后排座椅电动调节", "副驾驶位后排可调节按钮", "电动座椅记忆", "前/后排座椅加热", "前/后排座椅通风", "前/后排座椅按摩", "第二排独立座椅", "第三排座椅", "后排座椅放倒方式", "前/后中央扶手", "后排杯架", "可加热/制冷杯架"},
                                {"GPS导航系统", "定位互动服务", "中控台彩色大屏", "中控台彩色大屏尺寸", "中控液晶屏分屏显示", "蓝牙/车载电话", "手机互联/映射", "车联网", "车载电视", "后排液晶屏", "220V/230V电源", "外接音源接口", "CD/DVD", "扬声器品牌", "扬声器数量"},
                                {"近光灯", "远光灯", "LED日间行车灯", "自适应远近光", "自动头灯", "转向辅助灯", "转向头灯", "前雾灯", "大灯高度可调", "大灯清洗装置", "车内氛围灯"},
                                {"前/后电动车窗", "车窗一键升降", "车窗防夹手功能", "防紫外线/隔热玻璃", "后视镜电动调节", "后视镜加热", "内/外后视镜自动防眩目", "流媒体车内后视镜", "后视镜电动折叠", "后视镜记忆", "后风挡遮阳帘", "后排侧遮阳帘", "后排侧隐私玻璃", "遮阳板化妆镜", "后雨刷", "感应雨刷"},
                                {"空调控制方式", "后排独立空调", "后座出风口", "温度分区控制", "车内空气调节/花粉过滤", "车载空气净化器", "车载冰箱"},
                        };
                        for(int i = 0; i < carConfigs.length; i++){
                            String carConfig = carConfigs[i];
                            List<List<String>> configRes = new ArrayList<>();
                            for (CarsConfigListBean.CarConfigBean bean : carConfigBeans) {
                                Gson gson2 = new Gson();
                                java.lang.reflect.Type type = new TypeToken<LinkedHashMap<String, String>>() {
                                }.getType();
                                String json_str = bean.getCarConfig().getTypeValue(carConfig);
                                LinkedHashMap<String, String> jsonMap = gson2.fromJson(json_str, type);
                                List<String> config = new ArrayList<>();
                                if(jsonMap != null) {
                                    for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                                        config.add(entry.getValue());
                                    }
                                }else{
                                    Log.e("car", "车型为空"+carId);
                                }
                                configRes.add(config);
                            }
                            boolean flag = true;
                            for(int j = 0; j < carConfigMore[i].length; j ++) {
                                String config = carConfigMore[i][j];
                                ArrayList<ComparisonCarItem> contentRowData = new ArrayList<>();
                                //每一行的第一个
                                ComparisonCarItem comparisonCarItem4 = new ComparisonCarItem();
                                comparisonCarItem4.setName(config);
                                //if (!contentAllData.isEmpty()) {//判断行标题
                                if(flag) {
                                    comparisonCarItem4.setHeader(true);
                                    flag=false;
                                }else{
                                    comparisonCarItem4.setHeader(false);
                                }
                                comparisonCarItem4.setRowTitle(carConfig);
                                //}
                                contentRowData.add(comparisonCarItem4);
                                //每一行中的具体车型
                                //开始判断这一行的值是否全部相同
                                boolean valueSame = true;
                                String value = null;
                                for(int k = 0; k < configRes.size(); k ++){
                                    ComparisonCarItem comparisonCarItem5 = new ComparisonCarItem();
                                    String val = configRes.get(k).get(j);
                                    comparisonCarItem5.setName(val.equals("")?"-":val);
                                    contentRowData.add(comparisonCarItem5);
                                }
                                //每一行中的最后一个 虚位
                                ComparisonCarItem comparisonCarItem6 = new ComparisonCarItem();
                                comparisonCarItem6.setName("-");
                                contentRowData.add(comparisonCarItem6);
                                contentAllData.add(contentRowData);

                            }
                        }
                        setAdapter();
                    } catch (NullPointerException e) {
                        Log.e("car", e.getLocalizedMessage());
                        ToastUtils.makeShortText("车型配置出错", CarSeriesConfigAcitivity.this);
                    }
                } else {
                    ToastUtils.makeShortText("未查询到车型配置", CarSeriesConfigAcitivity.this);
                }
            }
            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
//        {
//            try {
//                vht_table.setVisibility(View.VISIBLE);
//                List<CarComparisonBean.ParamEntity> data = carComparisonBean.getParam();
//
//                //设置数据源
//                titleData = new ArrayList<>();
//
//                //title 第一个空格
//                ComparisonCarItem comparisonCarItem = new ComparisonCarItem();
//                comparisonCarItem.setName("");
//                titleData.add(comparisonCarItem);
//
//                //title 第一行车型名称
//                List<CarComparisonBean.ParamEntity.ParamitemsEntity.ValueitemsEntity> paramitems = data.get(0).getParamitems().get(0).getValueitems();
//                for (CarComparisonBean.ParamEntity.ParamitemsEntity.ValueitemsEntity paramitem : paramitems) {
//
//                    ComparisonCarItem comparisonCarItem2 = new ComparisonCarItem();
//                    comparisonCarItem2.setName(paramitem.getValue());
//                    comparisonCarItem2.setId(paramitem.getSpecid());
//                    titleData.add(comparisonCarItem2);
//                }
////title 最后的+号
//                ComparisonCarItem comparisonCarItem3 = new ComparisonCarItem();
//                comparisonCarItem3.setImgBackgroud(R.drawable.icon_tianjia);
//                titleData.add(comparisonCarItem3);
//                //
//                data.get(0).getParamitems().remove(0);
//
//
//                //一大坨数据处理  没有写好    车的具体参数
//                contentAllData = new ArrayList<>();
//                for (CarComparisonBean.ParamEntity paramEntity : data) {
//
//                    for (CarComparisonBean.ParamEntity.ParamitemsEntity paramitemsEntity : paramEntity.getParamitems()) {
//                        ArrayList<ComparisonCarItem> contentRowData = new ArrayList<>();
//                        //每一行的第一个
//                        ComparisonCarItem comparisonCarItem4 = new ComparisonCarItem();
//                        if (paramitemsEntity.getName().indexOf("(") <= 5) {
//                            comparisonCarItem4.setName(paramitemsEntity.getName().replace("(", "\n("));
//                        } else {
//                            comparisonCarItem4.setName(paramitemsEntity.getName());
//                        }
//                        if (!contentAllData.isEmpty()) {//判断行标题
//                            ComparisonCarItem lastComparisonCar = contentAllData.get(contentAllData.size() - 1).get(0);
//                            comparisonCarItem4.setHeader(!lastComparisonCar.getRowTitle().equals(paramEntity.getName()));
//                        }
//                        comparisonCarItem4.setRowTitle(paramEntity.getName());
//                        contentRowData.add(comparisonCarItem4);
//                        //每一行中的具体车型
//                        //开始判断这一行的值是否全部相同
//                        boolean valueSame = true;
//                        String value = null;
//                        for (CarComparisonBean.ParamEntity.ParamitemsEntity.ValueitemsEntity valueitemsEntity : paramitemsEntity.getValueitems()) {
//                            ComparisonCarItem comparisonCarItem5 = new ComparisonCarItem();
//                            comparisonCarItem5.setName(valueitemsEntity.getValue());
//                            comparisonCarItem5.setId(valueitemsEntity.getSpecid());
////                            comparisonCarItem5.setColor(valueitemsEntity.getColor());
//                            contentRowData.add(comparisonCarItem5);
//                            if (valueSame) {
//                                if (!TextUtils.isEmpty(value)) {
//                                    valueSame = value.equals(comparisonCarItem5.getName());
//                                }
//                                value = comparisonCarItem5.getName();
//                            }
//                        }
//                        comparisonCarItem4.setSame(valueSame);
//                        //每一行中的最后一个 虚位
//                        ComparisonCarItem comparisonCarItem6 = new ComparisonCarItem();
//                        comparisonCarItem6.setName("-");
//                        contentRowData.add(comparisonCarItem6);
//
//                        contentAllData.add(contentRowData);
//                    }
//
//                }
//                setAdapter();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        //}
    }


    private void toggleData() {
        isComparsionSame = !isComparsionSame;
        if (isComparsionSame) {
            if (contentData == null) {
                contentData = new ArrayList<>();
            }
            contentData.clear();
            try {
                contentData.addAll(deepCopy(contentAllData));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            ArrayList<ArrayList<ComparisonCarItem>> temp = new ArrayList<>();
            //一大坨数据处理  没有写好
            String rowTitle = null;
            for (ArrayList<ComparisonCarItem> comparisonCarItems : contentAllData) {
                ComparisonCarItem comparisonCarItem = comparisonCarItems.get(0);
                if (comparisonCarItem.isSame()) {
                    temp.add(comparisonCarItems);
                    if (comparisonCarItem.isHeader()) {
                        rowTitle = comparisonCarItem.getRowTitle();
                    }
                } else {
                    if (!comparisonCarItem.isHeader() && comparisonCarItem.getRowTitle().equals(rowTitle)) {
                        comparisonCarItem.setHeader(true);
                        rowTitle = null;
                    }
                }
            }
            contentAllData.removeAll(temp);
        } else {
            contentAllData.clear();
            contentAllData.addAll(contentData);
        }
        setAdapter();
    }

    //使用序列化方法（相对靠谱的方法）
    public static <T> List<T> deepCopy(List<T> src) throws IOException,
            ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut
                .toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    private void setAdapter() {

        //● ○
        VHTableAdapter tableAdapter = new VHTableAdapter(CarSeriesConfigAcitivity.this);
//                    vht_table.setFirstColumnIsMove(true);//设置第一列是否可移动,默认不可移动
//                    vht_table.setShowTitle(false);//设置是否显示标题行,默认显示
        //一般表格都只是展示用的，所以这里没做刷新，真要刷新数据的话，重新setadaper一次吧
        vht_table.setAdapter(tableAdapter);
        vht_table.setCurrentTouchView(vht_table.getFirstHListViewScrollView());
        if (vht_table.getTitleLayout() != null) {
            View suspensionView = LayoutInflater.from(CarSeriesConfigAcitivity.this).inflate(R.layout.layout_comparison_cell_header, null);
            final TextView tvSuspension = (TextView) suspensionView.findViewById(R.id.tv_title);
            final TextView tvsubTitle = (TextView) suspensionView.findViewById(R.id.tv_subtitle);
            tvsubTitle.setText(CC);

            vht_table.addTitleLayout(suspensionView);

            vht_table.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    Log.i("w", "setOnScrollListener" + firstVisibleItem + "==" + visibleItemCount + "===" + totalItemCount);
                    if (contentAllData.size() - 1 <= firstVisibleItem)
                        return;
                    String rowTitle = contentAllData.get(firstVisibleItem).get(0).getRowTitle();
                    String nextRowTitle = contentAllData.get(firstVisibleItem + 1).get(0).getRowTitle();

                    tvSuspension.setText(rowTitle);

                    if (rowTitle == nextRowTitle) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vht_table.getTitleLayout().getLayoutParams();
                        params.topMargin = 0;
                        vht_table.getTitleLayout().setLayoutParams(params);
                    }

                    if (rowTitle != nextRowTitle) {
                        View childView = view.getChildAt(0);
                        if (childView != null) {
                            int titleHeight = vht_table.getTitleLayout().getHeight();
                            int bottom = childView.getBottom();
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) vht_table.getTitleLayout().getLayoutParams();
                            if (bottom < titleHeight) {
                                float pushedDistance = bottom - titleHeight;
                                params.topMargin = (int) pushedDistance;
                                vht_table.getTitleLayout().setLayoutParams(params);
                            } else {
                                if (params.topMargin != 0) {
                                    params.topMargin = 0;
                                    vht_table.getTitleLayout().setLayoutParams(params);
                                }
                            }
                        }
                    }
                }
            });
        }

        //滚动到上次位置
        scrollLastPoint(mScrollX);
    }

    private void scrollLastPoint(final int x) {
        vht_table.post(new Runnable() {
            @Override
            public void run() {
                vht_table.onUIScrollChanged(x, 0);
            }
        });
    }


    public class VHTableAdapter implements VHBaseAdapter {
        private Context context;

        public VHTableAdapter(Context context) {
            this.context = context;

            title0Width = (int) getResources().getDimensionPixelSize(R.dimen.dimen_size_60dp);
            titleWidth = (int) getResources().getDimensionPixelSize(R.dimen.dimen_size_95dp);
            titleHieght = (int) getResources().getDimensionPixelSize(R.dimen.dimen_size_75dp);
            cellHieght = (int) getResources().getDimension(R.dimen.dimen_size_50dp);
            titleLeftPadding = (int) getResources().getDimensionPixelSize(R.dimen.dimen_size_3dp);
        }

        //表格内容的行数，不包括标题行
        @Override
        public int getContentRows() {
            return contentAllData.size();
        }

        //列数
        @Override
        public int getContentColumn() {
            return titleData.size();
        }

        //标题的view，这里从0开始，这里要注意，一定要有view返回去，不能为null，每一行
        // 各列的宽度就等于标题行的列的宽度，且边框的话，自己在这里和下文的表格单元格view里面设置
        @Override
        public View getTitleView(final int columnPosition, ViewGroup parent) {
            FrameLayout view = (FrameLayout) LayoutInflater.from(CarSeriesConfigAcitivity.this).inflate(R.layout.layout_comparison_header, null);
            view.setMinimumWidth(titleHieght);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            ImageView imgDel = (ImageView) view.findViewById(R.id.img_del);
            ImageView imgAdd = (ImageView) view.findViewById(R.id.img_add);
            ImageView imgRank = (ImageView) view.findViewById(R.id.iv_item_rank_pic);
            tvTitle.setTextColor(getResources().getColor(R.color.black_font_color));
            tvTitle.setHeight(titleHieght);
            view.setBackgroundResource(R.drawable.bg_shape_gray);
            ComparisonCarItem comparisonCarItem = titleData.get(columnPosition);
            if (0 == columnPosition) {//第一列
                imgDel.setVisibility(View.GONE);
                imgRank.setVisibility(View.GONE);
                tvTitle.setWidth(title0Width);
            } else {
//                UserUtils.parseMsn(imgRank, comparisonCarItem.getRankUrl());
                imgRank.setVisibility(TextUtils.isEmpty(comparisonCarItem.getName()) ? View.GONE : View.VISIBLE);
                imgDel.setVisibility(TextUtils.isEmpty(comparisonCarItem.getName()) ? View.GONE : View.VISIBLE);
                imgDel.setOnClickListener(new View.OnClickListener() {//删除按钮
                    @Override
                    public void onClick(View v) {
                        titleData.remove(columnPosition);
                        for (ArrayList<ComparisonCarItem> comparisonCarItems : contentAllData) {
                            comparisonCarItems.remove(columnPosition);
                            //开始判断这一行的值是否全部相同
                            boolean valueSame = true;
                            String value = null;
                            for (int i = 1; i < comparisonCarItems.size() - 1; i++) {
                                if (valueSame) {
                                    if (!TextUtils.isEmpty(value)) {
                                        valueSame = value.equals(comparisonCarItems.get(i).getName());
                                    }
                                    value = comparisonCarItems.get(i).getName();
                                }
                            }
                            comparisonCarItems.get(0).setSame(valueSame);
                        }
                        if (contentData != null && isComparsionSame)//因为显示全部的时候 contentAllData  contentData  是同一对象
                            for (ArrayList<ComparisonCarItem> comparisonCarItems2 : contentData) {
                                comparisonCarItems2.remove(columnPosition);
                                //开始判断这一行的值是否全部相同
                                boolean valueSame = true;
                                String value = null;
                                for (int i = 1; i < comparisonCarItems2.size() - 1; i++) {
                                    if (valueSame) {
                                        if (!TextUtils.isEmpty(value)) {
                                            valueSame = value.equals(comparisonCarItems2.get(i).getName());
                                        }
                                        value = comparisonCarItems2.get(i).getName();
                                    }
                                }
                                comparisonCarItems2.get(0).setSame(valueSame);
                            }
                        setAdapter();
                        if (isComparsionSame) {
                            toggleData();
                        }
                    }
                });
                tvTitle.setWidth(titleWidth);
            }
            tvTitle.setText(comparisonCarItem.getName());
            if (comparisonCarItem.getImgBackgroud() != -1) {//添加按钮
                imgAdd.setVisibility(View.VISIBLE);
                if (getContentColumn() - 2 == 9) {//最多九列"比较数据"  或者 来自车系比较
                    imgAdd.setImageDrawable(null);
                    imgAdd.setBackgroundDrawable(null);
                    imgAdd.setImageResource(R.drawable.icon_notianjia);
                    imgAdd.setOnClickListener(null);
                } else {
                    imgAdd.setImageResource(comparisonCarItem.getImgBackgroud());
                    imgAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            gotoHere(CarSeriesConfigAcitivity.this, titleData.size() - 2);
                            Toast.makeText(CarSeriesConfigAcitivity.this, "添加数据", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                imgAdd.setVisibility(View.GONE);
                imgAdd.setEnabled(false);
            }
            tvTitle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tvTitle.setPadding(titleLeftPadding, 0, 0, 0);
            return view;
        }

        //表格正文的view，行和列都从0开始，宽度的话在载入的时候，默认会是以标题行各列的宽度，高度的话自适应
        @Override
        public View getTableCellView(int contentRow, int contentColum, View view, ViewGroup parent) {
            TableCellView tableCellView = null;
            if (null == view) {
                tableCellView = new TableCellView();
                view = LayoutInflater.from(CarSeriesConfigAcitivity.this).inflate(R.layout.layout_comparison_header, null);
                tableCellView.flContent = (FrameLayout) view.findViewById(R.id.fl_content);
                tableCellView.tvTitle = (TextView) view.findViewById(R.id.tv_title);
                tableCellView.img_add = ((ImageView) view.findViewById(R.id.img_add));
                tableCellView.img_del = ((ImageView) view.findViewById(R.id.img_del));
                tableCellView.img_rank = ((ImageView) view.findViewById(R.id.iv_item_rank_pic));
                tableCellView.img_add.setVisibility(View.GONE);
                tableCellView.img_del.setVisibility(View.GONE);
                tableCellView.img_rank.setVisibility(View.GONE);
                tableCellView.tvTitle.setMinHeight(cellHieght);
                tableCellView.tvTitle.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tableCellView.tvTitle.setGravity(Gravity.CENTER);
                view.setTag(tableCellView);
            } else {
                tableCellView = (TableCellView) view.getTag();
            }
            ArrayList<ComparisonCarItem> comparisonCarItems = contentAllData.get(contentRow);
            ComparisonCarItem comparisonCarItem = comparisonCarItems.get(contentColum);
            int size = comparisonCarItems.size();
            boolean same = comparisonCarItems.get(0).isSame();

            if (!tableCellView.tvTitle.getText().equals(comparisonCarItem.getName())) {
                tableCellView.tvTitle.setText(comparisonCarItem.getName());
            }

            boolean isFirstColum = contentColum == 0;
            boolean isFirstOrLastColum = isFirstColum || contentColum == size - 1;

            int color = getResources().getColor(isFirstColum ? R.color.black_gray : R.color.text_noraml_color);
            if (tableCellView.tvTitle.getCurrentTextColor() != color) {
                tableCellView.tvTitle.setTextColor(color);
            }


//            int tabColor = getResources().getColor(isFirstOrLastColum || contentRow != 0 ? R.color.text_noraml_color : R.color.red);
//            if (tableCellView.tvTitle.getCurrentTextColor() != tabColor) {
//                tableCellView.tvTitle.setTextColor(tabColor);
//            }

            int resid = isFirstOrLastColum || same ? R.drawable.bg_shape_gray : R.drawable.bg_shape_green;
            if (view.getTag() == null || view.getTag() != Integer.valueOf(resid)) {
                view.setBackgroundResource(resid);
                view.setTag(R.layout.layout_comparison_header, resid);
            }

//            int visibility = isComparsionSame && same ? View.GONE : View.VISIBLE;
//            if (tableCellView.flContent.getVisibility() != visibility) {
//                tableCellView.flContent.setVisibility(visibility);
//            }

            return view;
        }

        @Override
        public View getTableRowTitlrView(int contentRow, View view) {
            TableRowTitlrView tableRowTitlrView = null;
            if (null == view) {
                tableRowTitlrView = new TableRowTitlrView();
                view = LayoutInflater.from(CarSeriesConfigAcitivity.this).inflate(R.layout.layout_comparison_cell_header, null);
                tableRowTitlrView.tvTitle = (TextView) view.findViewById(R.id.tv_title);
                tableRowTitlrView.tvsubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
            } else {
                tableRowTitlrView = (TableRowTitlrView) view.getTag();
            }
            ComparisonCarItem comparisonCarItem = contentAllData.get(contentRow).get(0);
            int visibility = comparisonCarItem.isHeader() ? View.VISIBLE : View.GONE;
            if (visibility != view.getVisibility()) {
                view.setVisibility(visibility);
            }
            if (!tableRowTitlrView.tvTitle.getText().equals(comparisonCarItem.getRowTitle())) {
                tableRowTitlrView.tvTitle.setText(comparisonCarItem.getRowTitle());
            }
            if (!tableRowTitlrView.tvsubTitle.getText().equals(CC)) {
                tableRowTitlrView.tvsubTitle.setText(CC);
            }
            return view;
        }

        @Override
        public View getFooterView(ListView view) {

            View footer = LayoutInflater.from(context).inflate(R.layout.layout_comparison_cell_header, null);
            TextView tvTitle = (TextView) footer.findViewById(R.id.tv_title);
            TextView tvsubTitle = (TextView) footer.findViewById(R.id.tv_subtitle);
            footer.setBackgroundColor(getResources().getColor(R.color.white));
            tvTitle.setText("注:以上仅供参考,请以店内实车为准");
            tvTitle.setPadding(20, 20, 20, 20);
            tvTitle.setTextColor(getResources().getColor(R.color.text_selected));
            tvsubTitle.setText(null);
            return footer;
        }


        @Override
        public Object getItem(int contentRow) {
            return contentAllData.get(contentRow);
        }


        //每一行被点击的时候的回调
        @Override
        public void OnClickContentRowItem(int row, View convertView) {
        }

        class TableCellView {
            FrameLayout flContent;
            TextView tvTitle;
            ImageView img_add;
            ImageView img_del;
            ImageView img_rank;
        }

        class TableRowTitlrView {
            TextView tvTitle;
            TextView tvsubTitle;
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
    }
}
