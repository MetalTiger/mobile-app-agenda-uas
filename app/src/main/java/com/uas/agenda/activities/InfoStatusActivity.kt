package com.uas.agenda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.uas.agenda.api.model.DeclineData
import com.uas.agenda.api.model.LoginResponse
import com.uas.agenda.api.service.APIService
import com.uas.agenda.api.service.ServiceBuilder
import com.uas.agenda.databinding.ActivityInfoStatusBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InfoStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoStatusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datos: Bundle? = intent.extras

        if (datos != null) {
            setup(datos)

            binding.imageButtonAceptar.setOnClickListener {
                val confirmationIntent = Intent(this, ConfirmationActivity::class.java)
                    .putExtra("id",datos.getString("id"))


                startActivity(confirmationIntent)
                finish()

            }

            binding.imageButtonRechazar.setOnClickListener {
                val id = datos.getString("id")

                val info = DeclineData(id)

                val call = ServiceBuilder.buildService(APIService::class.java).declineDate(info)

                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val head = response.body()?.head

                        if (head != null) {

                            if (head.code == 200) {
                                Toast.makeText(this@InfoStatusActivity, head.message, Toast.LENGTH_LONG).show()
                                finish()

                            }else {
                                Toast.makeText(this@InfoStatusActivity, head.message, Toast.LENGTH_LONG).show()

                            }


                        } else {
                            Toast.makeText(this@InfoStatusActivity, "Error", Toast.LENGTH_LONG).show()
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@InfoStatusActivity, t.message, Toast.LENGTH_LONG).show()

                    }

                })

            }

        }else{
            Toast.makeText(this,"Error en los datos", Toast.LENGTH_LONG).show()
        }

    }

    private fun setup(datos: Bundle){

        val nombre = datos.getString("nombre") + " " + datos.getString("apellido1") + " " + datos.getString("apellido2")

        binding.textViewSolicitante.text = nombre
        binding.textViewMatricula.text = datos.getString("matricula")
        binding.textViewCarrera.text = datos.getString("carrera")
        binding.textViewCorreo.text = datos.getString("correo")
        binding.textViewResponsable.text = datos.getString("responsable")
        binding.textViewRazones.text = datos.getString("razones")

    }

    override fun onBackPressed() {
        finish()
    }

}