package se.eelde.granter.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import se.eelde.granter.app.databinding.FragmentDummyBinding;

public class DummyFragment extends Fragment {
    public DummyFragment() {
    }

    public static Fragment newInstance() {
        return new DummyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentDummyBinding binding = FragmentDummyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
