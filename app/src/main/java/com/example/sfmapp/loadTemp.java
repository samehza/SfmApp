package com.example.sfmapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class loadTemp {
    Dialog dialog ;
    public void showDialog(Activity activity){
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_load);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(dialog.isShowing()){
                    dialog.dismiss();
                    Toast.makeText(activity,"Erreur d'acquisition",Toast.LENGTH_SHORT).show();
                }
            }
        }, 15000);   //15 seconds
    }
    public void hideDialog(){
        dialog.dismiss();
    }
}
