package com.cc.carmanager.fragment;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.cc.carmanager.R;
import com.cc.carmanager.util.ToastUtils;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

import me.weyye.hipermission.PermissionItem;
/**
 * Created by chenc on 2017/10/24.
 */
public class MapsFragment extends LazyFragment implements AMap.OnMapLoadedListener,
        AMap.OnCameraChangeListener, AMap.InfoWindowAdapter, AMapLocationListener {

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
    private static String TAG = "MAPS";

    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.maps_fragment_layout);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写


//        List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
//        permissonItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "定位", R.drawable.permission_ic_location));
//        HiPermission.create(MapsFragment.this.getActivity())
//                .permissions(permissonItems)
//                .checkMutiPermission(new PermissionCallback() {
//                    @Override
//                    public void onClose() {
//                        Log.i(TAG, "onClose");
//                        ToastUtils.makeShortText("用户关闭权限申请", MapsFragment.this.getActivity());
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        ToastUtils.makeShortText("所有权限申请完成", MapsFragment.this.getActivity());
//                    }
//
//                    @Override
//                    public void onDeny(String permisson, int position) {
//                        Log.i(TAG, "onDeny");
//                    }
//
//                    @Override
//                    public void onGuarantee(String permisson, int position) {
//                        Log.i(TAG, "onGuarantee");
//                    }
//                });
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
        mUiSettings.setRotateGesturesEnabled(false);//禁止地图旋转手势
        mUiSettings.setTiltGesturesEnabled(false);//禁止倾斜手势
        mUiSettings.setMyLocationButtonEnabled(true);
        //mUiSettings.setZoomControlsEnabled(false);
        //mUiSettings.setScrollGesturesEnabled(false);
        aMap.setOnMapLoadedListener(this);
        aMap.setOnCameraChangeListener(this);
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式


        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }

    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    /**
     * 初始化定位
     */
    private void initLocation() {
        //声明mlocationClient对象
        mlocationClient = new AMapLocationClient(this.getActivity());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//启动定位
        mlocationClient.startLocation();
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

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
