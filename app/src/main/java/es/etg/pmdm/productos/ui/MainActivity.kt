package es.etg.pmdm.productos.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import es.etg.pmdm.productos.data.local.database.ProductoDatabase
import es.etg.pmdm.productos.data.local.entities.ProductoEntity
import es.etg.pmdm.productos.data.local.entities.ProveedorEntity
import es.etg.pmdm.productos.data.api.service.ProductoAPIService
import es.etg.pmdm.productos.data.api.service.ProveedorAPIService
import es.etg.pmdm.productos.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var productoService: ProductoAPIService
    private lateinit var proveedorService: ProveedorAPIService
    companion object {
        const val BASE_URL = "https://api.restful-api.dev/"
        const val BASE_URL_DETALLE = "https://raw.githubusercontent.com/ManuelPavonBuendia/provedores/main/"
        lateinit var database: ProductoDatabase
        const val NOMBRE_BD="producto_database"
        const val CARGADO_BD="Datos cargados desde la base de datos"

        const val PROVEDOR_NO_ENCONTRADO="Proveedor no encontrado"
        const val ERROR_CONSULTAR_SERVICIO="Error al consultar el servicio"
        const val SIN_DATOS="Sin datos"
        const val DATOS_CARGADOS="Datos cargados"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productoService = createService(BASE_URL, ProductoAPIService::class.java)
        proveedorService = createService(BASE_URL_DETALLE, ProveedorAPIService::class.java)
        database = Room.databaseBuilder(
            applicationContext,
            ProductoDatabase::class.java,
            NOMBRE_BD
            ).build()
        //Implementación de pulsar el botón
        binding.btnConsultar.setOnClickListener {
            consultar(binding.edtIdInput.text.toString())
        }
    }

    private fun consultar(idProducto: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val productoDao = database.productoDao()
            val proveedorDao = database.proveedorDao()

            val productoDb = productoDao.getProductoById(idProducto.toInt())
            val proveedorDb = proveedorDao.getProveedorByProductoId(idProducto.toInt())

            runOnUiThread {
                if (productoDb != null && proveedorDb != null) {
                    // Mostrar desde la BD
                    binding.txtId.text = productoDb.id.toString()
                    binding.txtNombre.text = productoDb.nombre
                    binding.txtData.text = productoDb.descripcion
                    binding.txtProveedor.text = proveedorDb.nombre
                    Toast.makeText(this@MainActivity, CARGADO_BD, Toast.LENGTH_SHORT).show()
                } else {
                    // No está en la BD, hacemos la consulta a la API
                    consultarDesdeApi(idProducto)
                }
            }
        }
    }

    private fun consultarDesdeApi(idProducto: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val productos = productoService.getProductos().body()
            val producto = productos?.find { it.id == idProducto }
            val response = proveedorService.getProveedores().body()
            val proveedor = response?.find { it.idProducto == idProducto.toInt() }

            runOnUiThread {
                if (producto != null) {
                    val productoId = producto.id.toInt()
                    binding.txtId.text = productoId.toString()
                    binding.txtNombre.text = producto.nombre
                    val productoDescripcion = producto.descripcion?.entries?.joinToString { "${it.key}: ${it.value}" } ?: SIN_DATOS
                    binding.txtData.text = productoDescripcion
                    val proveedorNombre = proveedor?.nombre ?: PROVEDOR_NO_ENCONTRADO
                    binding.txtProveedor.text = proveedorNombre

                    // Guardar en la BD
                    guardar(productoId, producto.nombre, productoDescripcion, proveedorNombre)
                } else {
                    Toast.makeText(this@MainActivity, ERROR_CONSULTAR_SERVICIO, Toast.LENGTH_LONG).show()
                }
            }
        }
    }



    private fun <T> createService(baseUrl: String, serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    private fun guardar(id: Int, nombre: String, descripcion: String, proveedor: String){
        val productoDao = database.productoDao()
        val proveedorDao = database.proveedorDao()

        var productoId: Long = 0 ;
        val producto = ProductoEntity(id,nombre,descripcion)

        CoroutineScope(Dispatchers.IO).launch {

            productoId = productoDao.insert(producto)

            val proveedor = ProveedorEntity(0,proveedor,id)
            // Insertamos el cliente
            proveedorDao.insert(proveedor)
            runOnUiThread {
                Toast.makeText(this@MainActivity, DATOS_CARGADOS, Toast.LENGTH_LONG).show()
            }
        }

    }
}