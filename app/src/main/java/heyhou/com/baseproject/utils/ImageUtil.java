package heyhou.com.baseproject.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import heyhou.com.baseproject.base.BaseApplication;

/**
 * 创建:yb 2016/10/22.
 * 描述:图片操作工具
 */

public class ImageUtil {
    private static ImageUtil instance;
    private static ExecutorService mService;

    private ImageUtil() {
        if (mService == null)
            mService = Executors.newSingleThreadExecutor();
    }

    public static ImageUtil getInstance() {
        if (instance == null) {
            synchronized (ImageUtil.class) {
                if (instance == null) {
                    instance = new ImageUtil();
                }
            }
        }
        return instance;
    }

    // 压缩图片质量
    public Bitmap cQuality(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        int beginRate = 100;
        while (bos.size() / 1024 > 100) {
            // 大于100Kb
            bos.reset();
            beginRate -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, beginRate, bos);
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Bitmap result = BitmapFactory.decodeStream(bis);
        return result;
    }

    /**
     * 按比例大小 质量压缩
     *
     * @param filePath
     * @return
     */
    public Bitmap cSize(String filePath, int width, int height) {
        try {
            DebugTool.error("compress", filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
            options.inJustDecodeBounds = false;
            options.inSampleSize = calculateInSampleSize(options, width, height);
            bmp = BitmapFactory.decodeFile(filePath, options);
            if (bmp == null) {
                return null;
            }
            int degree = getBitmapDegree(filePath);
            return cQuality(rotateBitmapByDegree(bmp, degree));
        } catch (OutOfMemoryError error) {
            DebugTool.info("compress",error.getMessage());
        }
        return null;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    /**
     * 保存图片到本地相册
     *
     * @param path
     */
    public static void saveImg(String path) {
        saveImg(getInstance().cSize(path, ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight()));
    }

    public static void saveImg(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "HeyHou");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            DebugTool.info("saveImg:----------->" + e.getMessage());
        }

        try {
            MediaStore.Images.Media.insertImage(BaseApplication.m_appContext.getContentResolver(), file.getAbsolutePath(), fileName, null);
            BaseApplication.m_appContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean cQsSave(String source, String temp) {
        return saveBitmapFile(cSize(source, ScreenUtil.getScreenWidth() / 2, ScreenUtil.getScreenHeight() / 2), temp);
    }

    /**
     * 环信图片操作
     *
     * @param bitmap
     */
    public boolean saveBitmapFile(final Bitmap bitmap, final String path) {
        BufferedOutputStream bos = null;
        boolean result = false;
        try {
            try {
                bos = new BufferedOutputStream(new FileOutputStream(path));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                result = true;
            } finally {
                bos.flush();
                bos.close();
            }
        } catch (Exception e) {
        }
        return result;
    }


    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }


}
