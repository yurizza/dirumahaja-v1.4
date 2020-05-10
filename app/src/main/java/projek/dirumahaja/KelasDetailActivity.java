package projek.dirumahaja;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import projek.dirumahaja.api.UploadTugasService;
import projek.dirumahaja.database.AppDatabase;
import projek.dirumahaja.database.FavoritModel;
import projek.dirumahaja.dialog.LoadingDialog;
import projek.dirumahaja.model.BaseResponse;
import projek.dirumahaja.model.User;
import projek.dirumahaja.model.uploadtugas.UploadTugasResponse;
import projek.dirumahaja.util.ImageUtils;
import projek.dirumahaja.util.PrefUtil;
import projek.dirumahaja.view.readFavorit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class KelasDetailActivity extends AppCompatActivity {
    private TextView tvNamaKelas, tvKodeKelas,tvKumpulanTugas;
    private ImageView ivUploadImage, ivUploadFile, ivSend, imgThumb;
    private EditText etTugas, etJudul;
    private ImageButton ibShareKelasKode;
    Fragment fragment;
    Toolbar toolbar;
    UploadTugasService uploadTugasService;
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_STORAGE = 2;
    LoadingDialog loadingDialog;

    private Uri uri = null;

    //roomdb
    private AppDatabase appDatabase;
    private ImageButton kelas_favorit;
//    private Button lihat_favorit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_detail);

        kelas_favorit = findViewById(R.id.ib_kelas_favorit);
//        lihat_favorit = findViewById(R.id.btn_lihat_favorit);

        loadingDialog = new LoadingDialog(KelasDetailActivity.this);

        final String strNamaKelas =getIntent().getStringExtra("namaKelas");
        final String strSubKelas = getIntent().getStringExtra("subKelas");
        final String strKodeKelas=getIntent().getStringExtra("kodeKelas");
        final String strPengajar = getIntent().getStringExtra("pengajar");
        final User user = PrefUtil.getUser(this, PrefUtil.USER_SESSION);
        final int idKelas = getIntent().getIntExtra("idKelas", 0);

        tvNamaKelas = (TextView) findViewById(R.id.tv_detail_nama_kelas);
        etJudul = (EditText) findViewById(R.id.et_judul);
        ibShareKelasKode = (ImageButton) findViewById(R.id.ib_share_kode_kelas);
        tvKodeKelas = (TextView) findViewById(R.id.tv_kode_kelas);
        etTugas = (EditText) findViewById(R.id.et_upload_tugas);
        ivUploadImage = (ImageView) findViewById(R.id.iv_upload_gambar);
        ivUploadFile = (ImageView) findViewById(R.id.iv_upload_file);
        imgThumb = (ImageView) findViewById(R.id.iv_imgthumb);
        ivSend = (ImageView) findViewById(R.id.iv_send);
        tvKumpulanTugas = (TextView) findViewById(R.id.tv_kumpulan_tugas);

        appDatabase = AppDatabase.initDatabase(getApplicationContext());

        tvNamaKelas.setText(strNamaKelas + " " + strSubKelas);

        if (!strKodeKelas.equals("")) {
            tvKodeKelas.setText("Kode Kelas : " + strKodeKelas);
        } else {
            tvKodeKelas.setText("");
        }
        tvKumpulanTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KelasDetailActivity.this, TugasAnggotaActivity.class);
                intent.putExtra("email", user.getData().getEmail());
                intent.putExtra("idKelas", idKelas);
                intent.putExtra("pengajar",strPengajar);
                intent.putExtra("namaKelas",strNamaKelas+" "+strSubKelas);
                startActivity(intent);
            }
        });
        ivUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        imgThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = null;
                imgThumb.setImageURI(uri);
            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAct();
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
                    Toast.makeText(KelasDetailActivity.this, "Hanya PENGAJAR yang bisa berbagi KODE KELAS!", LENGTH_SHORT).show();
                }
            }
        });
        kelas_favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    FavoritModel favoritModel = new FavoritModel();

                    favoritModel.setKodeKelas(tvKodeKelas.getText().toString());
                    favoritModel.setNamaKelas(strNamaKelas);
                    favoritModel.setSubKelas(strSubKelas);
                    favoritModel.setPengajar(strPengajar);
                    favoritModel.setIdKelas(idKelas);
                    favoritModel.setEmail(user.getData().getEmail());

                    appDatabase.favoritDAO().insertFavorit(favoritModel);

                    Log.e("KelasDetailActivity", "Berhasil");
                    Toast.makeText(getApplicationContext(), "Disukai", LENGTH_SHORT).show();

                } catch (Exception ex) {
                    Log.e("KelasDetailActivity", "gagal disukai, msg : " + ex.getMessage());
                    Toast.makeText(getApplicationContext(), "gagal disukai", LENGTH_SHORT).show();
                }
            }
        });
//        lihat_favorit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), readFavorit.class);
//                startActivity(intent);
//            }
//        });

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
        if (TextUtils.isEmpty(strJudulTugas)) {
            etJudul.setError("Cannot be empty !");
            return;
        }
        if (uri != null) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String encoded = ImageUtils.bitmapToBase64String(bitmap, 100);
            uploadBase64(encoded, strEmail, idKelas, strJudulTugas, strDeskripsi);
        } else {
            String encoded = null;
            uploadBase64(encoded, strEmail, idKelas, strJudulTugas, strDeskripsi);
        }
    }

    private void choosePhoto() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);

        } else {
            openGallery();
        }
    }

    private void uploadBase64(String imgBase64, String strEmail, int idKelas, String strJudulTugas, String strDeskripsi) {
        loadingDialog.startLoadingDialog();
        uploadTugasService = new UploadTugasService(this);
        uploadTugasService.setTugasService("upload", strEmail, idKelas, strJudulTugas, strDeskripsi, "2020-05-08 23:59:59", imgBase64, new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                UploadTugasResponse baseResponse = (UploadTugasResponse) response.body();
                if (baseResponse != null) {
                    Toast.makeText(KelasDetailActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(KelasDetailActivity.this, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(KelasDetailActivity.this, "entahlahh", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                imgThumb.setImageURI(uri);
                Toast.makeText(KelasDetailActivity.this, "TAP FILE untuk menghapus LAMPIRAN", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                }

                return;
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }


}