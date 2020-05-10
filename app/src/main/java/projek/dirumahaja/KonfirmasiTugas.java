package projek.dirumahaja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import projek.dirumahaja.adapter.KonfirmasiTugasAdapter;
import projek.dirumahaja.adapter.TugasAdapter;
import projek.dirumahaja.model.kumpulantugas.DataItem;
import projek.dirumahaja.model.tugas.KelasItem;
import projek.dirumahaja.viewModel.KonfirmasiViewModel;
import projek.dirumahaja.viewModel.TugasViewModel;

public class KonfirmasiTugas extends AppCompatActivity {
    private KonfirmasiTugasAdapter konfirmasiTugasAdapter;
    private RecyclerView rvTugas;
    private KonfirmasiViewModel konfirmasiViewModel;
    private TextView tvJudulTugas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_tugas);

        final int idKelas = getIntent().getIntExtra("idKelas", 0);
        final String email = getIntent().getStringExtra("email");
        final int idTugas= getIntent().getIntExtra("idTugas", 0);
        final String judulTugas = getIntent().getStringExtra("judulTugas");
        tvJudulTugas = findViewById(R.id.nama_tugas);
        tvJudulTugas.setText(judulTugas);
        tugasAct(email,idKelas,idTugas);
    }
    private void tugasAct(String email,int idKelas,int idTugas) {
        konfirmasiTugasAdapter = new KonfirmasiTugasAdapter(this);
        konfirmasiTugasAdapter.notifyDataSetChanged();

        rvTugas = findViewById(R.id.rv_konfirmasitugas);
        rvTugas.setLayoutManager(new GridLayoutManager(this, 1));

        konfirmasiViewModel = new ViewModelProvider(this).get(KonfirmasiViewModel.class);
        konfirmasiViewModel.setKonfirmasiTugas(email, idKelas,idTugas);
        konfirmasiViewModel.getData().observe(this, getData);
        rvTugas.setAdapter(konfirmasiTugasAdapter);
    }

    private Observer<ArrayList<DataItem>> getData = new Observer<ArrayList<DataItem>>() {
        @Override
        public void onChanged(ArrayList<DataItem> dataItems) {
            if (dataItems!= null)
                konfirmasiTugasAdapter.setData(dataItems);
        }
    };
}
