package tedu.panmengquan.mine;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listview;
	private BookAdapter adapter;
	private Model modle;
	private List<Book> books;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (ListView) findViewById(R.id.listView1);
		modle = new Model();
		modle.loadBook(new BookListCallback() {
			public void onBookListLoaded(List<Book> books) {
				MainActivity.this.books = books;
				Log.i("info", "books="+books);
				setAdapter();
			}
		});
	}

	private void setAdapter() {
		adapter = new BookAdapter(this,books);
		listview.setAdapter(adapter);
	}

}
