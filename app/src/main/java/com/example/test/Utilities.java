package com.example.test;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Environment;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utilities {
    private static final String HOME_PATH_NAME = "AirToPlay";
    private static final String PHOTO_FILE_EXTENSION = "png";
    private static final String PHOTO_PATH_NAME = "image";
    private static final String VIDEO_FILE_EXTENSION = "avi";
    private static final String VIDEO_PATH_NAME = "movie";

    public static String getHomePath() {
        String homePath = null;
        try {
            return new File(Environment.getExternalStorageDirectory().getCanonicalPath(), HOME_PATH_NAME).getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            return homePath;
        }
    }

    public static String getSubDir(String dir) {
        String homePath = getHomePath();
        if (homePath == null) {
            return null;
        }
        boolean z = false;
        try {
            return new File(homePath, dir).getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String getPhotoPath() {
        return getSubDir(PHOTO_PATH_NAME);
    }

    public static String getVideoPath() {
        return getSubDir(VIDEO_PATH_NAME);
    }

    public static List<String> loadPhotoList() {
        File[] photoFiles = new File(getPhotoPath()).listFiles(new FileFilter() {
            public boolean accept(File file) {
                try {
                    String filePath = file.getCanonicalPath();
                    if (filePath.substring(filePath.lastIndexOf(".") + 1).equalsIgnoreCase(Utilities.PHOTO_FILE_EXTENSION)) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        List<String> photoFileNameList = null;
        if (photoFiles != null) {
            photoFileNameList = new ArrayList<>();
            for (File file : photoFiles) {
                photoFileNameList.add(file.getPath());
            }
            Collections.reverse(photoFileNameList);
        }
        return photoFileNameList;
    }

    public static List<String> loadVideoList() {
        File[] videoFiles = new File(getVideoPath()).listFiles(new FileFilter() {
            public boolean accept(File file) {
                try {
                    String filePath = file.getCanonicalPath();
                    if (filePath.substring(filePath.lastIndexOf(".") + 1).equalsIgnoreCase(Utilities.VIDEO_FILE_EXTENSION)) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        List<String> videoFileNameList = null;
        if (videoFiles != null) {
            videoFileNameList = new ArrayList<>();
            for (File file : videoFiles) {
                videoFileNameList.add(file.getPath());
            }
            Collections.reverse(videoFileNameList);
        }
        return videoFileNameList;
    }

    public static String getRandomPhotoFilePath() {
        String photoPath = getPhotoPath();
        if (photoPath == null) {
            return null;
        }
        File photoDir = new File(photoPath);
        if (!photoDir.exists() && !photoDir.mkdirs()) {
            return null;
        }
        boolean z = false;
        try {
            return new File(photoPath, new SimpleDateFormat("yyyyMMdd_HHmmsss", Locale.getDefault()).format(new Date()) + "." + PHOTO_FILE_EXTENSION).getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static String getRandomVideoFilePath() {
        String videoPath = getVideoPath();
        if (videoPath == null) {
            return null;
        }
        File videoDir = new File(videoPath);
        if (!videoDir.exists() && !videoDir.mkdirs()) {
            return null;
        }
        boolean z = false;
        try {
            return new File(videoPath, new SimpleDateFormat("yyyyMMdd_HHmmsss", Locale.getDefault()).format(new Date()) + "." + VIDEO_FILE_EXTENSION).getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static Bitmap addBorderToBitmap(Bitmap bmp, int color, int borderSize) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + (borderSize * 2), bmp.getHeight() + (borderSize * 2), bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawLine(0.0f, 0.0f, (float) (bmp.getWidth() + (borderSize * 2)), (float) (borderSize * 2), paint);
        canvas.drawLine(0.0f, (float) (bmp.getHeight() + borderSize), (float) (bmp.getWidth() + (borderSize * 2)), (float) (bmp.getHeight() + (borderSize * 2)), paint);
        canvas.drawLine(0.0f, 0.0f, (float) borderSize, (float) (bmp.getHeight() + (borderSize * 2)), paint);
        canvas.drawLine((float) (bmp.getWidth() + borderSize), 0.0f, (float) (bmp.getWidth() + (borderSize * 2)), (float) (bmp.getHeight() + (borderSize * 2)), paint);
        canvas.drawBitmap(bmp, (float) borderSize, (float) borderSize, null);
        return bmpWithBorder;
    }
}
