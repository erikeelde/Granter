package se.eelde.granter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Granter {
    private static final String PERMISSIONS_FRAGMENT_TAG = "permissions_fragment_tag";
    private final FragmentManager fragmentManager;
    private final ArrayList<String> permissions = new ArrayList<>();
    private final Resources resources;
    private int requestCode;
    private String rationale = null;
    private boolean sendUserToSettings = true;


    @SuppressWarnings("unused")
    public Granter(AppCompatActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
        resources = activity.getResources();
    }

    @SuppressWarnings("unused")
    public Granter(Fragment fragment) {
        fragmentManager = fragment.getChildFragmentManager();
        resources = fragment.getResources();
    }

    public Granter requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Granter addPermission(String... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
        return this;
    }

    public Granter rationale(String rationale) {
        this.rationale = rationale;
        return this;
    }

    @SuppressWarnings("unused")
    public Granter rationale(@StringRes int rationale) {
        this.rationale = resources.getString(rationale);
        return this;
    }

    public Granter sendUserToSettings(boolean sendUserToSettings) {
        this.sendUserToSettings = sendUserToSettings;
        return this;
    }

    public void show() {
        fragmentManager.beginTransaction().add(GranterFragment.newInstance(permissions, requestCode, rationale, sendUserToSettings), PERMISSIONS_FRAGMENT_TAG).commit();
    }
}
