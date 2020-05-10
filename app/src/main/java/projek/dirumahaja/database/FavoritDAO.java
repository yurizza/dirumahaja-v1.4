package projek.dirumahaja.database;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoritDAO {

    @Insert
    long insertFavorit(FavoritModel favoritModel);

    @Delete
    int hapusFavorit(FavoritModel favoritModel);

    @Query("SELECT *FROM data_favorit WHERE email=:email")
    List<FavoritModel> getFavorit(String email);
}
