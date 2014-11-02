package xstudio.xihaiwan.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class startActivity extends Activity {
	private final int SPLASH_DISPLAY_LENGHT = 2000; //延迟三秒    
    
 @Override   
 public void onCreate(Bundle savedInstanceState) {   
    super.onCreate(savedInstanceState);  
    requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
      setContentView(R.layout.startactivity);   
     new Handler().postDelayed(new Runnable(){   
  
	        public void run() {   
           Intent mainIntent = new Intent(startActivity.this,navigatorActivity.class);   
           startActivity.this.startActivity(mainIntent);   
           startActivity.this.finish();   
       }   
             
      }, SPLASH_DISPLAY_LENGHT);   
   }   

}
