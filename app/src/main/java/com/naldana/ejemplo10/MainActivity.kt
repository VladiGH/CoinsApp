package com.naldana.ejemplo10

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.naldana.ejemplo10.adapter.MoneyAdapter
import com.naldana.ejemplo10.database.DataProvider
import com.naldana.ejemplo10.fragmentos.MoneyFragment
import com.naldana.ejemplo10.activities.CurrencyAdder
import com.naldana.ejemplo10.database.LocalDB
import com.naldana.ejemplo10.utilities.ViewAnimator


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //private val tag = this@MainActivity::class.java.simpleName
    private val dataProvider = DataProvider(this@MainActivity)
    private val localDB = LocalDB(this@MainActivity)
    private var twoPane = false
    private val animator = ViewAnimator()
    private lateinit var moneyF: MoneyFragment
    private lateinit var moneyAdapter: MoneyAdapter


    override fun onDestroy() {
        super.onDestroy()
        localDB.closeDb()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            it.startAnimation(animator.getRotationAnimation())
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
            moneyF = MoneyFragment()
        }
        dataProvider.loadCoinList{
            moneyAdapter = MoneyAdapter(it){ coin ->
                if (twoPane) {
                    moneyF.setData(coin)
                } else {
                    //TODO 10 launch activity to show further money details
                }
            }
            setAdapter(moneyAdapter)
            recyclerview.adapter?.notifyDataSetChanged()
        }
        dataProvider.loadCountryList {
            it.forEach{country ->
                nav_view.menu.add(R.id.filter, it.indexOf(country), 0, country.name)
            }
        }
    }

    private fun setAdapter(adapterForMoney: MoneyAdapter) {
        recyclerview.apply {
            setHasFixedSize(true)
            adapter = adapterForMoney
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

            R.id.filter_AZ -> {
                moneyAdapter.sortDataByName()
            }
            R.id.filter_ZA -> {
                moneyAdapter.sortDataByName(false)
            }

            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
            else -> {
                dataProvider.loadCountryList {
                    moneyAdapter.filterByCountry(it[item.itemId].name)
                }
            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
