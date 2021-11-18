package com.uas.agenda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.uas.agenda.databinding.ActivityInfoDateBinding

class InfoDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datos: Bundle? = intent.extras

        if (datos != null) {
            setup(datos)
        }else{
            Toast.makeText(this,"Error en los datos", Toast.LENGTH_LONG).show()
        }

    }

    private fun setup(datos: Bundle){

        val nombre = datos.getString("nombre")+ " " + datos.getString("apellido1") + " " + datos.getString("apellido2")

        binding.textViewSolicitante.text = nombre
        binding.textViewMatricula.text = datos.getString("matricula")
        binding.textViewCarrera.text = datos.getString("carrera")
        binding.textViewCorreo.text = datos.getString("correo")
        binding.textViewResponsable.text = datos.getString("responsable")
        binding.textViewFecha.text = datos.getString("fecha")
        binding.textViewRazones.text = datos.getString("razones")

    }

    override fun onBackPressed() {
        finish()
    }

}