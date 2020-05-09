package projek.dirumahaja.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import projek.dirumahaja.KerjakanTugasActivity;
import projek.dirumahaja.R;
import projek.dirumahaja.model.User;
import projek.dirumahaja.model.kumpulantugas.DataItem;
import projek.dirumahaja.util.PrefUtil;

public class KonfirmasiTugasAdapter extends RecyclerView.Adapter<KonfirmasiTugasAdapter.ViewHolder> {
    private ArrayList<DataItem> dataItems=new ArrayList<>();
    private Context context;

    public KonfirmasiTugasAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<DataItem> items){
        dataItems.clear();
        dataItems.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KonfirmasiTugasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_konfirmasitugas,parent,false);
        return new KonfirmasiTugasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(dataItems.get(position).getNama());
        holder.tvDeskripsi.setText(dataItems.get(position).getDeskripsiJawaban());
        holder.tvTanggal.setText(dataItems.get(position).getTanggalUpload());
    }


    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        User user = PrefUtil.getUser(context, PrefUtil.USER_SESSION);
        TextView tvNama,tvDeskripsi,tvTanggal;

        LinearLayout layoutItemList;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvNama=itemView.findViewById(R.id.nama_konfirm);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal_konfirm);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi_konfirm);

            layoutItemList= itemView.findViewById(R.id.layout_item_list_konfirm);
            layoutItemList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent();
////
//                    intent = new Intent(context, KerjakanTugasActivity.class);
////                intent.putExtra("email",user.getData().getEmail());
////                    Toast.makeText(v.getContext(),user.getData().getEmail(), Toast.LENGTH_SHORT).show();
//                    intent.putExtra("idTugas",kelasItems.get(getAdapterPosition()).getIdTugas());
//                    intent.putExtra("namaKelas",kelasItems.get(getAdapterPosition()).getNamaKelas());
//                    intent.putExtra("subKelas",kelasItems.get(getAdapterPosition()).getSubKelas());
//                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}