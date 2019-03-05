package se.eelde.granter.app

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_permission_requesting.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import se.eelde.granter.Granter

/**
 * Created in [R.layout.activity_permission_requesting]
 */
class PermissionRequestingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_permission_requesting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.fragment_permission_1_button.setOnClickListener {
            Granter.Builder(this)
                    .requestCode(RC_READ_CONTACTS_PERM)
                    .addPermission(Manifest.permission.READ_CONTACTS)
                    .build()
                    .show()
        }

    }

    @AfterPermissionGranted(RC_READ_CONTACTS_PERM)
    private fun contactsTask() {
        Toast.makeText(activity, "TODO: Read contacts things", Toast.LENGTH_LONG).show()
    }

    companion object {

        private const val RC_READ_CONTACTS_PERM = 122

        internal fun newInstance(): Fragment {
            return PermissionRequestingFragment()
        }
    }
}
