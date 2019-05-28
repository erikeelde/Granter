package se.eelde.granter.app.coroutine

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import se.eelde.granter.Granter
import se.eelde.granter.app.R
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private var channel: Channel<Boolean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        button.setOnClickListener {

            uiScope.launch {
                if (userGrantedLocationPermission()) {
                    resultTextView.text = "${resultTextView.text} \n\npermission granted"
                } else {
                    resultTextView.text = "${resultTextView.text} \n\npermission denied"

                }
            }
        }
    }

    private suspend fun userGrantedLocationPermission(): Boolean {

        channel = Channel()

        Granter.Builder(this).addPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .build()
                .show()

        return suspendCoroutine { cont ->
            uiScope.launch {
                channel?.consumeEach { result -> cont.resume(result) }
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        uiScope.launch { channel?.send(true) }

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        uiScope.launch { channel?.send(false) }
    }
}
