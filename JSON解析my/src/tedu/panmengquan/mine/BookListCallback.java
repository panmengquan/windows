package tedu.panmengquan.mine;

import java.util.List;

public interface BookListCallback {
	/**
	 * ���б������Ϻ󽫻���õĻص�����
	 * @param musics
	 */
	void onBookListLoaded(List<Book> books);
}
