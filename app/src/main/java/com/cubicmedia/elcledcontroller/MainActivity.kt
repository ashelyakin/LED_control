package com.cubicmedia.elcledcontroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.Socket
import kotlin.experimental.and


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
            val client = Socket("localhost", 5000)
            val msg = "0x09" + "0x01" + "0x00" + "0xF4" + "0x01" + "0xFF" + "0xF2" + "0x00" + "0xF0"
            val listByte = ArrayList<Byte>()
            listByte.add(0x09.toByte())
            listByte.add(0x01.toByte())
            listByte.add(0x00.toByte())
            listByte.add(0xF3.toByte())
            listByte.add(0x01.toByte())
            listByte.add(0xFF.toByte())
            listByte.add(0xF2.toByte())
            listByte.add(0x00.toByte())
            listByte.add(0xF7.toByte())
            client.outputStream.write(listByte.toByteArray())
            client.close()
        }
    }
    fun String.decodeHex(): ByteArray = chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()

    private val HEX_ARRAY = "0123456789ABCDEF".toCharArray()
    fun bytesToHex(bytes: ByteArray): String? {
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = (bytes[j] and 0xFF.toByte()).toInt()
            hexChars[j * 2] = HEX_ARRAY[v ushr 4]
            hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
        }
        return String(hexChars)
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