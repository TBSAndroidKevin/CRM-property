package com.shyam.crmproperty.Other;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefHandler {

    SharedPreferences GlobalDocPref;
    SharedPreferences.Editor edtGlobalDoc;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "CRM";
    private final String ACCESS_TOKEN = "access_token";
    private final String ACCOUNT_ID = "account_id";
    private final String EMAIL = "account_email";
    private final String IS_LOGIN = "isLogin";
    private final String IS_ADMIN = "IS_ADMIN";

    public PrefHandler(Context context) {
        try {
            this._context = context;
            GlobalDocPref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            edtGlobalDoc = GlobalDocPref.edit();
            edtGlobalDoc.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIS_ADMIN(Boolean IS_ADMIN) {
        edtGlobalDoc.putBoolean(this.IS_ADMIN, IS_ADMIN);
        edtGlobalDoc.commit();
    }

    public Boolean getIS_ADMIN() {
        return GlobalDocPref.getBoolean(this.IS_ADMIN, false);
    }

    public boolean getIsLogin() {
        return GlobalDocPref.getBoolean(IS_LOGIN, false);
    }

    public void setIsLogin(boolean isLogin) {

        edtGlobalDoc.putBoolean(IS_LOGIN, isLogin);
        edtGlobalDoc.commit();
    }

    public String getACCESS_TOKEN() {
        return GlobalDocPref.getString(ACCESS_TOKEN, null);
    }

    public void setACCESS_TOKEN(String access_token) {
        edtGlobalDoc.putString(ACCESS_TOKEN, access_token);
        edtGlobalDoc.commit();
    }

    public void setEMAIL(String email){
        edtGlobalDoc.putString(EMAIL,email);
        edtGlobalDoc.commit();
    }

    public String getEMAIL(){
        return GlobalDocPref.getString(EMAIL,null);
    }

    public void setACCOUNT_ID(String account_id) {
        edtGlobalDoc.putString(ACCOUNT_ID, account_id);
        edtGlobalDoc.commit();
    }

    public String getACCOUNT_ID() {
        return GlobalDocPref.getString(ACCOUNT_ID, null);
    }
//
//    public void setParentId(String parentId)
//    {
//        edtGlobalDoc.putString("parentid",parentId);
//        edtGlobalDoc.commit();
//    }
//
//    public String getParentId()
//    {
//        return GlobalDocPref.getString("parentid",null);
//    }
//
//    public void setPreviousParentId(String previousParentId)
//    {
//        edtGlobalDoc.putString("previousPrentid",previousParentId);
//        edtGlobalDoc.commit();
//    }
//
//    public String getPreviousParentId()
//    {
//        return GlobalDocPref.getString("previousPrentid",null);
//    }


}
