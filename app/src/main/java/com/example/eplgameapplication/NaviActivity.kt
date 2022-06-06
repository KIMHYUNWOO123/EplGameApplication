package com.example.eplgameapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eplgameapplication.R
import com.example.eplgameapplication.databinding.ActivityNaviBinding
import com.example.eplgameapplication.fragments.GameFragment
import com.example.eplgameapplication.fragments.PlayerFragment
import com.example.eplgameapplication.fragments.TeamFragment

private const val TAG_Game = "Gmaefragment"
private const val TAG_Team = "Teamfragment"
private const val TAG_Player = "Playerfragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setFragment(TAG_Game, GameFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_game -> setFragment(TAG_Game, GameFragment())
                R.id.nav_team -> setFragment(TAG_Team, TeamFragment())
                R.id.nav_player-> setFragment(TAG_Player, PlayerFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val game = manager.findFragmentByTag(TAG_Game)
        val team = manager.findFragmentByTag(TAG_Team)
        val player = manager.findFragmentByTag(TAG_Player)

        if (game != null){
            fragTransaction.hide(game)
        }

        if (team != null){
            fragTransaction.hide(team)
        }

        if (player != null) {
            fragTransaction.hide(player)
        }

        if (tag == TAG_Game) {
            if (game!=null){
                fragTransaction.show(game)
            }
        }
        else if (tag == TAG_Team) {
            if (team != null) {
                fragTransaction.show(team)
            }
        }

        else if (tag == TAG_Player){
            if (player != null){
                fragTransaction.show(player)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }
}
