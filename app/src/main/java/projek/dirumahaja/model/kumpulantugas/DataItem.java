package projek.dirumahaja.model.kumpulantugas;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("idUser")
    private int idUser;
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;
    @SerializedName("lampiran")
    private Object lampiran;
    @SerializedName("tanggalUpload")
    private String tanggalUpload;
    @SerializedName("deskripsiJawaban")
    private String deskripsiJawaban;
    public int getIdUser(){
        return idUser;
    }
    public String getNama(){
        return nama;
    }
    public String getEmail(){
        return email;
    }
    public Object getLampiran(){
        return lampiran;
    }
    public String getTanggalUpload(){
        return tanggalUpload;
    }
    public String getDeskripsiJawaban(){
        return deskripsiJawaban;
    }
    @Override
    public String toString(){
        return
                "DataItem{" +
                        "idUser = '" + idUser + '\'' +
                        ",nama = '" + nama + '\'' +
                        ",email = '" + email + '\'' +
                        ",lampiran = '" + lampiran + '\'' +
                        ",tanggalUpload = '" + tanggalUpload + '\'' +
                        ",deskripsiJawaban = '" + deskripsiJawaban + '\'' +
                        "}";
    }
}