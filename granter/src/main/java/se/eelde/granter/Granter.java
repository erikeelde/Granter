package se.eelde.granter;

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
    private final ArrayList<String> permissions;
    private final int requestCode;
    private final String rationale;
    private final boolean sendUserToSettings;

    private Granter(FragmentManager fragmentManager, ArrayList<String> permissions, int requestCode, String rationale, boolean sendUserToSettings) {
        this.fragmentManager = fragmentManager;
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.rationale = rationale;
        this.sendUserToSettings = sendUserToSettings;
    }

    public void show() {
        fragmentManager.beginTransaction().add(GranterFragment.newInstance(permissions, requestCode, rationale, sendUserToSettings), PERMISSIONS_FRAGMENT_TAG).commit();
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private final FragmentManager fragmentManager;
        private final ArrayList<String> permissions = new ArrayList<>();
        private final Resources resources;
        private int requestCode;
        private String rationale = null;
        private boolean sendUserToSettings = true;

        public Builder(AppCompatActivity activity) {
            fragmentManager = activity.getSupportFragmentManager();
            resources = activity.getResources();
        }

        public Builder(Fragment fragment) {
            fragmentManager = fragment.getChildFragmentManager();
            resources = fragment.getResources();
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Builder addPermission(String... permissions) {
            this.permissions.addAll(Arrays.asList(permissions));
            return this;
        }

        public Builder rationale(String rationale) {
            this.rationale = rationale;
            return this;
        }

        public Builder rationale(@StringRes int rationale) {
            this.rationale = resources.getString(rationale);
            return this;
        }

        public Builder sendUserToSettings(boolean sendUserToSettings) {
            this.sendUserToSettings = sendUserToSettings;
            return this;
        }

        public Granter build() {
            return new Granter(fragmentManager, permissions, requestCode, rationale, sendUserToSettings);
        }
    }
}
