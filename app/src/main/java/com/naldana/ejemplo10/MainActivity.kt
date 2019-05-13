package com.naldana.ejemplo10

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
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
import com.naldana.ejemplo10.activities.DetailMoney
import com.naldana.ejemplo10.utilities.ViewAnimator


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val tag = this@MainActivity::class.java.simpleName
    private val dataProvider = DataProvider(this@MainActivity)
    private var twoPane = false
    private val animator = ViewAnimator()
    private lateinit var moneyF: MoneyFragment
    private lateinit var moneyAdapter: MoneyAdapter


    override fun onDestroy() {
        super.onDestroy()
        dataProvider.terminateProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener {
            it.startAnimation(animator.getRotationAnimation())
            dataProvider.syncCoinList { updatedCoin ->
                var updateErrors = 0
                updatedCoin.forEach { success ->
                    if(success) updateErrors++
                }
                moneyAdapter.updateCurrentCoin(dataProvider.loadCoinList())
                Snackbar.make(it, getString(R.string.updated_of)+": $updateErrors of ${moneyAdapter.getTrueItemCount()}", Snackbar.LENGTH_LONG).show()
            }
            dataProvider.syncCountryList {
                //Todo Jorge insertar paises nuevos en el menu lateral
            }
        }
        addCoin.setOnClickListener {
            val intent = Intent(this@MainActivity, CurrencyAdder::class.java)
            startActivity(intent)
        }
        //TODO Jorge hacer actividad para insertar paises y agregar un boton para acceder a ella
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
        moneyAdapter = MoneyAdapter(dataProvider.loadCoinList()) { coin ->
            if (twoPane) {
                val newLandMoneyFragmentMain = MoneyFragment.newInstance(coin)
                changeFragment(R.id.fragment_content,newLandMoneyFragmentMain)
            } else {
                //TODO Raul launch MoneyActivity to show further money details
                val coinBundle = Bundle().apply {
                    putParcelable("coin", coin)
                }
                val intent= Intent(this@MainActivity, DetailMoney::class.java)
                intent.putExtras(coinBundle)
                startActivity(intent)
            }
        }
        setAdapter(moneyAdapter)
        dataProvider.loadCountryList {
            it.forEach { country ->
                nav_view.menu.add(R.id.filter, it.indexOf(country), 0, country.name)
            }
        }
    }

    private fun changeFragment(id: Int, frag: Fragment){ supportFragmentManager.beginTransaction().replace(id, frag).commit() }

    private fun setAdapter(adapterForMoney: MoneyAdapter) {
        recyclerview.apply {
            setHasFixedSize(true)
            adapter = adapterForMoney
            layoutManager =
                if (twoPane) {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_content, moneyF).commit()
                    GridLayoutManager(this.context, 1)
                } else GridLayoutManager(this.context, 2)
        }
        adapterForMoney.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

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

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
