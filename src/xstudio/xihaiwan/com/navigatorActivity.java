package xstudio.xihaiwan.com;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class navigatorActivity extends Activity {
	private ImageView menu = null;
	private ImageView mainnotice = null;
	private long mkeyTime = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
	    setContentView(R.layout.navigatoractivity);
		ExitApplication.getInstance().addActivity(this);
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);

		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);

		// 将广告条加入到布局中
		adLayout.addView(adView);	
	    menu = (ImageView)findViewById(R.id.gotomenu);
	    mainnotice =(ImageView)findViewById(R.id.gotoinformation);
	    
	    menu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(navigatorActivity.this,
						XihaiwanActivity.class);
				startActivity(it);	
			}
		});

	    mainnotice.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(navigatorActivity.this,
						contentlistActivity.class);
				startActivity(it);	
			}
		});


	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-mkeyTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            mkeyTime = System.currentTimeMillis();   
	        } else {
	            //finish();
	            ExitApplication.getInstance().exit();
	            //System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
