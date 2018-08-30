package id.aasumitro.submission001.ui.activity.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.setContentView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import id.aasumitro.submission001.R.id.main_frame
import id.aasumitro.submission001.ui.fragment.FragmentMain
import org.jetbrains.anko.AnkoLogger

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)
        replaceFragment(FragmentMain())

    }

     private fun replaceFragment (fragment: Fragment, cleanStack: Boolean = false) {
         val manager = supportFragmentManager.beginTransaction()
         if (cleanStack) clearBackStack()
         manager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
         manager.replace(main_frame, fragment)
         manager.addToBackStack(null)
         manager.commit()
    }

    private fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 1) {
            manager.popBackStack()
        } else {
            finish()
        }
    }

}
