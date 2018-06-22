package com.codeforireland.vacanthome.utils;

import java.util.ArrayList;

/**
 * TODO: Not used - delete it later
 */
public interface PermissionResultCallback {

    void PermissionGranted(int request_code);
    void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions);
    void PermissionDenied(int request_code);
    void NeverAskAgain(int request_code);
}
