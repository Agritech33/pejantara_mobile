package com.agritech.pejantaraapp.ui.screen.bank_sampah

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.agritech.pejantaraapp.data.model.BankSampah

class BankSampahViewModel : ViewModel() {
    private val _bankSampahList = MutableLiveData<List<BankSampah>>()
    val bankSampahList: LiveData<List<BankSampah>> get() = _bankSampahList

    init {
        fetchBankSampahData()
    }
    private fun fetchBankSampahData() {
        _bankSampahList.value = listOf(
            BankSampah("Bank Sampah A", "Jalan A", 1.1234, 104.1234),
            BankSampah("Bank Sampah B", "Jalan B", 1.2234, 104.2234),
            BankSampah("Bank Sampah C", "Jalan C", 1.3234, 104.3234),
            BankSampah("Bank Sampah D", "Jalan D", 1.4234, 104.4234)
        )
    }
}
