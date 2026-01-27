package es.etg.pmdm.productos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Productos")
data class ProductoEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var nombre:String="",
    var descripcion:String=""
)