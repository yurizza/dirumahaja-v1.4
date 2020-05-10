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

import projek.dirumahaja.adapter.AdminTugasAdapter;
import projek.dirumahaja.adapter.TugasAdapter;
import projek.dirumahaja.model.tugas.KelasItem;
import projek.dirumahaja.viewModel.TugasViewModel;

public class TugasAnggotaActivity extends AppCompatActivity {
    private TugasAdapter tugasAdapter;
    private AdminTugasAdapter adminTugasAdapter;
    private RecyclerView rvTugas;
    private TugasViewModel tugasViewModel;
    private TextView tvNamaKelas;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas_anggota);

        final int idKelas = getIntent().getIntExtra("idKelas", 0);
        final String email = getIntent().getStringExtra("email");
        final String pengajar = getIntent().getStringExtra("pengajar");
        final String namaKelas = getIntent().getStringExtra("namaKelas");
        tvNamaKelas =(TextView) findViewById(R.id.tv_header);
        tvNamaKelas.setText(namaKelas);

        if(email.equals(pengajar))
            adminTugasAct(email,idKelas);
        else
            tugasAct(email,idKelas);

    }

    private void adminTugasAct(String email, int idKelas) {
        i=0;
        adminTugasAdapter = new AdminTugasAdapter(this);
        adminTugasAdapter.notifyDataSetChanged();

        rvTugas = findViewById(R.id.rv_list_tugas);
        rvTugas.setLayoutManager(new GridLayoutManager(this, 1));

        tugasViewModel = new ViewModelProvider(this).get(TugasViewModel.class);
        tugasViewModel.setTugas(email, idKelas);
        tugasViewModel.getKelas().observe(this, getKelas);
        rvTugas.setAdapter(adminTugasAdapter);
    }

    private void tugasAct(String email,int idKelas) {
        i=1;
        tugasAdapter = new TugasAdapter(this);
        tugasAdapter.notifyDataSetChanged();

        rvTugas = findViewById(R.id.rv_list_tugas);
        rvTugas.setLayoutManager(new GridLayoutManager(this, 1));

        tugasViewModel = new ViewModelProvider(this).get(TugasViewModel.class);
        tugasViewModel.setTugas(email, idKelas);
        tugasViewModel.getKelas().observe(this, getKelas);
        rvTugas.setAdapter(tugasAdapter);

    }

    private Observer<ArrayList<KelasItem>> getKelas = new Observer<ArrayList<KelasItem>>() {
        @Override
        public void onChanged(ArrayList<KelasItem> kelasItems) {
            if (kelasItems!= null) {
                if(i==1)
                    tugasAdapter.setData(kelasItems);
                else
                    adminTugasAdapter.setData(kelasItems);
            }
            else
                Toast.makeText(TugasAnggotaActivity.this, "kosong", Toast.LENGTH_SHORT).show();
        }
    };
}
