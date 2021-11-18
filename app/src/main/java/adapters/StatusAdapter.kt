package adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.uas.agenda.R
import com.uas.agenda.activities.ConfirmationActivity
import com.uas.agenda.activities.InfoStatusActivity
import com.uas.agenda.databinding.ListStatusBinding
import com.uas.agenda.api.model.Date
import com.uas.agenda.api.model.DeclineData
import com.uas.agenda.api.model.LoginResponse
import com.uas.agenda.api.service.APIService
import com.uas.agenda.api.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusAdapter(
    val listaStatus: MutableList<Date>)
    : RecyclerView.Adapter<StatusAdapter.ViewHolderStatus>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStatus {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_status, parent, false)
        return ViewHolderStatus(view)
    }

    override fun onBindViewHolder(holder: ViewHolderStatus, position: Int) {

        if (listaStatus[position].isStudent) {
            holder.binding.textViewdatos.text =
                "${listaStatus[position].firstName} ${listaStatus[position].lastName} - ${listaStatus[position].registrationNumber}"

        }else {
            holder.binding.textViewdatos.text =
                "${listaStatus[position].firstName} ${listaStatus[position].lastName} ${listaStatus[position].secondLastName}"
        }

        // Implementar onClick que habrá Intent de datos

        holder.itemView.setOnClickListener {
            val infoStatusIntent = Intent(holder.itemView.context, InfoStatusActivity::class.java).apply {
                putExtra("nombre", listaStatus[position].firstName)
                putExtra("apellido1", listaStatus[position].lastName)
                putExtra("apellido2", listaStatus[position].secondLastName)
                putExtra("matricula", listaStatus[position].registrationNumber)
                putExtra("carrera", listaStatus[position].career)
                putExtra("correo", listaStatus[position].email)
                putExtra("responsable", listaStatus[position].citedEmployee)
                putExtra("razones", listaStatus[position].reasons)
                putExtra("id", listaStatus[position].id)
            }

            holder.itemView.context.startActivity(infoStatusIntent)
        }

        holder.binding.imageButtonAceptar.setOnClickListener {
            //Toast.makeText(holder.itemView.context,"Seleccionó aceptar: ${listaStatus[position].nombre} - ${listaStatus[position].matricula}", Toast.LENGTH_SHORT).show()
            val confirmationIntent = Intent(holder.itemView.context, ConfirmationActivity::class.java)
                .putExtra("id", listaStatus[position].id)

            holder.itemView.context.startActivity(confirmationIntent)

        }

        holder.binding.imageButtonRechazar.setOnClickListener {
            val id = listaStatus[position].id

            val info = DeclineData(id)

            val call = ServiceBuilder.buildService(APIService::class.java).declineDate(info)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val head = response.body()?.head

                    if (head != null) {

                        if (head.code == 200) {
                            
                            val a = listaStatus[holder.adapterPosition]
                            listaStatus.remove(a)
                            notifyItemRemoved(holder.adapterPosition)


                        }else {

                            Toast.makeText(holder.itemView.context, head.message, Toast.LENGTH_LONG).show()

                        }

                    } else {
                        Toast.makeText(holder.itemView.context, "Error", Toast.LENGTH_LONG).show()

                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(holder.itemView.context, t.message, Toast.LENGTH_LONG).show()

                }

            })
            //Toast.makeText(holder.itemView.context,"Seleccionó rechazar: ${listaStatus[position].firstName} - ${listaStatus[position].registrationNumber}", Toast.LENGTH_SHORT).show()

        }


    }

    override fun getItemCount(): Int {
        return listaStatus.size
    }

    class ViewHolderStatus(view: View): RecyclerView.ViewHolder(view) {
        val binding = ListStatusBinding.bind(view)
    }
}