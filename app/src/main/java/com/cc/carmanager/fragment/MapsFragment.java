package com.cc.carmanager.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.cc.carmanager.R;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;

/**
 * Created by chenc on 2017/10/24.
 */
public class MapsFragment extends LazyFragment implements AMap.OnMapLoadedListener,
        AMap.OnCameraChangeListener, AMap.InfoWindowAdapter{

    private BitmapDescriptor initBitmap,smallIdentificationBitmap,bigIdentificationBitmap;//定位圆点、所有标识（车）
    //定位
    //private LocationTask mLocationTask;
    //逆地理编码功能
    //private RegeocodeTask mRegeocodeTask;
    //绘制点标记
    private Marker mInitialMark,tempMark;//可移动、圆点、点击

    private MapView mapView;
    private AMap aMap;
    private UiSettings mUiSettings;
    private boolean mIsFirst = true;

    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.maps_fragment_layout);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initBitmap();
        initMap();
        initLocation();
    }

    private void initBitmap()
    {
        initBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker);
        smallIdentificationBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.stable_cluster_marker_one_normal);
        bigIdentificationBitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.stable_cluster_marker_one_select);
    }

    /**
     * 初始化AMap对象
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        //mUiSettings.setZoomControlsEnabled(false);
        //mUiSettings.setScrollGesturesEnabled(false);
        aMap.setOnMapLoadedListener(this);
        aMap.setOnCameraChangeListener(this);
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
    }


    /**
     * 初始化定位
     */
    private void initLocation() {
//        mLocationTask = LocationTask.getInstance(getApplicationContext());
//        mLocationTask.setOnLocationGetListener(this);
//        mRegeocodeTask = new RegeocodeTask(getApplicationContext());
    }

    private static ArrayList<Marker> markers = new ArrayList<Marker>();
    public void addEmulateData(AMap amap,LatLng center) {
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromResource(R.drawable.stable_cluster_marker_one_normal);

        for (int i = 0; i < 20; i++) {
            double latitudeDelt = (Math.random() - 0.5) * 0.1;
            double longtitudeDelt = (Math.random() - 0.5) * 0.1;
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.anchor(0.5f, 0.5f);
            markerOptions.icon(bitmapDescriptor);
            markerOptions.position(new LatLng(center.latitude + latitudeDelt, center.longitude + longtitudeDelt));
            Marker marker = amap.addMarker(markerOptions);
            markers.add(marker);
        }
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        mapView.onPause();
    }

    @Override
    protected void onResumeLazy() {
        super.onResumeLazy();
        mapView.onResume();
        if (mInitialMark != null) {
            mInitialMark.setToTop();
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        for (Marker marker : markers) {
            marker.remove();
            marker.destroy();
        }
        markers.clear();
        mapView.onDestroy();
        //mLocationTask.onDestroy();
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        if (mIsFirst) {
            addEmulateData(aMap, cameraPosition.target);
            createInitialPosition(cameraPosition.target.latitude, cameraPosition.target.longitude);
            mIsFirst = false;
        }
        if (mInitialMark != null) {
            mInitialMark.setToTop();
        }
    }

    /**
     * 创建初始位置图标
     */
    private void createInitialPosition(double lat, double lng) {
        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(lat, lng));
        markerOptions.icon(initBitmap);
        mInitialMark = aMap.addMarker(markerOptions);
        mInitialMark.setClickable(false);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        Log.e("Car", "getInfoWindow");
        View infoWindow = getActivity().getLayoutInflater().inflate(
                R.layout.info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }
    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        TextView tv_time_info = (TextView)view.findViewById(R.id.tv_time_info);
        TextView tv_distance =(TextView) view.findViewById(R.id.tv_distance);
        tv_time.setText("test");
        tv_time_info.setText("test");
        tv_distance.setText("test");
    }

    @Override
    public View getInfoContents(Marker marker) {
        Log.e("Car", "getInfoContents");
        return null;
    }


    private void startAnim(Marker marker) {
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f);
        anim.setDuration(300);
        marker.setAnimation(anim);
        marker.startAnimation();
    }

    // 定义 Marker 点击事件监听
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {

        // marker 对象被点击时回调的接口
        // 返回 true 则表示接口已响应事件，否则返回false
        @Override
        public boolean onMarkerClick(final Marker marker) {
            Log.e("TAG", "点击的Marker");
            if(tempMark!=null)
            {
                tempMark.setIcon(smallIdentificationBitmap);
                tempMark = null;
            }
            startAnim(marker);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        tempMark = marker;
                        marker.setIcon(bigIdentificationBitmap);
                        marker.setPosition(marker.getPosition());
//                        Intent intent = new Intent(TestActivity.this, RouteActivity.class);
//                        intent.putExtra("start_lat", mPositionMark.getPosition().latitude);
//                        intent.putExtra("start_lng", mPositionMark.getPosition().longitude);
//                        intent.putExtra("end_lat", marker.getPosition().latitude);
//                        intent.putExtra("end_lng", marker.getPosition().longitude);
//                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
    };
}
