package projek.dirumahaja;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import projek.dirumahaja.database.AppDatabase;
import projek.dirumahaja.database.FavoritModel;
import projek.dirumahaja.model.User;
import projek.dirumahaja.util.PrefUtil;

import static android.widget.Toast.LENGTH_SHORT;

public class KelasDetailAnggotaActivity extends AppCompatActivity {

    private EditText etJudul, etTugas;
    private TextView tvTugas, tvJudul, tvDeskripsi, tvNamaKelas, tvKodeKelas;
    private ImageView ivSend, ivUploadImage, ivUploadFile;
    private ImageButton ibShareKelasKode;

    //roomdb
    private AppDatabase appDatabase;
    private ImageButton kelas_favorit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_detail_anggota);

        kelas_favorit = findViewById(R.id.ib_kelas_favorit);

        final String strNamaKelas = getIntent().getStringExtra("namaKelas");
        final String strSubKelas = getIntent().getStringExtra("subKelas");
        final String strKodeKelas = getIntent().getStringExtra("kodeKelas");
        final String strPengajar = getIntent().getStringExtra("pengajar");
        final User user = PrefUtil.getUser(this, PrefUtil.USER_SESSION);
        final int idKelas = getIntent().getIntExtra("idKelas", 0);

        appDatabase = AppDatabase.initDatabase(getApplicationContext());
        tvNamaKelas = (TextView) findViewById(R.id.tv_detail_nama_kelas);
        etJudul = (EditText) findViewById(R.id.et_judul);
        ibShareKelasKode = (ImageButton) findViewById(R.id.ib_share_kode_kelas);
        tvKodeKelas = (TextView) findViewById(R.id.tv_kode_kelas);
        etTugas = (EditText) findViewById(R.id.et_upload_tugas);
        ivUploadImage = (ImageView) findViewById(R.id.iv_upload_gambar);
        ivUploadFile = (ImageView) findViewById(R.id.iv_upload_file);
        ivSend = (ImageView) findViewById(R.id.iv_send);

        tvNamaKelas.setText(strNamaKelas + " " + strSubKelas);

        if (!strKodeKelas.equals("")) {
            tvKodeKelas.setText("Kode Kelas : " + strKodeKelas);
        } else {
            tvKodeKelas.setText("");
        }

        tvTugas = findViewById(R.id.tv_tugas);
        tvTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KelasDetailAnggotaActivity.this, TugasAnggotaActivity.class);
                intent.putExtra("email", user.getData().getEmail());
                intent.putExtra("idKelas", idKelas);
                intent.putExtra("pengajar",strPengajar);
                intent.putExtra("namaKelas",strNamaKelas+" "+strSubKelas);
                startActivity(intent);
            }
        });
        ibShareKelasKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share kode kelas to another app
                String strEmail = getIntent().getStringExtra("email");
                if (strEmail.equals(strPengajar)) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, strNamaKelas + " " + strSubKelas + "\nKode Kelas : " + strKodeKelas);
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                } else {
                    Toast.makeText(KelasDetailAnggotaActivity.this, "Hanya PENGAJAR yang bisa berbagi KODE KELAS!", LENGTH_SHORT).show();
                }
            }
        });
        kelas_favorit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                try {

                    FavoritModel favoritModel = new FavoritModel();

                    favoritModel.setKodeKelas("");
                    favoritModel.setNamaKelas(strNamaKelas);
                    favoritModel.setSubKelas(strSubKelas);
                    favoritModel.setPengajar(strPengajar);
                    favoritModel.setIdKelas(idKelas);
                    favoritModel.setEmail(user.getData().getEmail());

                    appDatabase.favoritDAO().insertFavorit(favoritModel);

                    Log.e("KelasDetailAnggotaActivity", "Berhasil");
                    Toast.makeText(getApplicationContext(), "Disukai", LENGTH_SHORT).show();

                } catch (Exception ex) {
                    Log.e("KelasDetailAnggotaActivity", "gagal disukai, msg : " + ex.getMessage());
                    Toast.makeText(getApplicationContext(), "gagal disukai", LENGTH_SHORT).show();
                }
            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAct();
            }
        });
    }

    public void sendAct() {
        String strEmail = getIntent().getStringExtra("email");
        int idKelas = getIntent().getIntExtra("idKelas", 0);
        String strDeskripsi = etTugas.getText().toString();
        String strJudulTugas = etJudul.getText().toString();
        if (TextUtils.isEmpty(strDeskripsi)) {
            etTugas.setError("Cannot be empty !");
            return;
        }
    }


//tutup
}
