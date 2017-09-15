package se.eelde.granter;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * "Stolen" from EasyPermissions. If the time presents itself we should arrange a PR
 * towards Easypermissions to provide this functionality.
 */
class Stolen {
    static void callGrantedCallback(Object callee, int requestCode, List<String> perms) {
        if (callee instanceof EasyPermissions.PermissionCallbacks) {
            ((EasyPermissions.PermissionCallbacks) callee).onPermissionsGranted(requestCode, perms);
        }
    }

    static void callDeniedCallback(Object callee, int requestCode, List<String> perms) {
        if (callee instanceof EasyPermissions.PermissionCallbacks) {
            ((EasyPermissions.PermissionCallbacks) callee).onPermissionsDenied(requestCode, perms);
        }
    }

    static void callAnnotations(Object object, int requestCode) {
        Class clazz = object.getClass();

        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                AfterPermissionGranted annotation = method.getAnnotation(AfterPermissionGranted.class);
                if (annotation != null) {
                    // Check for annotated methods with matching request code.
                    if (annotation.value() == requestCode) {
                        // Method must be void so that we can invoke it
                        if (method.getParameterTypes().length > 0) {
                            throw new RuntimeException(
                                    "Cannot execute method " + method.getName() + " because it is non-void method and/or has input parameters.");
                        }

                        try {
                            // Make method accessible if private
                            if (!method.isAccessible()) {
                                method.setAccessible(true);
                            }
                            method.invoke(object);
                        } catch (IllegalAccessException ignored) {
                        } catch (InvocationTargetException ignored) {
                        }
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    static boolean shouldShowRationale(Fragment fragment, String[] permissions) {
        for (String permission : permissions) {
            if (fragment.shouldShowRequestPermissionRationale(permission)) {
                return true;
            }
        }
        return false;
    }

    static int[] grantsFromPermissions(Fragment fragment, @NonNull String[] requestedPermissions) {
        int[] grantResults = new int[requestedPermissions.length];

        for (int i = 0; i < requestedPermissions.length; i++) {
            grantResults[i] = EasyPermissions.hasPermissions(fragment.getContext(), requestedPermissions[i]) ? PackageManager.PERMISSION_GRANTED : PackageManager.PERMISSION_DENIED;
        }

        return grantResults;
    }
}
