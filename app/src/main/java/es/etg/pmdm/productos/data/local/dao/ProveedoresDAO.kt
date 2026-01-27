package es.etg.pmdm.productos.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.etg.pmdm.productos.data.local.entities.ProveedorEntity

@Dao
interface ProveedoresDAO {
    @Query("SELECT * FROM proveedores WHERE idProducto = :idProducto")
    suspend fun getProveedorByProductoId(idProducto: Int): ProveedorEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(Proveedores: ProveedorEntity)

}