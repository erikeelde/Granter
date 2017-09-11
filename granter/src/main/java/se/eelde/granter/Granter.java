package se.eelde.granter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Granter {
    private static final String PERMISSIONS_FRAGMENT_TAG = "permissions_fragment_tag";
    private final FragmentManager fragmentManager;
    private final ArrayList<String> permissions = new ArrayList<>();
    private int requestCode;


    @SuppressWarnings("unused")
    public Granter(AppCompatActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
    }

    @SuppressWarnings("unused")
    public Granter(Fragment fragment) {
        fragmentManager = fragment.getChildFragmentManager();
    }

    public Granter requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Granter addPermission(String... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
        return this;
    }

    public void show() {
        fragmentManager.beginTransaction().add(GranterFragment.newInstance(requestCode, permissions), PERMISSIONS_FRAGMENT_TAG).commit();
    }
}
