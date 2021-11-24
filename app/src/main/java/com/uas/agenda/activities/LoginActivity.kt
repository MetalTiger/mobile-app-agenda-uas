package com.uas.agenda.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.uas.agenda.R
import com.uas.agenda.api.model.Datos
import com.uas.agenda.api.model.LoginData
import com.uas.agenda.api.model.LoginResponse
import com.uas.agenda.api.service.APIService
import com.uas.agenda.api.service.ServiceBuilder
import com.uas.agenda.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class AlertType {
    ACCEPT,
    ACCEPT_DECLINE
}

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

            val user = validateUser(binding.userEditText.text.toString())
            val pass = validatePassword(binding.passwordEditText.text.toString())
            val code = validateISCode(binding.isCodeEditText.text.toString())

            if (user && pass && code){
                signIn()
            }

        }

        binding.userEditText.setOnClickListener {
            binding.tilUser.error = null
        }

        binding.passwordEditText.setOnClickListener {
            binding.tilPassword.error = null
        }

        binding.isCodeEditText.setOnClickListener {
            binding.tilISCode.error = null
        }

    }

    private fun validateISCode(code: String): Boolean {

        if (code.isBlank()){

            binding.tilISCode.error = "No deje el campo en blanco"

            return false
        } else {
            binding.tilISCode.error = null

            return true
        }

    }

    private fun validatePassword(password: String): Boolean {

        if (password.isBlank()){

            binding.tilPassword.error = "No deje el campo en blanco"

            return false
        } else {
            binding.tilPassword.error = null

            return true
        }

    }

    private fun validateUser(user: String): Boolean {

        if (user.isBlank()){

            binding.tilUser.error = "No deje el campo en blanco"

            return false
        } else {
            binding.tilUser.error = null

            return true
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
                        showAlert("Error", head.message, AlertType.ACCEPT)

                        //Toast.makeText(this@LoginActivity, head.message, Toast.LENGTH_LONG).show()
                        Log.println(Log.ERROR, "tagSignIn", head.message)
                    }

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.println(Log.ERROR, "tagSignIn", t.message.toString())
                // Crear ventana emergente mostrando el error y poner opción, por ejemplo reintentar el inicio de sesión
                showAlert("Error del Servidor", "Tipo de error: ${t.message}. ¿Desea reintentar el inicio de sesión?", AlertType.ACCEPT_DECLINE)

            }

        })

    }

    private fun showMenu () {
        val menuIntent = Intent(this, MenuActivity::class.java)
            .putExtra("correo", binding.userEditText.text.toString())

        startActivity(menuIntent)

    }

    private fun showAlert(title: String, message: String, type: AlertType) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)

        when (type) {
            AlertType.ACCEPT -> {
                builder.setPositiveButton(getString(R.string.Alert_btnAccept), null)
            }
            AlertType.ACCEPT_DECLINE -> {
                builder.setPositiveButton(R.string.Alert_btnAccept) { _, _ -> signIn()}
                builder.setNegativeButton(getString(R.string.Alert_btnDecline)) { dialog, which -> dialog.cancel() }
            }
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}