package com.nagare.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class PermissionUtil {
    public static void requestPermission(AppCompatActivity activity, int requestId, String permission, boolean finishActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            PermissionUtil.RationaleDialog.newInstance(requestId, finishActivity).show(activity.getSupportFragmentManager(), "dialog");
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
        }
    }

    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults, String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    public static class RationaleDialog extends DialogFragment {
        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode", ARGUMENT_FINISH_ACTIVITY = "finish";
        private boolean finishActivity = false;

        public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);
            RationaleDialog dialog = new RationaleDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
            finishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity()).setMessage("Request rationale location").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
                    finishActivity = false;
                }
            }).setNegativeButton("Cancel", null).create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (finishActivity) {
                Toast.makeText(getActivity(), "Permission request", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }

    public static class PermissionDeniedDialog extends DialogFragment {
        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";
        private boolean finishActivity = false;

        public static PermissionDeniedDialog newInstance(boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);

            PermissionDeniedDialog dialog = new PermissionDeniedDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            finishActivity = getArguments().getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity()).setMessage("Location permission denied").setPositiveButton("OK", null).create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);
            if (finishActivity) {
                Toast.makeText(getActivity(), "Permission request", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}