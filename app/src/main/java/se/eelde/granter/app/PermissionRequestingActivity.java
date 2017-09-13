package se.eelde.granter.app;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import se.eelde.granter.Granter;
import se.eelde.granter.app.databinding.ActivityPermissionRequestingBinding;

public class PermissionRequestingActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final int RC_CAMERA = 121;
    public static final int RC_2 = 122;
    public static final int RC_multiple = 123;
    private static final String TAG = "PermissionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPermissionRequestingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_permission_requesting);

        binding.permission1Button.setOnClickListener(view -> new Granter.Builder(this)
                .requestCode(RC_CAMERA)
                .addPermission(Manifest.permission.CAMERA)
                .build()
                .show());

        binding.permission2Button.setOnClickListener(view -> new Granter.Builder(this)
                .requestCode(RC_2)
                .addPermission(Manifest.permission.SEND_SMS)
                .build()
                .show());

        binding.permissionMultipleButton.setOnClickListener(view -> new Granter.Builder(this)
                .requestCode(RC_multiple)
                .addPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION)
                .rationale("This app neeeds access to audio and location!")
                .build()
                .show());

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(binding.fragmentContainer.getId(), PermissionRequestingFragment.newInstance())
                    .commit();
        }
    }

    @AfterPermissionGranted(RC_CAMERA)
    public void rc1() {
        Toast.makeText(this, "TODO: rc1 things", Toast.LENGTH_SHORT).show();
    }

    @AfterPermissionGranted(RC_2)
    public void rc2() {
        Toast.makeText(this, "TODO: rc2 things", Toast.LENGTH_SHORT).show();
    }

    @AfterPermissionGranted(RC_multiple)
    public void rc_multiple() {
        Toast.makeText(this, "TODO: rc_multiple things", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "onPermissionsGranted:" + requestCode + ":" + perms.size(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "onPermissionsDenied:" + requestCode + ":" + perms.size(), Toast.LENGTH_LONG).show();
    }
}
