package projek.dirumahaja.model.kumpulantugas;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KumpulanTugasResponse{

    @SerializedName("data")
    private ArrayList<DataItem> data;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public ArrayList<DataItem> getData(){
        return data;
    }

    public boolean isError(){
        return error;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
        return
                "KumpulanTugasResponse{" +
                        "data = '" + data + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}