package xyz.hisname.fireflyiii.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import xyz.hisname.fireflyiii.repository.models.currency.CurrencyData

@Dao
abstract class CurrencyDataDao: BaseDao<CurrencyData> {

    @Query("SELECT * FROM currency")
    abstract fun getAllCurrency(): LiveData<MutableList<CurrencyData>>

}