package com.cubicmedia.elcledcontroller

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.Socket


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        btc_set_color.setOnClickListener {
            /*val a = IridiumLib()
            a.setLedBacklight(0, 33, 55)*/
            //sendNotification()
            sendThrowSocket()
        }
    }

    private fun sendThrowSocket() {
        GlobalScope.launch {
            val client = Socket("127.0.0.1", 5000)
            val listByte = ArrayList<Byte>()
            listByte.add(0xF3.toByte())
            listByte.add(1.toByte())
            listByte.add(0.toByte())
            listByte.add(33.toByte())
            listByte.add(55.toByte())
            client.outputStream.write(listByte.toByteArray())
            client.close()
        }
    }

    private val LED_NOTIFICATION_ID = 0 //arbitrary constant


    private fun redFlashLight() {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notif = Notification()
        notif.ledARGB = -0x10000
        notif.flags = Notification.FLAG_SHOW_LIGHTS
        notif.ledOnMS = 100
        notif.ledOffMS = 100
        nm.notify(LED_NOTIFICATION_ID, notif)
    }

    private fun sendNotification() {
        val notificationID = 2
        val channelID = "com.cubicmedia.cubiccv.music"
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(
                this,
                channelID)
                .setContentTitle("Cubic Computer Vision")
                .setContentText("Service is working.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(channelID)
                .setAutoCancel(true)
                .setLights(Color.GREEN, 100, 100)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setPriority(PRIORITY_MAX)
                .build()
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationID, notification)
    }


    /*
        public const byte LedControlSet = 0xF3;

        public virtual void EnableLedStrip(Color color)
        {
            Set(SicpCommands.LedControlSet, true.ToByte(), color.R, color.G, color.B);
        }

        public virtual void Set(byte command, ISicpCommandParameters parameters)
        {
            Set(command, parameters.ToBytes());
        }

        public virtual void Set(byte command, params byte[] parameters)
        {
            List<byte> data = new List<byte> { command };
            data.AddRange(parameters);

            socket.Send(new SicpMessage(monitorId, groupId, data.ToArray()));
        }
    * */
}