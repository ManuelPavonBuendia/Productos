package es.etg.pmdm.productos.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.etg.pmdm.productos.R
import es.etg.pmdm.productos.databinding.FragmentLoginBinding
import es.etg.pmdm.productos.ui.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)


        binding.btnLogin.setOnClickListener {
            val usuario = binding.edtUsuario.text.toString()
            val contraseña = binding.edtPassword.text.toString()
            if(comprobarCredenciales(usuario,contraseña)) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(requireContext(),
                    "Usuario o contraseña incorrectos",
                    Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun comprobarCredenciales(usuario: String, password: String): Boolean {
        // 🔹 Forma sencilla (perfecta para prácticas)
        return usuario == "Manuel" && password == "250805"
    }



}