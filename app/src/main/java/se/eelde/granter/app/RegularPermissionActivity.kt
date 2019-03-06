package se.eelde.granter.app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_regular_permission.*

class RegularPermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_permission)

        regular_permission_button.setOnClickListener { checkPermissions() }
    }

    private fun moveToNextStep() {
        supportFragmentManager
                .beginTransaction()
                .replace(fragment_container.id, DummyFragment.newInstance())
                .commit()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                        .setTitle("title")
                        .setMessage("message")
                        .setPositiveButton("ok") { _, _ -> ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION_RATIONALE) }
                        .setNegativeButton("nah", null)
                        .create()
                        .show()

            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
            }
        } else {
            moveToNextStep()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_LOCATION -> {
                run {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        moveToNextStep()
                    } else if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        AlertDialog.Builder(this)
                                .setTitle("title")
                                .setMessage("settings")
                                .setPositiveButton("ok", null)
                                .setNegativeButton("nah", null)
                                .create()
                                .show()

                    }
                }
                run {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        moveToNextStep()
                    }
                }
                run { super.onRequestPermissionsResult(requestCode, permissions, grantResults) }
            }
            REQUEST_PERMISSION_LOCATION_RATIONALE -> {
                run {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        moveToNextStep()
                    }
                }
                run { super.onRequestPermissionsResult(requestCode, permissions, grantResults) }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    companion object {
        private const val REQUEST_PERMISSION_LOCATION_RATIONALE = 213
        private const val REQUEST_PERMISSION_LOCATION = 212

        fun start(context: Context) {
            context.startActivity(Intent(context, RegularPermissionActivity::class.java))
        }
    }
}
