package tedu.panmengquan.mine;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;
public class Model {

	public void loadBook(final BookListCallback callback) {
		AsyncTask<String, String, List<Book>> task = new AsyncTask<String, String, List<Book>>(){
			protected List<Book> doInBackground(String... arg0) {
				String urll = "http://apis.juhe.cn/goodbook/query?key=0e0da506c70830aef8b324613795c943&catalog_id=246";
						URL url;
						try {
							url = new URL(urll);
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							conn.setRequestMethod("GET");
							InputStream is = conn.getInputStream();
							BufferedReader reader = new BufferedReader(new InputStreamReader(is));
							StringBuilder sb = new StringBuilder();
							String line = null;
							while((line=reader.readLine())!=null){
								sb.append(line);
							}
							String json = sb.toString();
							Log.i("info", "json="+json);
//
//							//½âÎöjson  { songurl:{url:[{},{}]}, songinfo:{} }  
//							JSONObject obj = new JSONObject(json);
//							JSONArray urlAry = obj.getJSONObject("songurl").getJSONArray("url");	
//							
							
					JSONObject obj = new JSONObject(json);
					JSONArray ary = obj.getJSONObject("result").getJSONArray("data");

					List<Book> books = new ArrayList<Book>();
							for(int i=0; i<ary.length(); i++){
								 obj = ary.getJSONObject(i);
								Book b = new Book();
								b.setTitle(obj.getString("title"));
								b.setTitle(obj.getString("title"));
								b.setCatalog(obj.getString("catalog"));
								books.add(b);
							}
							return books;
						} catch (Exception e) {
							e.printStackTrace();
						}
				return null;
			}
			@Override
			protected void onPostExecute(List<Book> books) {
				callback.onBookListLoaded(books);
			}
		
		};
		task.execute();
	}
	
}
