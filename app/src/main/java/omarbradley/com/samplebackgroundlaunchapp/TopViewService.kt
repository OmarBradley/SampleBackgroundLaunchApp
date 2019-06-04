package omarbradley.com.samplebackgroundlaunchapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import androidx.annotation.RequiresApi

class TopViewService : Service() {

    private var windowManager: WindowManager? = null
    private var mView: View? = null

    override fun onBind(p0: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val inflate = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val params = WindowManager.LayoutParams(
            300,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.LEFT or Gravity.TOP
        mView = inflate.inflate(R.layout.view_in_service, null)
        windowManager?.addView(mView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (windowManager != null) {
            if (mView != null) {
                windowManager?.removeView(mView)
                mView = null
            }
            windowManager = null
        }
    }

}
