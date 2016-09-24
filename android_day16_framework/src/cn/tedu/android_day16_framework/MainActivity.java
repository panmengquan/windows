package cn.tedu.android_day16_framework;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView;
	private RequestQueue queue;
	protected MusicsResp resp;
	private MusicAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 控件初始化
		setViews();
		// 加载新歌榜音乐数据
		loadNewMusicList();
	}

	// 加载新歌榜音乐数据
	private void loadNewMusicList() {
		// 1. RequestQueue
		queue = Volley.newRequestQueue(this);
		// 2. StringRequest
		String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0&method=baidu.ting.billboard.billList&format=json&type=1&offset=0&size=50";
		StringRequest req = new StringRequest(url, 
				new Listener<String>() {
					public void onResponse(String response) {
						//在主线程中执行onResponse方法
						//Log.d("inf", response);
						//response就是返回的json格式字符串
						//解析json  封装成List<Music> 
						//设置listView的adapter
						Gson gson = new Gson();
						resp=gson.fromJson(response, MusicsResp.class);
						setAdapter();
					}
				}, new ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Log.e("info", "请求出错:"+error.getMessage());
					}
				});
		// 3. addToQueue
		queue.add(req);
	}

	/**
	 * 设置listView的适配器
	 */
	public void setAdapter(){
		adapter = new MusicAdapter(this, resp.getSong_list(), queue);
		//给listView添加header
		final ImageView imageView = new ImageView(this);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 248);
		imageView.setLayoutParams(params);
		imageView.setImageResource(R.drawable.ic_launcher);
		listView.addHeaderView(imageView);
		listView.setAdapter(adapter);
		//使用volley加载headerView中的图片
		String uri = resp.getBillboard().getPic_s444();
		ImageRequest req = new ImageRequest(uri, 
				new Listener<Bitmap>() {
					public void onResponse(Bitmap response) {
						imageView.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, 
				new ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Log.e("info", "图片加载失败");
					}
				});
		queue.add(req);
	}
	
	private void setViews() {
		listView = (ListView) findViewById(R.id.listView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
