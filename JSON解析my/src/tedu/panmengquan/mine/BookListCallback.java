package tedu.panmengquan.mine;

import java.util.List;

public interface BookListCallback {
	/**
	 * 当列表加载完毕后将会调用的回调方法
	 * @param musics
	 */
	void onBookListLoaded(List<Book> books);
}
