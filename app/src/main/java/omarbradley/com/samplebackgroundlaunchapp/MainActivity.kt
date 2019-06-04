package omarbradley.com.samplebackgroundlaunchapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_topViewService.setOnClickListener {
            startService(Intent(this, TopViewService::class.java))
        }

        button_startForegroundService.setOnClickListener {
            startForegroundService(Intent(this, MyForeGroundService::class.java))
        }

    }

}
