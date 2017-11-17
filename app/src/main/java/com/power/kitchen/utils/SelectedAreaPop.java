package com.power.kitchen.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.power.kitchen.R;
import com.power.kitchen.bean.Area;
import com.power.kitchen.bean.CitysBean;
import com.power.kitchen.bean.ResultBean;
import com.power.kitchen.callback.JsonCallback;
import com.power.kitchen.weight.widget.OnWheelChangedListener;
import com.power.kitchen.weight.widget.WheelView;
import com.power.kitchen.weight.widget.adapters.ArrayWheelAdapter;

import org.json.JSONObject;

public class SelectedAreaPop extends PopupWindow implements OnClickListener, OnWheelChangedListener {

	private Context context;
	
	private int layout;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private TextView mBtnConfirm;
	private TextView btn_qx;
	private List<Area> areas ;
	private String area_Sid,area_Cid;
	private List<Area> Careas;
	private List<Area> Dareas;
	private AreaClickListener areaClickListener;

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName ="";
	
	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode ="";
	
	public SharedPreferences sp;
	
	private String sheng_id,shi_id,qu_id;
	
	
	public SelectedAreaPop(Context context,int layout,List<Area> areas ){
		this.context=context;
		this.layout=layout;
		this.areas=areas;//全省
		initView();
	}
	
	
	private  void initView(){
		View view = View.inflate(context, layout, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		setContentView(view);
		setUpViews(view);
		setUpListener();
		setUpData();
		
		
		setAnimationStyle(R.style.popwin_anim_style);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setBackgroundDrawable(new ColorDrawable(view.getResources().getColor(android.R.color.transparent)));
		setFocusable(true);
		setOutsideTouchable(true);
		update();
		
	}
	private void setUpViews(View view) {
		mViewProvince = (WheelView)view. findViewById(R.id.id_province);
		mViewCity = (WheelView) view.findViewById(R.id.id_city);
		mViewDistrict = (WheelView)view. findViewById(R.id.id_district);
		mBtnConfirm = (TextView) view.findViewById(R.id.btn_confirm);
		btn_qx=(TextView) view.findViewById(R.id.btn_qx);
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	// 添加onclick事件
    	mBtnConfirm.setOnClickListener(this);
    	btn_qx.setOnClickListener(this);
    }
	
	private void setUpData() {
//		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter(context, areas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName=Dareas.get(newValue).name;
			qu_id=Dareas.get(newValue).area_id;
		}
	}
	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		area_Cid = Careas.get(pCurrent).area_id;
		mCurrentCityName=Careas.get(pCurrent).name;
		shi_id=Careas.get(pCurrent).area_id;
		getQuData();
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		area_Sid = areas.get(pCurrent).area_id;
		mCurrentProviceName=areas.get(pCurrent).name;
		sheng_id=areas.get(pCurrent).area_id;
		getCityData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			showSelectedResult();
			break;
		case R.id.btn_qx:
			areaClickListener.onAreaClearListener();
			break;
		default:
			break;
		}
	}
	
	private void showSelectedResult() {
//		Toast.makeText(context, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
//				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
		StringBuffer sb = new StringBuffer();
		sb.append(mCurrentProviceName+" "+mCurrentCityName+" "+mCurrentDistrictName);
		areaClickListener.onAreaClickListener(sb.toString(), sheng_id, shi_id, qu_id);

	}


	public interface AreaClickListener{
		void onAreaClickListener(String areaName, String sheng_id, String shi_id, String qu_id);
		void onAreaClearListener();
	}
	
	public void setOnAreaClickListener(AreaClickListener areaClickListener){
		this.areaClickListener=areaClickListener;
	}

	private void getCityData() {
		Map<String, String> map = new HashMap<>();
		map.put("app_id",SPUtils.getInstance().getString("app_id",""));
		map.put("token",SPUtils.getInstance().getString("token",""));
		map.put("id",SPUtils.getInstance().getString("id",""));
		map.put("area_id",area_Sid);
		JSONObject values = new JSONObject(map);
		HttpParams params = new HttpParams();
		params.put("data",values.toString());

		OkGo.<CitysBean>post(Urls.area_lists)
				.tag(this)
				.params(params)
				.execute(new JsonCallback<CitysBean>(CitysBean.class) {
					@Override
					public void onSuccess(Response<CitysBean> response) {
						CitysBean citysBean = response.body();
						if (TextUtils.equals("1",citysBean.getStatus())){
							List<Area> areas = new ArrayList<Area>();
							for (int i = 0; i < citysBean.getData().size(); i++) {
								Area area = new Area();
								area.area_id = citysBean.getData().get(i).getArea_id();
								area.name = citysBean.getData().get(i).getName();
								area.up_id = citysBean.getData().get(i).getUp_id();
								areas.add(area);
							}
							Careas = areas;
							mViewCity.setViewAdapter(new ArrayWheelAdapter(context, Careas));
							mViewCity.setCurrentItem(0);
							updateAreas();
						}
					}
				});
	}

	private void getQuData() {
		Map<String, String> map = new HashMap<>();
		map.put("app_id",SPUtils.getInstance().getString("app_id",""));
		map.put("token",SPUtils.getInstance().getString("token",""));
		map.put("id",SPUtils.getInstance().getString("id",""));
		map.put("area_id",area_Cid);
		JSONObject values = new JSONObject(map);
		HttpParams params = new HttpParams();
		params.put("data",values.toString());

		OkGo.<CitysBean>post(Urls.area_lists)
				.tag(this)
				.params(params)
				.execute(new JsonCallback<CitysBean>(CitysBean.class) {
					@Override
					public void onSuccess(Response<CitysBean> response) {
						CitysBean citysBean = response.body();
						if (TextUtils.equals("1",citysBean.getStatus())){
							List<Area> areas = new ArrayList<Area>();
							for (int i = 0; i < citysBean.getData().size(); i++) {
								Area area = new Area();
								area.area_id = citysBean.getData().get(i).getArea_id();
								area.name = citysBean.getData().get(i).getName();
								area.up_id = citysBean.getData().get(i).getUp_id();
								areas.add(area);
							}
							Dareas = areas;
							mViewDistrict.setViewAdapter(new ArrayWheelAdapter(context, Dareas));
							mViewDistrict.setCurrentItem(0);
						}
					}
				});
	}
	
}
