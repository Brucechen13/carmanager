package com.cc.carmanager.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cc.carmanager.MyApplication;
import com.cc.carmanager.R;
import com.cc.carmanager.bean.CarMapMakersBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.cc.carmanager.util.Utils;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by chenc on 2018/3/3.
 */

public class MapFragment extends LazyFragment implements BaiduMap.OnMapClickListener{
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    public LocationClient mLocationClient = null;
    private BitmapDescriptor initBitmap, smallIdentificationBitmap, bigIdentificationBitmap;//定位圆点、所有标识（车）

    private boolean isFirst = true;
    private MyLocationListener myListener = new MyLocationListener();

    private CompanyPlaceView bottom_layout;

    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.maps_fragment_layout);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mMapView.showScaleControl(false);
        mBaiduMap.getUiSettings().setRotateGesturesEnabled(false);
        mBaiduMap.getUiSettings().setOverlookingGesturesEnabled(false);
        mBaiduMap.setOnMapClickListener(this);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        initMapLocation();
        initBitmap();
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, false, initBitmap);
        mBaiduMap.setMyLocationConfiguration(config);
        mLocationClient.start();
        // 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
        mBaiduMap.setOnMarkerClickListener(markerClickListener);

        addEmulateData();

        bottom_layout = new CompanyPlaceView(findViewById(R.id.bottom_layout));
    }


    private void initBitmap() {
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        Bitmap bitmap = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker), 50, 50);
        initBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
        bitmap = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.stable_cluster_marker_one_normal), 150, 150);
        smallIdentificationBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
        bitmap = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.stable_cluster_marker_one_select), 250, 250);
        bigIdentificationBitmap = BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void initMapLocation() {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；

        option.setScanSpan(1000);

        option.setOpenGps(true);

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(true);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    private static List<OverlayOptions> markers = new ArrayList<OverlayOptions>();

    public void addEmulateData() {
        VolleyInstance.getVolleyInstance().startRequest(NetUrlsSet.URL_CAR_MAP, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarMapMakersBean mRecommendBean = gson.fromJson(resultStr, CarMapMakersBean.class);
                if (mRecommendBean.isSuccess()) {
//                    Bitmap bitmap = zoomImg(BitmapFactory.decodeResource(getResources(), R.drawable.stable_cluster_marker_one_normal), 100, 100);//
//                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                    for (CarMapMakersBean.CarMapMakerBean bean : mRecommendBean.getData()) {
                        LatLng point1 = new LatLng(bean.getLatitude(), bean.getLongitude());
                        //创建OverlayOptions属性
                        Bundle bundle = new Bundle();
                        bundle.putString("place", bean.getLocation());
                        bundle.putString("openGrade", bean.getOpenGrade());
                        bundle.putString("useType", bean.getUseType());
                        OverlayOptions option1 = new MarkerOptions()
                                .position(point1).anchor(0.5f, 0.5f)
                                .icon(smallIdentificationBitmap).extraInfo(bundle);
                        //将OverlayOptions添加到list
                        markers.add(option1);
                    }
                    //在地图上批量添加
                    mBaiduMap.addOverlays(markers);
                } else {
                    ToastUtils.makeShortText("新闻加载失败", MapFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });

    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        //获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        //计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        //取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        //得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        mMapView.onPause();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        mMapView.onResume();
    }

    private LatLng userLoc;

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        bottom_layout.hideUp();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            userLoc = new LatLng(latitude, longitude);

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);

            if (isFirst) {
                //设定中心点坐标
                LatLng center = new LatLng(latitude, longitude);
                //定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(center)
                        .zoom(15)
                        .build();
                //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);
                isFirst = false;
            }
        }
    }

    private Marker tempMark;
    BaiduMap.OnMarkerClickListener markerClickListener = new BaiduMap.OnMarkerClickListener() {

        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(final Marker marker) {
            Log.e("TAG", "点击的Marker");
            if (tempMark != null) {
                tempMark.setIcon(smallIdentificationBitmap);
                tempMark = null;
            }
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(marker.getPosition())
                    .zoom(15)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mBaiduMap.setMapStatus(mMapStatusUpdate);

            bottom_layout.showUp(marker.getExtraInfo(), marker.getPosition());
            //showInfoWindow(marker);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        tempMark = marker;
                        marker.setIcon(bigIdentificationBitmap);
                        marker.setPosition(marker.getPosition());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
    };

    private void showInfoWindow(Marker marker) {
        LatLng llInfo = marker.getPosition();
        InfoWindow infowindow = new InfoWindow(getInfoWindoView(marker, marker.getPosition()), llInfo, 0);
        //将信息窗口显示出来
        mBaiduMap.showInfoWindow(infowindow);
    }

    private View infoView;

    private View getInfoWindoView(final Marker marker, LatLng posi) {
        if (null == infoView) {
            infoView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
        }
        TextView tvTitle = infoView.findViewById(R.id.tv_distance);
        TextView tvPlace = infoView.findViewById(R.id.tv_place);


        int distance = (int) DistanceUtil.getDistance(userLoc, posi);
        String ds = "";
        if (distance < 1000) {
            ds = String.format(Locale.CHINA, "%d米", distance);
        } else {
            ds = String.format(Locale.CHINA, "%d公里", distance / 1000);
        }
        String place = marker.getExtraInfo().getString("place");
        Log.e("car", place);
        tvPlace.setText(place);
        tvTitle.setText(ds);

        return infoView;
    }

    public class CompanyPlaceView{
        TextView company_name;
        TextView company_place;
        TextView company_dis;
        TextView tvLine;

        View v;

        private LatLng posi;
        private String location;

        CompanyPlaceView(View view) {
            v = view;
            company_name = view.findViewById(R.id.company_name);
            company_place = view.findViewById(R.id.company_place);
            company_dis = view.findViewById(R.id.tv_distance);
            tvLine = view.findViewById(R.id.contact_line);
        }

        public void showUp(Bundle bundle, LatLng posi){
            v.setVisibility(View.VISIBLE);
            //company_name.setText(bundle.getString("name"));
            company_place.setText(bundle.getString("place"));
            company_name.setText(bundle.getString("openGrade"));
            company_dis.setText(bundle.getString("useType"));
            location = bundle.getString("place");
//            int distance = (int) DistanceUtil.getDistance(userLoc, posi);
//            String ds = "";
//            if (distance < 1000) {
//                ds = String.format(Locale.CHINA, "距您%dm", distance);
//            } else {
//                ds = String.format(Locale.CHINA, "距您%dkm", distance / 1000);
//            }
//            company_dis.setText(ds);
            this.posi = posi;
        }

        public void hideUp(){
            v.setVisibility(View.GONE);
        }


    }
}
