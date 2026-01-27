package es.etg.pmdm.productos.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.etg.pmdm.productos.ui.login.LoginFragment
import es.etg.pmdm.productos.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }
    }
}