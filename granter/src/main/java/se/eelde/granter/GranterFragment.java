package se.eelde.granter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class GranterFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final String ARGUMENT_PERMISSIONS_ARRAY = "argument_permissions_array";
    private static final String ARGUMENT_REQUEST_CODE = "argument_request_code";
    private static final String ARGUMENT_RATIONALE = "argument_rationale";
    private static final String ARGUMENT_SEND_USER_TO_SETTINGS = "argument_send_user_to_settings";
    private String[] requestedPermissions;
    private int requestCode;
    private String rationale;
    private boolean sendUserToSettings = true;

    static GranterFragment newInstance(ArrayList<String> permissions, int requestCode, String rationale, boolean sendUserToSettings) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARGUMENT_PERMISSIONS_ARRAY, permissions);
        args.putInt(ARGUMENT_REQUEST_CODE, requestCode);
        args.putString(ARGUMENT_RATIONALE, rationale);
        args.putBoolean(ARGUMENT_SEND_USER_TO_SETTINGS, sendUserToSettings);

        GranterFragment fragment = new GranterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> stringArrayList = getArguments().getStringArrayList(ARGUMENT_PERMISSIONS_ARRAY);
        //noinspection ConstantConditions
        requestedPermissions = new String[stringArrayList.size()];
        stringArrayList.toArray(requestedPermissions);
        requestCode = getArguments().getInt(ARGUMENT_REQUEST_CODE);
        rationale = getArguments().getString(ARGUMENT_RATIONALE, getString(R.string.permission_rationale_message));
        sendUserToSettings = getArguments().getBoolean(ARGUMENT_SEND_USER_TO_SETTINGS, true);

        checkPermission();
    }

    private void checkPermission() {
        if (!EasyPermissions.hasPermissions(getContext(), requestedPermissions)) {
            EasyPermissions.requestPermissions(this,
                    rationale,
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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (sendUserToSettings && EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void callback(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (getParentFragment() != null) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this, getParentFragment());
        } else if (getActivity() != null) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this, getActivity());
        }
    }
}
