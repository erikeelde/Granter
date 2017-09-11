package se.eelde.granter.app;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import se.eelde.granter.Granter;
import se.eelde.granter.app.databinding.FragmentPermissionRequestingBinding;

/**
 * Created in {@link R.layout#activity_permission_requesting}
 */
public class PermissionRequestingFragment extends Fragment {

    private static final String TAG = "MainFragment";
    private static final int RC_READ_CONTACTS_PERM = 122;

    public static Fragment newInstance() {
        return new PermissionRequestingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentPermissionRequestingBinding binding = FragmentPermissionRequestingBinding.inflate(inflater, container, false);

        binding.fragmentPermission1Button.setOnClickListener(view -> new Granter(this)
                .requestCode(RC_READ_CONTACTS_PERM)
                .addPermission(Manifest.permission.READ_CONTACTS)
                .show());

        return binding.getRoot();
    }

    @AfterPermissionGranted(RC_READ_CONTACTS_PERM)
    private void contactsTask() {
        Toast.makeText(getActivity(), "TODO: Read contacts things", Toast.LENGTH_LONG).show();
    }
}
