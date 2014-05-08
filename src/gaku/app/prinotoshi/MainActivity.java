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
import android.view.Window;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SurfaceView のインスタンスを実体化し、ContentView としてセットする
        //SurfaceViewTest surfaceView = new SurfaceViewTest(this);
        //PrinSurface surfaceView = new PrinSurface(this);
        //setContentView(surfaceView);// タイトルバーを非表示
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.v("create","create");
    }
    @Override
    protected void onResume() {
    super.onResume();
    // SurfaceView のインスタンスを実体化し、ContentView としてセットする
    //SurfaceViewTest surfaceView = new SurfaceViewTest(this);
    //PrinSurface surfaceView = new PrinSurface(this);
    //setContentView(surfaceView);

    }
    public void Start(View view){
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