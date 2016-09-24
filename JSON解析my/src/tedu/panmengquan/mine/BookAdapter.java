package tedu.panmengquan.mine;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {
	private Context context;
	private List<Book> books;
	private LayoutInflater inflater;
	
	public BookAdapter(Context context, List<Book> books) {
		super();
		this.context = context;
		this.books = books;
		this.inflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return books.size();
	}

	@Override
	public Book getItem(int position) {
		return books.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.load_music_item,null);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvCatlog = (TextView) convertView.findViewById(R.id.tvCatalog);
			convertView.setTag(holder);
		}
		holder=(ViewHolder) convertView.getTag();
		Book b = getItem(position);
		holder.tvTitle.setText(b.getTitle());
		holder.tvCatlog.setText(b.getCatalog());
		return convertView;
	}
	class ViewHolder{
		//ImageView ivAlbum;
		TextView tvTitle;
		TextView tvCatlog;
	}

}
