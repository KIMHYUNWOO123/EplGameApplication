package com.example.eplgameapplication.fragments

import android.graphics.Color
import kotlinx.android.synthetic.main.fragment_game.*

import android.os.Bundle
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


class GameFragment : Fragment() {
    val ServerIP:String = "tcp://172.30.1.22:1883"
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
            mqttClient.publish("android", MqttMessage("rank".toByteArray()))
       }
        btn_play.setOnClickListener {
            rank_layout.visibility = View.INVISIBLE
            play_layout.visibility = View.VISIBLE
            mqttClient.publish("android", MqttMessage("play".toByteArray()))
        }
        mqttClient.subscribe("game")
        mqttClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                Log.d("MQTTService", "Connection")
            }

            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("MQTTService","Message Arrived : " + message.toString())
                val msg = message.toString()
                if (msg.contains("rank")) {
                    var msg1  = msg.split(",")
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
                if (msg.contains("game")){
                    var msg1  = msg.split(",")
                    game1.text = msg1[1]
                    game2.text = msg1[2]
                    game3.text = msg1[3]
                }
                if (msg.contains("winner")){
                    var msg1  = msg.split(",")
                    game1.text = ""
                    game2.text = msg1[1]
                    game3.text = ""
                }
            }
            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.d("MQTTService", "Delivery Complete")
            }
        })
    }
}