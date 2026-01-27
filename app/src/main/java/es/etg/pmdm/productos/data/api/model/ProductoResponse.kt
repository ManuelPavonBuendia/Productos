package es.etg.pmdm.productos.data.api.model

import com.google.gson.annotations.SerializedName

data class ProductoResponse(
    val id: String,
    @SerializedName("name") var nombre: String,
    @SerializedName("data") var descripcion: Map<String, String>?
)