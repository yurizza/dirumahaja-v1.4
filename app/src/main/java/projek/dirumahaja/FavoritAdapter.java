package projek.dirumahaja;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import projek.dirumahaja.database.AppDatabase;
import projek.dirumahaja.database.FavoritModel;
import projek.dirumahaja.model.User;
import projek.dirumahaja.util.PrefUtil;

import static android.widget.Toast.LENGTH_SHORT;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FavoritModel> favoritItems = new ArrayList<>();
    private AppDatabase appDatabase;

    public FavoritAdapter(Context context) {
        this.context = context;
        appDatabase = AppDatabase.initDatabase(this.context);
    }

    public void setData(ArrayList<FavoritModel> items) {
        favoritItems.clear();
        favoritItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_kelas_favorit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvKode.setText(favoritItems.get(position).getKodeKelas());
        holder.tvNama.setText(favoritItems.get(position).getNamaKelas());
        holder.tvSub.setText(favoritItems.get(position).getSubKelas());
        holder.tvPengajar.setText(favoritItems.get(position).getPengajar());

        holder.btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FavoritModel favoritModel = new FavoritModel();

                    favoritModel.setKodeKelas(favoritItems.get(position).getKodeKelas());
                    favoritModel.setNamaKelas(favoritItems.get(position).getNamaKelas());
                    favoritModel.setSubKelas(favoritItems.get(position).getSubKelas());
                    favoritModel.setPengajar(favoritItems.get(position).getPengajar());
                    favoritModel.setId(favoritItems.get(position).getId());

                    appDatabase.favoritDAO().hapusFavorit(favoritModel);

                    Log.e("KelasDetailAdapter", "Dihapus");
                    Toast.makeText(context, "Dihapus", LENGTH_SHORT).show();

                } catch (Exception ex) {
                    Log.e("KelasDetailAdapter", "gagal dihapus msg : " + ex.getMessage());
                    Toast.makeText(context, "gagal dihapus", LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnhapus;
        TextView tvKode, tvNama, tvSub, tvPengajar;
        LinearLayout layoutItemList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnhapus = itemView.findViewById(R.id.btn_hapus);
            tvKode = itemView.findViewById(R.id.itemlist_kodekelas);
            tvNama = itemView.findViewById(R.id.itemlist_namakelas);
            tvSub = itemView.findViewById(R.id.itemlist_subkelas);
            tvPengajar = itemView.findViewById(R.id.itemlist_pengajar);

            layoutItemList = itemView.findViewById(R.id.layout_item_list_favorit);
            layoutItemList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = PrefUtil.getUser(v.getContext(),PrefUtil.USER_SESSION);
                    Intent intent = new Intent();
                    if (favoritItems.get(getAdapterPosition()).getPengajar().equals(user.getData().getEmail()))
                        intent = new Intent(context, KelasDetailActivity.class);
                    else
                        intent = new Intent(context, KelasDetailAnggotaActivity.class);

                    intent.putExtra("email", user.getData().getEmail());
//                    Toast.makeText(v.getContext(),user.getData().getEmail(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("idKelas", favoritItems.get(getAdapterPosition()).getIdKelas());
                    intent.putExtra("namaKelas", favoritItems.get(getAdapterPosition()).getNamaKelas());
                    intent.putExtra("subKelas", favoritItems.get(getAdapterPosition()).getSubKelas());
                    intent.putExtra("kodeKelas", favoritItems.get(getAdapterPosition()).getKodeKelas());
                    intent.putExtra("pengajar", favoritItems.get(getAdapterPosition()).getPengajar());
                    v.getContext().startActivity(intent);
                }
            });

        }
    }
}
