package se.eelde.granter.app

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_permission_requesting.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import se.eelde.granter.Granter

class PermissionRequestingActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_permission_requesting)

        regular_permission.setOnClickListener { RegularPermissionActivity.start(this) }

        permission_1_button.setOnClickListener {
            Granter.Builder(this)
                    .requestCode(RC_CAMERA)
                    .addPermission(Manifest.permission.CAMERA)
                    .build()
                    .show()
        }

        permission_2_button.setOnClickListener {
            Granter.Builder(this)
                    .requestCode(RC_2)
                    .addPermission(Manifest.permission.SEND_SMS)
                    .build()
                    .show()
        }

        permission_multiple_button.setOnClickListener {
            Granter.Builder(this)
                    .requestCode(RC_multiple)
                    .addPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION)
                    .rationale("This app neeeds access to audio and location!")
                    .build()
                    .show()
        }

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(fragment_container.id, PermissionRequestingFragment.newInstance())
                    .commit()
        }
    }

    @AfterPermissionGranted(RC_CAMERA)
    fun rc1() {
        Toast.makeText(this, "TODO: rc1 things", Toast.LENGTH_SHORT).show()

        supportFragmentManager
                .beginTransaction()
                .replace(fragment_container.id, DummyFragment.newInstance())
                .commit()
    }

    @AfterPermissionGranted(RC_2)
    fun rc2() {
        Toast.makeText(this, "TODO: rc2 things", Toast.LENGTH_SHORT).show()
    }

    @AfterPermissionGranted(RC_multiple)
    fun rc_multiple() {
        Toast.makeText(this, "TODO: rc_multiple things", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
    }

    companion object {
        private const val TAG = "PermissionActivity"
        private const val RC_CAMERA = 121
        private const val RC_2 = 122
        private const val RC_multiple = 123

    }
}

