package com.uas.agenda.main.ui.status


import adapters.StatusAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uas.agenda.databinding.FragmentStatusBinding
import com.uas.agenda.api.service.APIService
import com.uas.agenda.api.model.Date
import com.uas.agenda.api.model.DatesResponse
import com.uas.agenda.api.service.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusFragment : Fragment() {

    private lateinit var statusViewModel: StatusViewModel
    private var _binding: FragmentStatusBinding? = null

    private lateinit var adapter: StatusAdapter
    private var pendingDates = mutableListOf<Date>()

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        statusViewModel = ViewModelProvider(this).get(StatusViewModel::class.java)

        _binding = FragmentStatusBinding.inflate(inflater, container, false)

        getDates()

        initRecyclerView()


        return binding.root
    }

    private fun initRecyclerView() {
        adapter = StatusAdapter(pendingDates)
        binding.recyclerViewStatus.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewStatus.adapter = adapter
    }

    private fun getDates() {

        val call = ServiceBuilder.buildService(APIService::class.java).searchDates()

        call.enqueue(object : Callback<DatesResponse> {
            override fun onResponse(call: Call<DatesResponse>, response: Response<DatesResponse>) {
                Log.i("Status", response.body().toString())
                val dates = response.body()?.body?.response ?: emptyList()  // Citas en general

                if (dates.isNotEmpty()){
                    pendingDates.clear()
                    pendingDates.addAll(dates.filter { date -> date.status == "pending" })

                    if (pendingDates.isEmpty()){
                        Toast.makeText(context, "No hay citas pendientes", Toast.LENGTH_SHORT).show()
                    }

                    //pendingDates = dates.filter { date -> date.status == "pending" }.toMutableList()
                    adapter.notifyDataSetChanged()

                    Log.i("StatusLista", pendingDates.toString())

                } else {
                    Toast.makeText(context, "No hay citas", Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()

        getDates()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}