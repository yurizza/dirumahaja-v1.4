package projek.dirumahaja.api;

import projek.dirumahaja.api.config.Config;
import projek.dirumahaja.api.interfaces.KonfirmasiTugasInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonfirmasiTugasService {
    private Retrofit retrofit;
    public KonfirmasiTugasInterface getKonfirmassiTugas(){
        if (retrofit == null){
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(KonfirmasiTugasInterface.class);
    }
}
