package xstudio.xihaiwan.com;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

public class WebcontentActivity extends Activity {
	private WebView noticeview = null;
	private String webcontent = "";
	private String contenturl = null;
	private ImageButton backimg = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.webcontentactivity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.menutitlebar); // titlebar为自己标题栏的布局
		ExitApplication.getInstance().addActivity(this);
		Intent intent = this.getIntent();// 得到用于激活它的意图
		contenturl = intent.getStringExtra("selecturl");
		noticeview = (WebView) findViewById(R.id.webView1);

		noticeview.getSettings().setJavaScriptEnabled(true);
		noticeview.getSettings().setSupportZoom(false);
		noticeview.getSettings().setBuiltInZoomControls(false);
		noticeview.getSettings().setDefaultFontSize(18);
		backimg = (ImageButton)findViewById(R.id.backimagebtn);
		
		backimg.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(WebcontentActivity.this,
						contentlistActivity.class);
				startActivity(it);	
			}
		});

		//noticeview.loadUrl("http://www.xihaiwan.com.cn/information/notice.aspx?id=25");
		final Runnable updateThread = new Runnable() {

			public void run() {
				// 更新UI
				noticeview.loadDataWithBaseURL("http://www.xihaiwan.com.cn/information/notice.aspx?id=25", webcontent, "text/html", "utf-8",null);
			}

		};		
		new Thread() {
			public void run() {
				//
				try {
					Document doc = Jsoup.connect(
							contenturl)
							.get();
					Elements links = doc.select("div[id=ContentPlaceHolder1_htmltext]");
					// 注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
					for (Element link : links) {
						// 这里没有什么好说的。
						webcontent += link.text();
					}

					WebcontentActivity.this.runOnUiThread(updateThread);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		

		
	}
}