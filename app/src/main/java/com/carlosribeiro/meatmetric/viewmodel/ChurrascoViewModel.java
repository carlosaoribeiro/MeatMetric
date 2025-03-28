package com.carlosribeiro.meatmetric.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.carlosribeiro.meatmetric.model.Churrasco;
import com.carlosribeiro.meatmetric.model.Churrasco.TipoChurrasco;
import com.carlosribeiro.meatmetric.util.CalculoUtil;

public class ChurrascoViewModel extends ViewModel {

    private final MutableLiveData<Churrasco> churrascoLiveData = new MutableLiveData<>();

    public LiveData<Churrasco> getChurrasco() {
        return churrascoLiveData;
    }

    public void calcularChurrasco(Churrasco.TipoChurrasco tipo, int numeroPessoas) {
        Churrasco resultado = CalculoUtil.calcularChurrasco(tipo, numeroPessoas);
        churrascoLiveData.setValue(resultado);
    }
}
