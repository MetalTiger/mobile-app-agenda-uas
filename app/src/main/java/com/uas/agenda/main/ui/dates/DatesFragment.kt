package com.uas.agenda.main.ui.dates

import com.uas.agenda.api.service.APIService
import adapters.DatesAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uas.agenda.databinding.FragmentDatesBinding
import com.uas.agenda.api.model.Date
import com.uas.agenda.api.model.DatesResponse
import com.uas.agenda.api.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DatesFragment : Fragment() {

    private lateinit var datesViewModel: DatesViewModel
    private var _binding: FragmentDatesBinding? = null

    private lateinit var adapter: DatesAdapter
    private val acceptedDates = mutableListOf<Date>()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        datesViewModel = ViewModelProvider(this).get(DatesViewModel::class.java)

        _binding = FragmentDatesBinding.inflate(inflater, container, false)

        // La actualización de la lista y el llamada a la API se debe realizar aquí

        getDates()

        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = DatesAdapter(acceptedDates)
        binding.recyclerViewDates.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewDates.adapter = adapter
    }


    private fun getDates() {

        val call = ServiceBuilder.buildService(APIService::class.java).searchDates()

        call.enqueue(object : Callback<DatesResponse> {
            override fun onResponse(call: Call<DatesResponse>, response: Response<DatesResponse>) {
                Log.i("DatesResponse", response.body().toString())
                val dates = response.body()?.body?.response ?: emptyList()

                if (dates.isNotEmpty()){

                    acceptedDates.clear()
                    acceptedDates.addAll(dates.filter { date -> date.status == "accepted" })
                    //acceptedDates = dates.filter { date -> date.status == "accepted" }.toMutableList()
                    adapter.notifyDataSetChanged()

                    Log.i("DatesLista", acceptedDates.toString())

                } else {
                    Toast.makeText(context, "No ha confirmado ninguna cita", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<DatesResponse>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}