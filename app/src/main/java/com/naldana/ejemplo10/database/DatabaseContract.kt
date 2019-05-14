package com.naldana.ejemplo10.database

import android.provider.BaseColumns

object DatabaseContract {

    object CoinEntry : BaseColumns {
        const val TABLE_NAME = "coin"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_YEAR = "year"
        const val COLUMN_AVAILABLE = "available"
    }

    object CountryEntry : BaseColumns {
        const val TABLE_NAME = "country"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

   /* object CxC: BaseColumns {
        const val TABLE_NAME = "cxc"
        const val FKPK_COLUMN_COUNTRY_ID = "countryID"
        const val  FKPK_COLUMN_COIN_ID = "coinID"
    }*/
}