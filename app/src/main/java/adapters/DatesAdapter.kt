package adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uas.agenda.R
import com.uas.agenda.activities.InfoDateActivity
import com.uas.agenda.databinding.ListDatesBinding
import com.uas.agenda.api.model.Date

class DatesAdapter(
    val listaDates: MutableList<Date>)
    : RecyclerView.Adapter<DatesAdapter.ViewHolderDates>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDates {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_dates, parent, false)
        return ViewHolderDates(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDates, position: Int) {

        if (listaDates[position].isStudent) {
            holder.binding.datosTextView.text =
                "${listaDates[position].firstName} ${listaDates[position].lastName} - ${listaDates[position].registrationNumber}"

        }else {
            holder.binding.datosTextView.text =
                "${listaDates[position].firstName} ${listaDates[position].lastName} ${listaDates[position].secondLastName}"
        }

        // Implementar onClick que habrá Intent de datos

        holder.itemView.setOnClickListener {
            val infoDateIntent = Intent(holder.itemView.context, InfoDateActivity::class.java).apply {
                putExtra("nombre", listaDates[position].firstName)
                putExtra("apellido1", listaDates[position].lastName)
                putExtra("apellido2", listaDates[position].secondLastName)
                putExtra("matricula", listaDates[position].registrationNumber)
                putExtra("carrera", listaDates[position].career)
                putExtra("correo", listaDates[position].email)
                putExtra("responsable", listaDates[position].citedEmployee)
                putExtra("razones", listaDates[position].reasons)
                putExtra("fecha", listaDates[position].date)

            }

            holder.itemView.context.startActivity(infoDateIntent)

        }


    }

    override fun getItemCount(): Int {
        return listaDates.size
    }

    class ViewHolderDates(view:View): RecyclerView.ViewHolder(view) {
        val binding = ListDatesBinding.bind(view)
    }
}