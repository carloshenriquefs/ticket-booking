package br.com.fiap.ticket.ViewModel

import androidx.lifecycle.LiveData
import br.com.fiap.ticket.Domain.FlightModel
import br.com.fiap.ticket.Domain.LocationModel
import br.com.fiap.ticket.Repository.MainRepository

class MainViewModel {
    private val repository = MainRepository()

    fun loadLocations(): LiveData<MutableList<LocationModel>> {
        return repository.loadLocation()
    }

    fun loadFiltered(from: String, to: String):
            LiveData<MutableList<FlightModel>> {
        return repository.loadFiltered(from, to)
    }
}