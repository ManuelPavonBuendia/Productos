package es.etg.pmdm.productos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.etg.pmdm.productos.data.local.entities.ProductoEntity
import es.etg.pmdm.productos.data.local.entities.ProveedorEntity
import es.etg.pmdm.productos.data.local.dao.ProductosDao
import es.etg.pmdm.productos.data.local.dao.ProveedoresDAO

@Database(entities = [ProductoEntity::class, ProveedorEntity::class], version = 1)
abstract class ProductoDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductosDao

    abstract fun proveedorDao(): ProveedoresDAO
}