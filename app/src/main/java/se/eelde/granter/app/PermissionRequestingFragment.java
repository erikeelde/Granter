package se.eelde.granter.app;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pub.devrel.easypermissions.AfterPermissionGranted;
import se.eelde.granter.Granter;

/**
 * Created in {@link R.layout#activity_permission_requesting}
 */
public class PermissionRequestingFragment extends Fragment {

    private static final int RC_READ_CONTACTS_PERM = 122;

    static Fragment newInstance() {
        return new PermissionRequestingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission_requesting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.fragment_permission_1_button).setOnClickListener(button -> new Granter.Builder(this)
                .requestCode(RC_READ_CONTACTS_PERM)
                .addPermission(Manifest.permission.READ_CONTACTS)
                .build()
                .show());

    }

    @AfterPermissionGranted(RC_READ_CONTACTS_PERM)
    private void contactsTask() {
        Toast.makeText(getActivity(), "TODO: Read contacts things", Toast.LENGTH_LONG).show();
    }
}
