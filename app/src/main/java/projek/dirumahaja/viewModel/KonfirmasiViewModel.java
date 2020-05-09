package projek.dirumahaja.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import projek.dirumahaja.KonfirmasiTugas;
import projek.dirumahaja.api.KonfirmasiTugasService;
import projek.dirumahaja.api.KumpulTugasService;
import projek.dirumahaja.api.TugasService;
import projek.dirumahaja.model.kumpulantugas.DataItem;
import projek.dirumahaja.model.kumpulantugas.KumpulanTugasResponse;
import projek.dirumahaja.model.tugas.TugasResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiViewModel extends ViewModel {
    private KonfirmasiTugasService konfirmasiTugasService;

    private MutableLiveData<ArrayList<DataItem>> listMutableLiveData = new MutableLiveData<ArrayList<DataItem>>();

    public void setKonfirmasiTugas(String email,int idKelas,int idTugas){
        if (this.konfirmasiTugasService ==null){
            konfirmasiTugasService = new KonfirmasiTugasService();
        }
        konfirmasiTugasService.getKonfirmassiTugas().liatTugas("anggota",email,idKelas,idTugas).enqueue(new Callback<KumpulanTugasResponse>() {
            @Override
            public void onResponse(Call<KumpulanTugasResponse> call, Response<KumpulanTugasResponse> response) {
                KumpulanTugasResponse kumpulanTugasResponse = response.body();
                if (kumpulanTugasResponse!=null && !kumpulanTugasResponse.isError()){
                    ArrayList<DataItem> dataItems = kumpulanTugasResponse.getData();
                    listMutableLiveData.postValue(dataItems);
                }
            }
            @Override
            public void onFailure(Call<KumpulanTugasResponse> call, Throwable t) {
            }

        });
    }
    public LiveData<ArrayList<DataItem>> getData(){
        return listMutableLiveData;
    }
}
