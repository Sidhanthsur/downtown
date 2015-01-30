package in.co.mkube.downtown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by tarun on 30/1/15.
 */
public class SplashActivity extends Activity {
    private static final int SPLASH_TIME = 2 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                SplashActivity.this.finish();


            }
        }, SPLASH_TIME);

    }


    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
