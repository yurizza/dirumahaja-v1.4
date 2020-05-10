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
import projek.dirumahaja.KonfirmasiTugas;
import projek.dirumahaja.R;
import projek.dirumahaja.model.User;
import projek.dirumahaja.model.tugas.KelasItem;
import projek.dirumahaja.util.PrefUtil;

public class AdminTugasAdapter extends RecyclerView.Adapter<AdminTugasAdapter.ViewHolder> {
    private ArrayList<KelasItem> kelasItems=new ArrayList<>();
    private Context context;

    public AdminTugasAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<KelasItem> items){
        kelasItems.clear();
        kelasItems.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdminTugasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_list_tugas,parent,false);
        return new AdminTugasAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvJudul.setText(kelasItems.get(position).getJudulTugas());
        holder.tvDeskripsi.setText(kelasItems.get(position).getDeskripsiTugas());
        holder.tvTenggat.setText(kelasItems.get(position).getTanggalUpload());
        holder.tvTanggal.setText(kelasItems.get(position).getTanggalTenggat());
        holder.tvKerjakan.setText("Lihat Anggota yg Mengerjakan >>");
    }

    @Override
    public int getItemCount() {
        return kelasItems.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        User user = PrefUtil.getUser(context, PrefUtil.USER_SESSION);
        TextView tvHeader,tvJudul,tvDeskripsi,tvTanggal,tvTenggat,tvNamaKelas,tvSubKelas,tvSelesai,tvKerjakan;
        CardView cvKelas;
        LinearLayout layoutItemList;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvTanggal = itemView.findViewById(R.id.tv_upload);
            tvTenggat = itemView.findViewById(R.id.tv_tenggat);
            tvKerjakan = itemView.findViewById(R.id.tv_kerjakan);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            layoutItemList= itemView.findViewById(R.id.layout_item_list_tugas);
            layoutItemList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();

                    intent = new Intent(context, KonfirmasiTugas.class);
                    intent.putExtra("email",user.getData().getEmail());
//                    Toast.makeText(v.getContext(),user.getData().getEmail(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("idTugas",kelasItems.get(getAdapterPosition()).getIdTugas());
                    intent.putExtra("idKelas",kelasItems.get(getAdapterPosition()).getIdKelas());
                    intent.putExtra("judulTugas",kelasItems.get(getAdapterPosition()).getJudulTugas());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}