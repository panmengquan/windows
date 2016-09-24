package cn.tedu.media_player_v4.service;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import cn.tedu.media_player_v4.util.GlobalConsts;

/** �������ֵ���� */
public class PlayMusicService extends Service{
	private MediaPlayer player = new MediaPlayer();
	private boolean isLoop = true;
	
	/**
	 * ��Service����ʱִ��1��
	 */
	public void onCreate() {
		super.onCreate();
		//��mediaplayer��Ӽ���
		player.setOnPreparedListener(new OnPreparedListener() {
			//������׼����Ϻ�ִ�еļ�������
			public void onPrepared(MediaPlayer mp) {
				player.start();
				//�����Զ���㲥  ���ֿ�ʼ����
				Intent intent = new Intent(GlobalConsts.ACTION_MUSIC_STARTED);
				sendBroadcast(intent);
			}
		});
		//���������߳�  ÿ1s��һ�θ��½��ȵĹ㲥
		//�̲߳�������̫��  ������onCreate������
		//��֤run���� ��������ִ����� ��ֹ�ڴ�й©
		new Thread(){
			public void run() {
				while(isLoop){
					try {
						Thread.sleep(1000);
						//���������ڲ���ʱ �����Զ���㲥
						if(player.isPlaying()){
							int total=player.getDuration();
							int progress=player.getCurrentPosition();
							Intent intent = new Intent(GlobalConsts.ACTION_UPDATE_MUSIC_PROGRESS);
							intent.putExtra("total", total);
							intent.putExtra("progress", progress);
							sendBroadcast(intent);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void onDestroy() {
		//�ͷ�mediaplayer
		isLoop = false;
		player.release(); 
		super.onDestroy();
	}
	
	public IBinder onBind(Intent intent) {
		return new MusicBinder();
	}
	
	
	public class MusicBinder extends Binder{
		/**
		 * ��ת��position��λ�� ��������/��ͣ
		 */
		public void seekTo(int position){
			player.seekTo(position);
		}
		
		/**  �������ֵĽӿڷ���  */
		public void playMusic(String url){
			try {
				player.reset();
				player.setDataSource(url);
				player.prepareAsync(); //�첽��׼��
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * ��ͣ�򲥷�
		 */
		public void startOrPause() {
			if(player.isPlaying()){
				player.pause();
			}else{
				player.start();
			}
		}
	}
	
}
