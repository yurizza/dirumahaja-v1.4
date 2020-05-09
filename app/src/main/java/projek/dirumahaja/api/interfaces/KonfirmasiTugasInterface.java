package projek.dirumahaja.api.interfaces;

import projek.dirumahaja.api.config.Config;
import projek.dirumahaja.model.kumpulantugas.KumpulanTugasResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KonfirmasiTugasInterface {
    @FormUrlEncoded
    @POST(Config.API_TUGAS)
    Call<KumpulanTugasResponse> liatTugas(
            @Field("action") String action,
            @Field("email") String email,
            @Field("idKelas") int idKelas,
            @Field("idTugas") int idTugas);
}
