package com.naldana.ejemplo10

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.naldana.ejemplo10.adapter.MoneyAdapter
import com.naldana.ejemplo10.models.Coin
import com.naldana.ejemplo10.database.DataProvider
import com.naldana.ejemplo10.fragmentos.MoneyFragment
import android.view.animation.DecelerateInterpolator
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val tag = this@MainActivity::class.java.simpleName
    private val moneyF = MoneyFragment()
    private val dataProvider = DataProvider(this@MainActivity)
    private var twoPane = false

    override fun onDestroy() {
        super.onDestroy()
        dataProvider.closeDb()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            val animSet = AnimationSet(true)
            animSet.interpolator = DecelerateInterpolator()
            animSet.fillAfter = true
            animSet.isFillEnabled = true
            val animRotate = RotateAnimation(
                0.0f, 360.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
            animRotate.duration = 1500
            animRotate.fillAfter = true
            animRotate.repeatCount = Animation.INFINITE
            animSet.addAnimation(animRotate)
            it.startAnimation(animSet)
            recyclerview.adapter?.notifyDataSetChanged()
        }
        addCoin.setOnClickListener {
            val intent = Intent(this@MainActivity, CurrencyAdder::class.java)
            startActivity(intent)
        }

        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this@MainActivity)
        if (fragment_content != null) {
            twoPane = true
        }
        dataProvider.pullData(Coin::class.java){
            setAdapter(it)
            recyclerview.adapter?.notifyDataSetChanged()
        }
    }

    private fun setAdapter(data: ArrayList<Coin>) {
        data.forEach { coin ->  Log.i(tag, "ejecutando setAdapter <E> ${coin._id}")}
        recyclerview.apply {
            setHasFixedSize(true)
            adapter = MoneyAdapter(data) {
                if (twoPane) {
                    moneyF.setData(it)
                } else {
                    //TODO 10 hacer algo cool como abrir otra activity aqui y le pasamos la moneda
                }
            }
            layoutManager =
                if (twoPane){
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_content, moneyF).commit()
                    GridLayoutManager(this.context, 1)
                }
                else GridLayoutManager(this.context, 2)
        }
    }


    // TODO (16) Para poder tener un comportamiento Predecible
    // TODO (16.1) Cuando se presione el boton back y el menu este abierto cerralo
    // TODO (16.2) De lo contrario hacer la accion predeterminada
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // TODO (17) LLena el menu que esta en la barra. El de tres puntos a la derecha
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // TODO (18) Atiende el click del menu de la barra
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    // TODO (14.2) Funcion que recibe el ID del elemento tocado
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.filter_region -> {

            }
            R.id.filter_AZ -> {
                (recyclerview.adapter as MoneyAdapter).sortDataByName()
            }
            R.id.filter_ZA -> {
                (recyclerview.adapter as MoneyAdapter).sortDataByName(false)
            }
            R.id.equivalence -> {
                Log.i(tag, "equivalence")
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
