package xstudio.xihaiwan.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class navigatorActivity extends Activity {
	private ImageView menu = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
	    setContentView(R.layout.navigatoractivity);

	    menu = (ImageView)findViewById(R.id.gotomenu);
	    
	    menu.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent();
				it.setClass(navigatorActivity.this,
						XihaiwanActivity.class);
				startActivity(it);	
			}
		});



	
	}
}
