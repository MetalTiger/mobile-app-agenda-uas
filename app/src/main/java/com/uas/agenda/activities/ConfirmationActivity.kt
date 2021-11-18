package com.uas.agenda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.uas.agenda.api.model.AcceptData
import com.uas.agenda.api.model.LoginResponse
import com.uas.agenda.api.service.APIService
import com.uas.agenda.api.service.ServiceBuilder
import com.uas.agenda.databinding.ActivityConfirmationBinding
import com.uas.agenda.main.TimePickerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir id del intent
        val datos: Bundle? = intent.extras

        binding.editTextTime.setOnClickListener { showTimePickerDialog() }

        binding.imageButtonConfirmar.setOnClickListener {

            if (binding.editTextTime.text.isEmpty()) {
                Toast.makeText(this, "Ingrese una hora!!!", Toast.LENGTH_LONG).show()

            } else {
                // Poner validación de horas aceptables

                if (datos != null) {
                    val id = datos.getString("id")

                    val info = AcceptData(id, binding.editTextTime.text.toString())

                    val call = ServiceBuilder.buildService(APIService::class.java).acceptDate(info)

                    call.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            val head = response.body()?.head

                            if (head != null) {

                                if (head.code == 200) {
                                    Toast.makeText(this@ConfirmationActivity, head.message, Toast.LENGTH_LONG).show()
                                    finish()

                                } else {
                                    Toast.makeText(this@ConfirmationActivity, head.message, Toast.LENGTH_LONG).show()

                                }


                            } else {
                                Toast.makeText(this@ConfirmationActivity, "Error", Toast.LENGTH_LONG).show()
                            }

                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(this@ConfirmationActivity, t.message, Toast.LENGTH_LONG).show()
                        }

                    })

                }

            }

        }

    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment{ time -> onTimeSelected(time)}
        timePicker.show(supportFragmentManager, "time")
    }


    private fun onTimeSelected(time: String) {
        binding.editTextTime.setText(time)

    }

    override fun onBackPressed() {
        finish()
    }
}