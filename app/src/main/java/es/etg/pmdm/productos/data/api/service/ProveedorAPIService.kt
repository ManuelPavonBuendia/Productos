package es.etg.pmdm.productos.data.api.service

import es.etg.pmdm.productos.data.api.model.ProveedorResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProveedorAPIService {
    @GET("proovedores.json")
    suspend fun getProveedores(): Response<List<ProveedorResponse>>
}