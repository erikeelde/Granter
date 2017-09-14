package se.eelde.granter.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import se.eelde.granter.app.databinding.ActivityRegularPermissionBinding;

public class RegularPermissionActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_LOCATION_RATIONALE = 213;
    private static final int REQUEST_PERMISSION_LOCATION = 212;
    private ActivityRegularPermissionBinding binding;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RegularPermissionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_permission);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regular_permission);

        binding.regularPermissionButton.setOnClickListener(view -> checkPermissions());
    }

    private void moveToNextStep() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.fragmentContainer.getId(), DummyFragment.newInstance())
                .commit();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("title")
                        .setMessage("message")
                        .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION_RATIONALE))
                        .setNegativeButton("nah", null)
                        .create()
                        .show();

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            }
        } else {
            moveToNextStep();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveToNextStep();
                } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new AlertDialog.Builder(this)
                            .setTitle("title")
                            .setMessage("settings")
                            .setPositiveButton("ok", null)
                            .setNegativeButton("nah", null)
                            .create()
                            .show();

                }
            }
            case REQUEST_PERMISSION_LOCATION_RATIONALE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    moveToNextStep();
                }
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}
