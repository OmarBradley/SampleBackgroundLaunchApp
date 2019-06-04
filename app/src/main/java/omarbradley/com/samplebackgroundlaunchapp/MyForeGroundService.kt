package omarbradley.com.samplebackgroundlaunchapp

import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MyForeGroundService : Service() {

    private val CHANNEL_ID = "Foreground"

    private lateinit var observable: Disposable

    @Nullable
    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show()
        createNotificationChannel()
        Toast.makeText(this, "Creating Notification", Toast.LENGTH_SHORT).show()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("test")
            .setTicker("test")
            .setContentText("Example")
            .setSmallIcon(R.drawable.ic_dialog_map)
            .build()

        startForeground(1001, notification)
    }

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Creating Notification111", Toast.LENGTH_SHORT).show()
        observable = Observable.interval(3000L, TimeUnit.MILLISECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                startActivity(Intent(this, TestActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }, { e ->
                e.printStackTrace()
            })
        return START_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show()
        observable.dispose()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(omarbradley.com.samplebackgroundlaunchapp.R.string.channel_name)
            val descriptionText = getString(omarbradley.com.samplebackgroundlaunchapp.R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
