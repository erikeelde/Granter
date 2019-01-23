package se.eelde.granter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class GranterFragment extends Fragment implements EasyPermissions.PermissionCallbacks {

    private static final String ARGUMENT_PERMISSIONS_ARRAY = "argument_permissions_array";
    private static final String ARGUMENT_REQUEST_CODE = "argument_request_code";
    private static final String ARGUMENT_RATIONALE = "argument_rationale";
    private static final String ARGUMENT_SYSTEM_SETTINGS_RATIONALE = "argument_system_settings_rationale";
    private static final int RC_SETTINGS_DIALOG = AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE;
    private static final int RC_PERMISSIONS = 13267;
    private boolean shouldHaveShownRationale;
    private String[] requestedPermissions;
    private int requestCode;
    private String systemSettingsrationale;

    static GranterFragment newInstance(@NonNull ArrayList<String> permissions, int requestCode, @NonNull String rationale, @NonNull String systemSettingRationale) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARGUMENT_PERMISSIONS_ARRAY, permissions);
        args.putInt(ARGUMENT_REQUEST_CODE, requestCode);
        args.putString(ARGUMENT_RATIONALE, rationale);
        args.putString(ARGUMENT_SYSTEM_SETTINGS_RATIONALE, systemSettingRationale);

        GranterFragment fragment = new GranterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getContext();
        if (context == null) {
            throw new IllegalStateException("Context cannot be null");
        }
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalStateException("Arguments cannot be null");
        }
        ArrayList<String> stringArrayList = arguments.getStringArrayList(ARGUMENT_PERMISSIONS_ARRAY);

        //noinspection ConstantConditions
        requestedPermissions = new String[stringArrayList.size()];
        stringArrayList.toArray(requestedPermissions);
        requestCode = arguments.getInt(ARGUMENT_REQUEST_CODE);
        String rationale = arguments.getString(ARGUMENT_RATIONALE, getString(R.string.permission_rationale_message));
        systemSettingsrationale = arguments.getString(ARGUMENT_SYSTEM_SETTINGS_RATIONALE, getString(R.string.permission_system_settings_rationale_message));

        shouldHaveShownRationale = Stolen.shouldShowRationale(this, requestedPermissions);

        if (savedInstanceState == null) {
            if (!EasyPermissions.hasPermissions(getContext(), requestedPermissions)) {
                EasyPermissions.requestPermissions(this,
                        rationale,
                        RC_PERMISSIONS,
                        requestedPermissions);
            } else {
                int[] ints = new int[requestedPermissions.length];
                Arrays.fill(ints, PackageManager.PERMISSION_GRANTED);
                callback(requestedPermissions, ints);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // with no ui show we would get a null savedInstanceState in onCreate and
        // would not be able to distinguish between new creates and recreates of the fragment.
        outState.putBoolean("state_recreated", true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int internalRequestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SETTINGS_DIALOG == internalRequestCode) {
            callback(requestedPermissions, Stolen.grantsFromPermissions(this, requestedPermissions));
        }
    }

    @SuppressWarnings("unused")
    @AfterPermissionGranted(RC_PERMISSIONS)
    public void allPermissionsGranted() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            new Handler(Looper.getMainLooper())
                    .post(new Runnable() {
                              @Override
                              public void run() {
                                  Stolen.callAnnotations(getCallee(), requestCode);

                              }
                          }
                    );
        } else {
            Stolen.callAnnotations(getCallee(), requestCode);
        }
    }

    @Override
    public void onPermissionsGranted(int internalRequestCode, @NonNull final List<String> perms) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            new Handler(Looper.getMainLooper())
                    .post(new Runnable() {
                              @Override
                              public void run() {
                                  Stolen.callGrantedCallback(getCallee(), requestCode, perms);
                              }
                          }
                    );
        } else {
            Stolen.callGrantedCallback(getCallee(), requestCode, perms);
        }
    }

    @Override
    public void onPermissionsDenied(int internalRequestCode, @NonNull final List<String> perms) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            // fix to be able to do fragment transactions in the permission callbacks
            new Handler(Looper.getMainLooper())
                    .post(new Runnable() {
                              @Override
                              public void run() {
                                  if (!shouldHaveShownRationale && EasyPermissions.somePermissionPermanentlyDenied(GranterFragment.this, perms)) {
                                      new AppSettingsDialog.Builder(GranterFragment.this).setRationale(systemSettingsrationale).setRequestCode(RC_SETTINGS_DIALOG).build().show();
                                  } else {
                                      Stolen.callDeniedCallback(getCallee(), requestCode, perms);
                                  }
                              }
                          }
                    );
        } else {
            if (!shouldHaveShownRationale && EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this).setRationale(systemSettingsrationale).setRequestCode(RC_SETTINGS_DIALOG).build().show();
            } else {
                Stolen.callDeniedCallback(getCallee(), requestCode, perms);
            }
        }
    }

    @NonNull
    public Object getCallee() {
        if (getParentFragment() != null) {
            return getParentFragment();
        } else if (getActivity() != null) {
            return getActivity();
        } else {
            throw new IllegalStateException("Couldn't find callee for callback.");
        }
    }

    private void callback(@NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getCallee());
    }
}
