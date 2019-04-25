package com.naldana.ejemplo10

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.naldana.ejemplo10.adapter.MoneyAdapter
import com.naldana.ejemplo10.database.DatabaseContract
import com.naldana.ejemplo10.pojo.Coin
import com.naldana.ejemplo10.firebase.Database
import com.naldana.ejemplo10.database.DatabaseSQL
import com.naldana.ejemplo10.fragmentos.MoneyFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var dbHelper = DatabaseSQL(this) // TODO (12) Se crea una instancia del SQLiteHelper definido en la clase Database.
    val ultradata = arrayListOf<Coin>()
    val conexionDB = Database()
    val moneyF = MoneyFragment()
    val TAG = "MainActivity"
    var twoPane = false

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // TODO (9) Se asigna a la actividad la barra personalizada
        setSupportActionBar(toolbar)


        // TODO (10) Click Listener para el boton flotante
        fab.setOnClickListener {
            conexionDB.fillData(ultradata, this::writeToLocalDB)
            /*val intento = Intent(this@MainActivity, CurrencyAdder::class.java)
            startActivity(intento)*/
        }
        addCoin.setOnClickListener{
            val intento = Intent(this@MainActivity, CurrencyAdder::class.java)
            startActivity(intento)
        }

        // TODO (11) Permite administrar el DrawerLayout y el ActionBar
        // TODO (11.1) Implementa las caracteristicas recomendas
        // TODO (11.2) Un DrawerLayout (drawer_layout)
        // TODO (11.3) Un lugar donde dibujar el indicador de apertura (la toolbar)
        // TODO (11.4) Una String que describe el estado de apertura
        // TODO (11.5) Una String que describe el estado cierre
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        // TODO (12) Con el Listener Creado se asigna al  DrawerLayout
        drawer_layout.addDrawerListener(toggle)


        // TODO(13) Se sincroniza el estado del menu con el LISTENER
        toggle.syncState()

        // TODO (14) Se configura el listener del menu que aparece en la barra lateral
        // TODO (14.1) Es necesario implementar la inteface {{@NavigationView.OnNavigationItemSelectedListener}}
        nav_view.setNavigationItemSelectedListener(this)
        if (fragment_content != null) {
            twoPane = true
        }
        /*
         * TODO (Instrucciones)Luego de leer todos los comentarios añada la implementación de RecyclerViewAdapter
         * Y la obtencion de datos para el API de Monedas
         */
        setAdapter(readMonedas())
    }

    private fun setAdapter(data: ArrayList<Coin>) {
        // TODO (20) Para saber si estamos en modo dos paneles
        recyclerview.apply {
            setHasFixedSize(true)
            Log.i("MainActivity", "Esta mierda se ejecuta")
            adapter = MoneyAdapter(data) {
                moneyF.setData(it)
            }
            layoutManager = if (twoPane) {
                GridLayoutManager(this.context, 1)
            } else {
                GridLayoutManager(this.context, 2)
            }
            if (twoPane) {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_content, moneyF).commit()
            }
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
            R.id.nav_camera -> {
                Log.i(TAG, "disponibel")
            }
            R.id.nav_gallery -> {
                Log.i(TAG, "disponibel")
            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

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

    private fun writeToLocalDB(data: ArrayList<Coin>) {
        val db = dbHelper.writableDatabase
        data.forEach {
            val values = ContentValues().apply {
                put(DatabaseContract.CoinEntry.COLUMN_NAME, it.name)
                put(DatabaseContract.CoinEntry.COLUMN_COUNTRY, it.country)
                put(DatabaseContract.CoinEntry.COLUMN_YEAR, it.year)
                put(DatabaseContract.CoinEntry.COLUMN_AVAILABLE, if (it.available) 1 else 0)
            }

            val newRowId = db?.insert(DatabaseContract.CoinEntry.TABLE_NAME, null, values)

            if (newRowId == -1L) {
                Snackbar.make(findViewById(R.id.recyclerview), "ALV se cacaseo", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(findViewById(R.id.recyclerview), "si funciono $newRowId", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun readMonedas(): ArrayList<Coin> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseContract.CoinEntry.COLUMN_NAME,
            DatabaseContract.CoinEntry.COLUMN_COUNTRY,
            DatabaseContract.CoinEntry.COLUMN_YEAR,
            DatabaseContract.CoinEntry.COLUMN_AVAILABLE
        )

        val sortOrder = "${DatabaseContract.CoinEntry.COLUMN_NAME} DESC"

        val cursor = db.query(
            DatabaseContract.CoinEntry.TABLE_NAME, // nombre de la tabla
            projection, // columnas que se devolverán
            null, // Columns where clausule
            null, // values Where clausule
            null, // Do not group rows
            null, // do not filter by row
            sortOrder // sort order
        )

        var lista = ArrayList<Coin>()

        with(cursor) {
            while (moveToNext()) {
                var coin = Coin(
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_COUNTRY)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_YEAR)),
                    getInt(getColumnIndexOrThrow(DatabaseContract.CoinEntry.COLUMN_AVAILABLE))==1
                )
                Log.i("MainActivity", "From local database ${coin.name} ${coin.year}" )
                lista.add(coin)
            }
        }

        return lista
    }
}
