package com.parkingsystem.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.parkingsystem.R;
import com.parkingsystem.utils.overlayutil.DrivingRouteOverlay;
import com.parkingsystem.utils.overlayutil.OverlayManager;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements BaiduMap.OnMapClickListener,
        OnGetRoutePlanResultListener {

    private int isFirsIn = 0;

    public LocationClient mLocationClient;

    private MapView mMapView = null;

    private BaiduMap mBaiduMap = null;

    private Button locateToLocation;

    private Button startNavigation;


    private BDLocationListener mListener = new MyLocationListener();

    private List<String> permissionList = new ArrayList<>();

    private Marker mMarker;

    //定位相关
    private double mLatitude;

    private double mLongitude;

    private LatLng mLastLocationData;

    private LatLng mDestLocationData;

    // 路线规划
    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用

    RouteLine route = null; //路线

    OverlayManager routeOverlay = null; // 该类提供一个能够显示和管理多个overlay的基类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_navigation);

        mMapView = (MapView) findViewById(R.id.map_view);

        //得到BaiduMap的实例
        mBaiduMap = mMapView.getMap();
        //设置Baidu的地图类型，具有MAP_TYPE_NONE、MAP_TYPE_NORMAL和MAP_TYPE_SATELLITE
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //必须主动开启定位功能
        mBaiduMap.setMyLocationEnabled(true);

        //创建LocationClient
        mLocationClient = new LocationClient(getApplicationContext());

        //注册一个回调用的Listener
        mLocationClient.registerLocationListener(mListener);

        locateToLocation = (Button) findViewById(R.id.bt_my_location);
        startNavigation = (Button) findViewById(R.id.bt_real_nav);

        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mDestLocationData = latLng;
                addDestInfoOverlay(latLng);
            }
        });

        //检查权限
        checkPermissionState();
        //配置一些参数
        initLocationClient();
        //定位现在的位置
        locateMyLocation();
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

    }

    private void addDestInfoOverlay(LatLng latLng) {
        mBaiduMap.clear();
    }

    /**
     * 判断是否有定位所需权限，没有的话，需要运行时申请
     */
    private void checkPermissionState() {

        if (ContextCompat.checkSelfPermission(NavigationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(NavigationActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(NavigationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(NavigationActivity.this, permissions, 1);
        } else {
            //调用LocationClient的start接口，接下来SDK将完成发送定位请求的工作
            mLocationClient.start();
        }
    }

    /**
     * 设置地图基本配置
     */
    private void initLocationClient() {
        //创建option实例
        //option有很多默认设置，可以按需变更
        LocationClientOption option = new LocationClientOption();

        //设置定位模式，默认高精度
        //有高精度，低功耗，仅设备等模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //设置返回的定位结果坐标系
        //默认gcj02
        option.setCoorType("bd09ll");

        //设置定位间隔，默认为0，即只定位1次
        //此处设置定位请求的间隔大于等于10000ms
        option.setScanSpan(1000);

        //设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //设置是否使用gps，默认false,
        option.setOpenGps(true);

        //设置是否当GPS有效时，是否按照1次/s的频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //设置是否需要位置语义化结果，默认false
        //设置为true后，可以在BDLocation.getLocationDescribe里得到类似于“在北京天安门附近”的结果
        option.setIsNeedLocationDescribe(true);

        //设置是否需要POI(Point of Interest，信息点)，默认false，同样可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //定位SDK内部是一个SERVICE，并放到了独立进程
        //此处设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);

        //设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        //为LocationClient配置option
        mLocationClient.setLocOption(option);
    }

    //MapView与Activity的生命周期匹配
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);

        mMapView.onDestroy();
    }

    /**
     * 定位到我的位置
     */
    private void locateMyLocation() {

        locateToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(mLatitude, mLongitude);
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(mapStatusUpdate);
            }
        });
    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            route = result.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
            routeOverlay = overlay;
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result.getRouteLines().get(0));  //设置路线数据
            overlay.addToMap(); //将所有overlay添加到地图中
            overlay.zoomToSpan();//缩放地图
        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };

    /**
     * 发起路线规划搜索示例
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
        route = null;
        // 设置起终点信息，对于tranist search 来说，城市名无意义
        PlanNode stNode = PlanNode.withLocation(new LatLng(mLatitude, mLongitude));
        PlanNode enNode = PlanNode.withLocation(new LatLng(114.935048,25.860444));

        // 实际使用中请对起点终点城市进行正确的设定

            //驾车行驶
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode).to(enNode));

    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }


    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    /**
     * 用于显示一条驾车路线的overlay，自3.4.0版本起可实例化多个添加在地图中显示，当数据中包含路况数据时，则默认使用路况纹理分段绘制
     */
    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);

        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        }
    }


    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //BDLocation中含有大量的信息，由之前配置的option决定
            //LocType定义了结果码，例如161表示网络定位成功
            //具体参考sdk文档
            Log.d("ZJTest", "onReceiveLocation: " + bdLocation.getLocType());

            //我在这里利用经度、纬度信息构建坐标点
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();
            LatLng point = new LatLng(mLatitude,
                    mLongitude);



            //创建一个新的MapStatus
            MapStatus mapStatus = new MapStatus.Builder()
                    //定位到定位点
                    .target(point)
                    //决定缩放的尺寸
                    .zoom(16)
                    .build();

            if (isFirsIn != 3) {
                //利用MapStatus构建一个MapStatusUpdate对象
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
                //更新BaiduMap，此时BaiduMap的界面就会从初始位置（北京），移动到定位点
                mBaiduMap.setMapStatus(mapStatusUpdate);
                isFirsIn++;
            }


            //得到定位使用的图标
            Bitmap origin = BitmapFactory.decodeResource(
                    getResources(), R.drawable.before);

            //我自己的图标图片太大了，因此这里做了一下缩放
            Matrix matrix = new Matrix();
            matrix.postScale((float) 0.3, (float) 0.3);
            Bitmap resize = Bitmap.createBitmap(origin, 0, 0,
                    origin.getWidth(), origin.getHeight(),
                    matrix, true);

            //重新构建定位图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromBitmap(resize);

            //利用定位点信息和图标，构建MarkerOption，用于在地图上添加Marker
            MarkerOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            //设置Marker的动画效果，我选的是“生长”
            option.animateType(MarkerOptions.MarkerAnimateType.grow);

            //因为我选择的是不断更新位置，
            //因此，如果之前已经叠加过图标，先移除
            if (mMarker != null) {
                mMarker.remove();
            }

            //在地图上添加Marker，并显示
            mMarker = (Marker)(mBaiduMap.addOverlay(option));
        }

        //这个接口主要返回连接网络的类型
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }
}