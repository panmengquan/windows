package cn.tedu.android_day16_framework;

import java.util.List;

/**
 * 封装音乐列表的查询结果
 */
public class MusicsResp {
	private List<Music> song_list;
	private Billboard billboard;
	private int error_code;

	public MusicsResp() {
		// TODO Auto-generated constructor stub
	}

	public MusicsResp(List<Music> song_list, Billboard billboard, int error_code) {
		super();
		this.song_list = song_list;
		this.billboard = billboard;
		this.error_code = error_code;
	}

	public List<Music> getSong_list() {
		return song_list;
	}

	public void setSong_list(List<Music> song_list) {
		this.song_list = song_list;
	}

	public Billboard getBillboard() {
		return billboard;
	}

	public void setBillboard(Billboard billboard) {
		this.billboard = billboard;
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

}
