package projek.dirumahaja.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_favorit")
public class FavoritModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "idKelas")
    private int idKelas;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "namaKelas")
    private String namaKelas;

    @ColumnInfo(name = "subKelas")
    private String subKelas;

    @ColumnInfo(name = "kodeKelas")
    private String kodeKelas;

    @ColumnInfo(name = "pengajar")
    private String pengajar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getSubKelas() {
        return subKelas;
    }

    public void setSubKelas(String subKelas) {
        this.subKelas = subKelas;
    }

    public String getKodeKelas() {
        return kodeKelas;
    }

    public void setKodeKelas(String kodeKelas) {
        this.kodeKelas = kodeKelas;
    }

    public String getPengajar() {
        return pengajar;
    }

    public void setPengajar(String pengajar) {
        this.pengajar = pengajar;
    }
}
