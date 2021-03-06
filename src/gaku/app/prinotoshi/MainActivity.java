package gaku.app.prinotoshi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	public ImageView item0;
	public TextView name;
	public TextView desc;
	public TextView unlock;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SurfaceView のインスタンスを実体化し、ContentView としてセットする
        //SurfaceViewTest surfaceView = new SurfaceViewTest(this);
        //PrinSurface surfaceView = new PrinSurface(this);
        //setContentView(surfaceView);// タイトルバーを非表示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.v("create","create");
        item0 = (ImageView)findViewById(R.id.item0);
        name = (TextView)findViewById(R.id.name);
        desc = (TextView)findViewById(R.id.desc);
        unlock = (TextView)findViewById(R.id.unlock);
    }

    public void result(View view){
    	Intent intent = new Intent(this,Result.class);
    	startActivity(intent);
    }

    public void select(View view){
    	switch(view.getId()){
    	case R.id.item1:
    		item0.setImageResource(R.drawable.muteki5);
    		name.setText(R.string.item1);
    		desc.setText(R.string.desc1);
    		unlock.setText(R.string.unlock1);
    		break;
    	case R.id.item2:
    		item0.setImageResource(R.drawable.purin5);
    		name.setText(R.string.item2);
    		desc.setText(R.string.desc2);
    		unlock.setText(R.string.unlock2);
    		break;
    	case R.id.item3:
    		item0.setImageResource(R.drawable.double1);
    		name.setText(R.string.item3);
    		desc.setText(R.string.desc3);
    		unlock.setText(R.string.unlock3);
    		break;
    	case R.id.item4:
    		item0.setImageResource(R.drawable.resurrection);
    		name.setText(R.string.item4);
    		desc.setText(R.string.desc4);
    		unlock.setText(R.string.unlock4);
    		break;
    	case R.id.item5:
    		item0.setImageResource(R.drawable.add5);
    		name.setText(R.string.item5);
    		desc.setText(R.string.desc5);
    		unlock.setText(R.string.unlock5);
    		break;
    	case R.id.item6:
    		item0.setImageResource(R.drawable.add1);
    		name.setText(R.string.item6);
    		desc.setText(R.string.desc6);
    		unlock.setText(R.string.unlock6);
    		break;
    	case R.id.item7:
    		item0.setImageResource(R.drawable.lock);
    		name.setText(R.string.item7);
    		desc.setText(R.string.desc7);
    		unlock.setText(R.string.unlock7);
    		break;
    	case R.id.item8:
    		item0.setImageResource(R.drawable.lock);
    		name.setText(R.string.item8);
    		desc.setText(R.string.desc8);
    		unlock.setText(R.string.unlock8);
    		break;
    	case R.id.item9:
    		item0.setImageResource(R.drawable.lock);
    		name.setText(R.string.item9);
    		desc.setText(R.string.desc9);
    		unlock.setText(R.string.unlock9);
    		break;
    	}
    }


	public void Start(View view){

		view.setBackgroundDrawable(null);
		if(view instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup)view;
			int size = vg.getChildCount();
			for(int i = 0; i < size; i++) {
				Start(vg.getChildAt(i));
			}
		}
		//ゲームスタート
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setClassName( "gaku.app.prinotoshi","gaku.app.prinotoshi.StartActivity");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// メニューの要素を追加
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "Update");

		return true;
	}

	// オプションメニュー選択された場合、選択項目に合わせて
	// WebViewの表示先URLを変更する。
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/* webViewにlayout.xmlのwebviewをセット */
		// WebView myWebView = (WebView)findViewById(R.id.webview);
		super.onOptionsItemSelected(item);
		int itemId = item.getItemId();
		switch (itemId) {
		case 0:
			// パフォーマンス低下を検出する機能を無効にしておく
			StrictMode
			.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.permitAll().build());

			String url0 = "http://pppnexus.ddo.jp/prinotoshi.apk";
			// ダウンロード・インストール開始
			download(url0);

			break;
		}
		Toast.makeText(this, "Selected Item: " + item.getTitle(),
				Toast.LENGTH_SHORT).show();
		return true;
	}

	/**
	 * ダウンロード・インストールメソッド
	 */
	public void download(String apkurl) {

		try {
			// URL設定
			URL url = new URL(apkurl);

			// HTTP接続開始
			HttpURLConnection c = (HttpURLConnection) url.openConnection();
			c.setRequestMethod("GET");
			c.connect();
			// SDカードの設定
			String PATH = Environment.getExternalStorageDirectory()
					+ "/download/";
			File file = new File(PATH);
			file.mkdirs();

			// テンポラリファイルの設定
			File outputFile = new File(file, "app.apk");
			FileOutputStream fos = new FileOutputStream(outputFile);

			// ダウンロード開始
			InputStream is = c.getInputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			is.close();

			// Intent生成
			Intent intent = new Intent(Intent.ACTION_VIEW);
			// MIME type設定
			intent.setDataAndType(
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory()
							+ "/download/"
							+ "app.apk")),
					"application/vnd.android.package-archive");
			// Intent発行
			startActivity(intent);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}