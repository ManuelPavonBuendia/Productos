package es.etg.pmdm.productos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import es.etg.pmdm.productos.data.local.entities.ProductoEntity

@Dao
interface ProductosDao {
    @Transaction
    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getProductoById(id: Int): ProductoEntity?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(Productos: ProductoEntity):Long

}