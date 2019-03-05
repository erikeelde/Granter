package se.eelde.granter;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Granter {
    private static final String PERMISSIONS_FRAGMENT_TAG = "permissions_fragment_tag";

    private final FragmentManager fragmentManager;
    private final ArrayList<String> permissions;
    private final int requestCode;
    private final String rationale;
    private String systemSettingRationale;

    private Granter(FragmentManager fragmentManager, ArrayList<String> permissions, int requestCode, String rationale, String systemSettingRationale) {
        this.fragmentManager = fragmentManager;
        this.permissions = permissions;
        this.requestCode = requestCode;
        this.rationale = rationale;
        this.systemSettingRationale = systemSettingRationale;
    }

    public void show() {
        @Nullable
        Fragment oldGranterFragment = fragmentManager.findFragmentByTag(PERMISSIONS_FRAGMENT_TAG);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (oldGranterFragment != null) {
            fragmentTransaction.remove(oldGranterFragment);
        }

        fragmentTransaction.add(GranterFragment.newInstance(permissions, requestCode, rationale, systemSettingRationale), PERMISSIONS_FRAGMENT_TAG)
                .commit();
    }

    @SuppressWarnings("unused")
    public static class Builder {
        private final FragmentManager fragmentManager;
        private final ArrayList<String> permissions = new ArrayList<>();
        private final Resources resources;
        private int requestCode = 7896;
        private String rationale = null;
        private String systemSettingRationale = null;

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

        public Builder systemSettingRationale(String systemSettingRationale) {
            this.systemSettingRationale = systemSettingRationale;
            return this;
        }

        public Builder systemSettingRationale(@StringRes int systemSettingRationale) {
            this.systemSettingRationale = resources.getString(systemSettingRationale);
            return this;
        }

        public Granter build() {
            return new Granter(fragmentManager, permissions, requestCode, rationale, systemSettingRationale);
        }
    }
}
