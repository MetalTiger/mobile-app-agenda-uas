package com.uas.agenda.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.uas.agenda.api.model.Datos
import com.uas.agenda.api.model.LoginData
import com.uas.agenda.api.model.LoginResponse
import com.uas.agenda.api.service.APIService
import com.uas.agenda.api.service.ServiceBuilder
import com.uas.agenda.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()

    }

    private fun setup() {

        binding.loginButton.setOnClickListener{

            if (binding.userEditText.text.isEmpty() || binding.passwordEditText.text.isEmpty() || binding.isCodeEditText.text.isEmpty()){

                showAlert("Error", "No deje espacios en blanco.")

            } else {

                signIn()

            }

        }
    }

    private fun signIn(){

        val datos = LoginData(binding.userEditText.text.toString(), binding.passwordEditText.text.toString())

        val call = ServiceBuilder.buildService(APIService::class.java).signIn(datos)

        call.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.println(Log.INFO, "tagSignIn", response.body().toString())
                val head: Datos? = response.body()?.head

                if (head != null) {


                    if (head.code.equals(200)) {

                        showMenu()
                        Log.println(Log.INFO, "tagSignIn", head.toString())


                    } else {
                        Toast.makeText(this@LoginActivity, head.message, Toast.LENGTH_LONG).show()
                        Log.println(Log.ERROR, "tagSignIn", head.message)
                    }

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.println(Log.ERROR, "tagSignIn", t.toString())
                // Crear ventana emergente mostrando el error y poner opción, por ejemplo reintentar el inicio de sesión
                signIn()

            }

        })

    }

    private fun showMenu () {
        val menuIntent = Intent(this, MenuActivity::class.java)
            .putExtra("correo", binding.userEditText.text.toString())

        startActivity(menuIntent)

    }

    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}