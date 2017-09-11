package se.eelde.granter;

import android.content.Context;
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
    private final Context context;
    private int requestCode;
    private String rationale = null;


    @SuppressWarnings("unused")
    public Granter(AppCompatActivity activity) {
        fragmentManager = activity.getSupportFragmentManager();
        context = activity;
    }

    @SuppressWarnings("unused")
    public Granter(Fragment fragment) {
        fragmentManager = fragment.getChildFragmentManager();
        context = fragment.getContext();
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

    public Granter rationale(@StringRes int rationale) {
        this.rationale = context.getString(rationale);
        return this;
    }

    public void show() {
        fragmentManager.beginTransaction().add(GranterFragment.newInstance(requestCode, permissions, rationale), PERMISSIONS_FRAGMENT_TAG).commit();
    }
}
