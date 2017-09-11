package se.eelde.granter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import pub.devrel.easypermissions.EasyPermissions;

public class GranterFragment extends Fragment {

    private static final String ARGUMENT_PERMISSIONS_ARRAY = "argument_permissions_array";
    private static final String ARGUMENT_REQUEST_CODE = "argument_request_code";
    private String[] requestedPermissions;
    private int requestCode;

    static GranterFragment newInstance(int requestCode, ArrayList<String> permissions) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARGUMENT_PERMISSIONS_ARRAY, permissions);
        args.putInt(ARGUMENT_REQUEST_CODE, requestCode);

        GranterFragment fragment = new GranterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> stringArrayList = getArguments().getStringArrayList(ARGUMENT_PERMISSIONS_ARRAY);
        requestedPermissions = new String[stringArrayList.size()];
        stringArrayList.toArray(requestedPermissions);
        requestCode = getArguments().getInt(ARGUMENT_REQUEST_CODE);

        checkPermission();
    }

    private void checkPermission() {
        if (!EasyPermissions.hasPermissions(getContext(), requestedPermissions)) {
            EasyPermissions.requestPermissions(this,
                    getString(R.string.permission_rationale_message),
                    requestCode,
                    requestedPermissions);
        } else {
            int[] ints = new int[requestedPermissions.length];
            Arrays.fill(ints, PackageManager.PERMISSION_GRANTED);
            callback(requestCode, requestedPermissions, ints);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        callback(requestCode, permissions, grantResults);
    }

    private void callback(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (getParentFragment() != null) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getParentFragment());
        } else if (getActivity() != null) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getActivity());
        }
    }
}
