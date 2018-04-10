package com.cc.carmanager.fragment.cars;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PatternMatcher;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;

import com.baidu.location.Address;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.cc.carmanager.R;
import com.cc.carmanager.adapt.CarsCompanyAdapter;
import com.cc.carmanager.bean.CarsCompanyBean;
import com.cc.carmanager.net.VolleyInstance;
import com.cc.carmanager.net.VolleyResult;
import com.cc.carmanager.util.NetUrlsSet;
import com.cc.carmanager.util.ToastUtils;
import com.google.gson.Gson;
import com.shizhefei.fragment.LazyFragment;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chenc on 2017/12/30.
 */

public class CarsCompanyFragment extends LazyFragment {
    private ArrayList<CarsCompanyBean.CarCompanyBean> datas = new ArrayList<>();
    private CarsCompanyAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private int type = 4;
    private int carId;
    private String province, city;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_recycle_fragment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);//这里用线性显示 类似于listview
        mAdapter = new CarsCompanyAdapter(this.getActivity(), datas, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        getLocation();

        Bundle bundle = getArguments();
        type = bundle.getInt("position") + 1;
        carId = bundle.getInt("carId");
    }

    private void getData() {
        datas.clear();
        String url_news = String.format(Locale.CHINA, type==4?NetUrlsSet.URL_CAR_SELLER:NetUrlsSet.URL_CAR_REPAIR, carId, URLEncoder.encode(province), URLEncoder.encode(city));

        VolleyInstance.getVolleyInstance().startRequest(url_news, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Gson gson = new Gson();
                CarsCompanyBean mRecommendBean = gson.fromJson(resultStr, CarsCompanyBean.class);
                if (mRecommendBean.isSuccess()) {
                    datas.addAll(mRecommendBean.getData());
                } else {
                    ToastUtils.makeShortText("未查询到商家", CarsCompanyFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    public void updateVersion(String wd, String jd) {
        String path = "http://api.map.baidu.com/geocoder?output=json&location=" + wd + "," + jd + "&key=gf0YBWhrMVmRG9tvaEYvh5IbzApnkcXF";
        VolleyInstance.getVolleyInstance().startRequest(path, new VolleyResult() {
            @Override
            public void success(String resultStr) {
                Log.d("地址为", resultStr);
                Gson gson = new Gson();

                ReveseGeoCoderInfo info = gson.fromJson(resultStr,ReveseGeoCoderInfo.class);
                if(info.getStatus().equals("OK")){
                    city = info.getResult().getAddressComponent().getCity();
                    province = info.getResult().getAddressComponent().getProvince();
                    getData();
                }else{
                    ToastUtils.makeShortText("新闻加载失败", CarsCompanyFragment.this.getContext());
                }
            }

            @Override
            public void failure() {
                Log.d("aaa", "推荐界面下的推荐网络数据解析失败");
            }
        });
    }


    public void getLocation() {
        String locationProvider;
        //获取地理位置管理器
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().getApplication().LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            ToastUtils.makeShortText("没有可用的位置提供器", CarsCompanyFragment.this.getContext());
            return;
        }
        //获取Location
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ToastUtils.makeShortText("未通过位置授权", CarsCompanyFragment.this.getContext());
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            showLocation(location);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
    }

    /**
     * 显示地理位置经度和纬度信息
     *
     * @param location
     */
    private void showLocation(Location location) {
        String locationStr = "纬度：" + location.getLatitude() + "\n"
                + "经度：" + location.getLongitude();
        updateVersion(location.getLatitude() + "", location.getLongitude() + "");
    }

    /**
     * LocationListern监听器
     * 参数：地理位置提供器、监听位置变化的时间间隔、位置变化的距离间隔、LocationListener监听器
     */

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };

    class ReveseGeoCoderInfo{
        private String status;
        private ReveseGeoCoderRes result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ReveseGeoCoderRes getResult() {
            return result;
        }

        public void setResult(ReveseGeoCoderRes result) {
            this.result = result;
        }
    }
    class ReveseGeoCoderRes{
        private AddressComponent addressComponent;

        public AddressComponent getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }
    }
    class AddressComponent{
        private String city;
        private String province;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }

}
