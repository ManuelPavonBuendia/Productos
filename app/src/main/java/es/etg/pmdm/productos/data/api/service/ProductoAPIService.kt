package es.etg.pmdm.productos.data.api.service

import es.etg.pmdm.productos.data.api.model.ProductoResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductoAPIService {
    // Hace una petición GET al endpoint "objects" de la API.
    // La API devuelve un array JSON de productos, por eso usamos List<ProductoResponse>.
    // Retrofit convierte automáticamente el JSON en una lista de objetos ProductoResponse.
    @GET("objects")
    suspend fun getProductos(): Response<List<ProductoResponse>>
}