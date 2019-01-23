package se.eelde.granter.app;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import pub.devrel.easypermissions.AfterPermissionGranted;
import se.eelde.granter.Granter;
import se.eelde.granter.app.databinding.FragmentPermissionRequestingBinding;

/**
 * Created in {@link R.layout#activity_permission_requesting}
 */
public class PermissionRequestingFragment extends Fragment {

    private static final int RC_READ_CONTACTS_PERM = 122;

    public static Fragment newInstance() {
        return new PermissionRequestingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPermissionRequestingBinding binding = FragmentPermissionRequestingBinding.inflate(inflater, container, false);

        binding.fragmentPermission1Button.setOnClickListener(view -> new Granter.Builder(this)
                .requestCode(RC_READ_CONTACTS_PERM)
                .addPermission(Manifest.permission.READ_CONTACTS)
                .build()
                .show());

        return binding.getRoot();
    }

    @AfterPermissionGranted(RC_READ_CONTACTS_PERM)
    private void contactsTask() {
        Toast.makeText(getActivity(), "TODO: Read contacts things", Toast.LENGTH_LONG).show();
    }
}
