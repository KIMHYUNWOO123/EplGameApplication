package com.example.eplgameapplication.fragments

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
    val ServerIP:String = "tcp://172.30.1.21:1883"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_game)
        var mqttClient: MqttClient? = null
        mqttClient = MqttClient(ServerIP, MqttClient.generateClientId(), null)
        mqttClient.connect()

        btn_rank.setOnClickListener {
            mqttClient.publish("android", MqttMessage("rank".toByteArray()))
            rank_layout.visibility = View.VISIBLE
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var mqttClient: MqttClient? = null
//        mqttClient = MqttClient(ServerIP, MqttClient.generateClientId(), null)
//        mqttClient.connect()

        btn_rank.setOnClickListener {
//            mqttClient.publish("android", MqttMessage("rank".toByteArray()))
            rank_layout.visibility = View.VISIBLE
       }
//        mqttClient.subscribe("rank")
//        mqttClient.setCallback(object : MqttCallback {
//            override fun connectionLost(cause: Throwable?) {
//                Log.d("MQTTService", "Connection")
//            }
//
//            override fun messageArrived(topic: String?, message: MqttMessage?) {
//                Log.d("MQTTService","Message Arrived : " + message.toString())
//                val msg = message.toString()
//                if (msg.contains("Tottenham")) {
//                    var msg1  = msg.split(",")
//                    team1.text = msg1[0]
//                }
//            }
//            override fun deliveryComplete(token: IMqttDeliveryToken?) {
//                Log.d("MQTTService", "Delivery Complete")
//            }
//        })
//    }
    }
}