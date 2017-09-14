package se.eelde.granter.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;

import se.eelde.granter.app.databinding.ActivityPermisoPermissionBinding;

public class PermisoPermissionActivity extends PermisoActivity {

    private ActivityPermisoPermissionBinding binding;

    public static void start(Context context) {
        context.startActivity(new Intent(context, PermisoPermissionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_permission);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permiso_permission);

        Permiso.getInstance().setActivity(this);

        binding.permisoPermission.setOnClickListener(view -> Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                moveToNextStep();
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                Permiso.getInstance().showRationaleInDialog("Title", "Message", null, callback);
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private void moveToNextStep() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.fragmentContainer.getId(), DummyFragment.newInstance())
                .commit();
    }
}
