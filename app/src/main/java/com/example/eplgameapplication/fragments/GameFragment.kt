package com.example.eplgameapplication.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import kotlinx.android.synthetic.main.fragment_game.*

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eplgameapplication.R
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import kotlin.concurrent.thread


class GameFragment : Fragment() {
    val ServerIP:String = "tcp://172.30.1.21:1883"
    var handler : Handler? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mqttClient: MqttClient? = null
        mqttClient = MqttClient(ServerIP, MqttClient.generateClientId(), null) //3번 연결설정
        mqttClient.connect()

        btn_rank.setOnClickListener {
            rank_layout.visibility = View.VISIBLE
            play_layout.visibility = View.INVISIBLE
            win_layout.visibility = View.INVISIBLE
            mqttClient.publish("android", MqttMessage("rank".toByteArray()))
       }
        btn_play.setOnClickListener {
            rank_layout.visibility = View.INVISIBLE
            play_layout.visibility = View.VISIBLE
            win_layout.visibility = View.INVISIBLE
            mqttClient.publish("android", MqttMessage("play".toByteArray()))
        }
        btn_winner.setOnClickListener {
            rank_layout.visibility = View.INVISIBLE
            play_layout.visibility = View.INVISIBLE
            win_layout.visibility = View.VISIBLE
            mqttClient.publish("android", MqttMessage("win".toByteArray()))
        }
        mqttClient.subscribe("game")
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                Log.d("MQTTService", "Connection")
            }
                @SuppressLint("ResourceAsColor")
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    Log.d("MQTTService", "Message Arrived : " + message.toString())
                    val msg = message.toString()
                    if (msg.contains("play")) {
                        var msg2 = msg.split(",")
                        score.text = (msg2[1].toInt() + 1).toString() + "번째 게임"
                        game1.setTextColor(Color.BLACK)
                        game1.text = msg2[2]
                        game2.setTextColor(Color.BLACK)
                        game2.text = msg2[3]
                        game3.setTextColor(Color.BLACK)
                        game3.text = msg2[4]
                    }
                    if (msg.contains("rank")) {
                        var msg1 = msg.split(",")
                        team1.text = msg1[1]
                        point1.text = msg1[2]
                        team2.text = msg1[3]
                        point2.text = msg1[4]
                        team3.text = msg1[5]
                        point3.text = msg1[6]
                        team4.text = msg1[7]
                        point4.text = msg1[8]
                        team5.text = msg1[9]
                        point5.text = msg1[10]
                        team6.text = msg1[11]
                        point6.text = msg1[12]
                    }
                    if (msg.contains("winner")) {
                        var msg3 = msg.split(",")
                        Log.d("winner", msg3.toString())
                        score.text = "*우승팀*"
                        game1.setTextColor(Color.RED)
                        game1.text = msg3[1]
                        game2.text = "*득점왕*"
                        game3.setTextColor(Color.RED)
                        game3.text = msg3[2] + "-" + msg3[3] + "골"
                    }
                    if (msg.contains("history")){
                        var msg4 = msg.split(",")
                        Log.d("history", msg4.toString())
                        winnerteam2.text = msg4[1]
                        winnerteam3.text = msg4[3]
                        winnerteam4.text = msg4[5]
                        winnerteam5.text = msg4[7]
                        winnerteam6.text = msg4[9]
                        winnerteam7.text = msg4[11]
                        winner1.text = msg4[2]
                        winner2.text = msg4[4]
                        winner3.text = msg4[6]
                        winner4.text = msg4[8]
                        winner5.text = msg4[10]
                        winner6.text = msg4[12]
                    }
                }
            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("MQTTService", "Delivery Complete")
            }
        })
    }
}