package com.zty.therapist.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.zty.therapist.notification.DownloadApkService;
import com.zty.therapist.notification.DownloadService;
import com.zty.therapist.ui.activity.PdfActivity;

import java.io.File;

/**
 * Created by zty on 2017/1/6.
 */

public class DownloadUtils {

    public static File file = Environment.getExternalStorageDirectory();
    public static String assent = "/therapist/pdf";

    public static String apk = "/therapist/安装包";

    public static void downPdf(Context context, String url, String fileName) {
        File f = new File(file + assent + "/" + fileName + ".pdf");
        if (f.exists()) {
            context.startActivity(new Intent(context, PdfActivity.class).putExtra("pdfName", fileName));
        } else {
            Intent updateIntent = new Intent(context, DownloadService.class);
            updateIntent.putExtra("downUrl", url);
            updateIntent.putExtra("fileName", fileName);
            context.startService(updateIntent);
        }
    }

    public static void downApk(Context context, String url, String fileName) {
        Intent updateIntent = new Intent(context, DownloadApkService.class);
        updateIntent.putExtra("downUrl", url);
        updateIntent.putExtra("fileName", fileName);
        context.startService(updateIntent);
    }
}
