package projek.dirumahaja;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import projek.dirumahaja.model.User;
import projek.dirumahaja.util.PrefUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ImageView profil, tugas, diskusi, kelas;
    TextView tvUsername,tvKontak,tvNomorMahasiswa;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Beranda");

        User user = PrefUtil.getUser(view.getContext(),PrefUtil.USER_SESSION);
        tvUsername = view.findViewById(R.id.tv_username);
        tvNomorMahasiswa = view.findViewById(R.id.tv_nomormahasiswa);
        tvKontak = view.findViewById(R.id.tv_kontak);
        tvUsername.setText(user.getData().getNama());
        tvNomorMahasiswa.setText(user.getData().getNomorMahasiswa());
        tvKontak.setText(user.getData().getEmail());

    }
}
