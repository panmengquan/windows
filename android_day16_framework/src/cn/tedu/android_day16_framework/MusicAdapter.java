package cn.tedu.android_day16_framework;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter{
	private Context context;
	private List<Music> musics;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	
	public MusicAdapter(Context context, List<Music> musics, RequestQueue queue) {
		this.context = context;
		this.musics = musics;
		this.inflater = LayoutInflater.from(context);
		//初始化ImageLoader
		//imageLoader = new ImageLoader(queue, new SoftReferenceImageCacheImpl());
		imageLoader = new ImageLoader(queue, new LruImageCacheImpl());
	}
	
	class SoftReferenceImageCacheImpl implements ImageCache{
		private HashMap<String, SoftReference<Bitmap>> cacheMap = new HashMap<String, SoftReference<Bitmap>>();

		public Bitmap getBitmap(String url) {
			SoftReference<Bitmap> ref = cacheMap.get(url);
			if(ref!=null){
				Bitmap b = ref.get();
				return b;
			}
			return null;
		}
		public void putBitmap(String url, Bitmap bitmap) {
			cacheMap.put(url, new SoftReference<Bitmap>(bitmap));
		}
	}
	
	public class LruImageCacheImpl implements ImageCache {  
		  
	    private LruCache<String, Bitmap> mCache;  
	  
	    public LruImageCacheImpl() {  
	        int maxSize = 10 * 1024 * 1024;  
	        mCache = new LruCache<String, Bitmap>(maxSize) {  
	            @Override  
	            protected int sizeOf(String key, Bitmap bitmap) {  
	                return bitmap.getRowBytes() * bitmap.getHeight();  
	            }  
	        };  
	    }  
	  
	    @Override  
	    public Bitmap getBitmap(String url) {  
	        return mCache.get(url);  
	    }  
	  
	    @Override  
	    public void putBitmap(String url, Bitmap bitmap) {  
	        mCache.put(url, bitmap);  
	    }  
	  
	}  
	

	@Override
	public int getCount() {
		return musics.size();
	}

	@Override
	public Music getItem(int position) {
		return musics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_lv_music, null);
			holder = new ViewHolder();
			holder.ivAlbum = (ImageView) convertView.findViewById(R.id.ivAlbum);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvSinger = (TextView) convertView.findViewById(R.id.tvSinger);
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		//控件的赋值
		Music m = getItem(position);
		holder.tvTitle.setText(m.getTitle());
		holder.tvSinger.setText(m.getAuthor());
		//使用volley异步加载图片
		ImageListener listener = ImageLoader.getImageListener(holder.ivAlbum, R.drawable.ic_launcher, R.drawable.ic_launcher);
		imageLoader.get(m.getPic_small(), listener);
		return convertView;
	}
	
	
	class ViewHolder{
		ImageView ivAlbum;
		TextView tvTitle;
		TextView tvSinger;
	}

}
