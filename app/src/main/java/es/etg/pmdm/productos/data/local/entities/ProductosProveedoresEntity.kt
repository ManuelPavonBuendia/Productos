package es.etg.pmdm.productos.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProductosProveedoresEntity(
    @Embedded val productos: ProductoEntity,
    @Relation(
        parentColumn = "id", //Entidad cliente
        entityColumn = "idProducto" // Entidad secundaria: telefono
    )
    val proveedores: List<ProveedorEntity>
)