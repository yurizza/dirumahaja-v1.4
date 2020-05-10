package projek.dirumahaja.view;

import android.os.Bundle;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import projek.dirumahaja.FavoritAdapter;
import projek.dirumahaja.R;
import projek.dirumahaja.database.AppDatabase;
import projek.dirumahaja.database.FavoritModel;
import projek.dirumahaja.model.User;
import projek.dirumahaja.util.PrefUtil;

public class readFavorit extends AppCompatActivity {

    private FavoritAdapter favoritAdapter;
    private RecyclerView rvFavorit;
    private AppDatabase appDatabase;
    private ArrayList<FavoritModel> listFavorit = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_favorit);

        User user = PrefUtil.getUser(this,PrefUtil.USER_SESSION);
        rvFavorit = findViewById(R.id.rv_favorit);

        favoritAdapter = new FavoritAdapter(getApplicationContext());
        favoritAdapter.notifyDataSetChanged();

        if (appDatabase == null) {
            appDatabase = AppDatabase.initDatabase(getApplicationContext());
        }
        listFavorit.addAll(appDatabase.favoritDAO().getFavorit(user.getData().getEmail()));
        favoritAdapter.setData(listFavorit);

        rvFavorit.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvFavorit.setAdapter(favoritAdapter);

    }
}
