package com.example.androidnetworkdemo;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:9102";
    private static final String TAG = "OkHttpActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
    }


    public void getRequest(View view) {

        // 要有客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();

        // 请求内容
        final Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "/get/text")
                .build();

        // 用client 创建请求任务
        Call task = okHttpClient.newCall(request);

        // 异步请求
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"getRequest onFailure -> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                int code = response.code();
                Log.d(TAG,"getRequest onResponse code -> " + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody requestBody = response.body();
                    Log.d(TAG,"getRequest onResponse requestBody -> " + requestBody.toString());
                }

            }
        });

    }


    public void postComment(View view) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000,TimeUnit.MILLISECONDS)
                .build();


        CommentItem commentItem = new CommentItem("234134123","你的文章不错！");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(commentItem);

        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = RequestBody.create(jsonStr,mediaType);

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "post/comment")
                .build();

        Call task = okHttpClient.newCall(request);

        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"postComment onFailure -> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG,"postComment onResponse code -> " + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody requestBody = response.body();
                    if (requestBody != null) {
                        Log.d(TAG,"postComment onResponse requestBody -> " + requestBody.toString());
                    }
                }
            }
        });

    }

    public void postFile(View view) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000,TimeUnit.MILLISECONDS)
                .build();


        File file = new File("/sdcard/DCIM/Camera/IMG_20161218_114916.jpg");

        MediaType mediaType = MediaType.parse("image/jpeg");
        RequestBody fileBody = RequestBody.create(file,mediaType);

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("file",file.getName(),fileBody)
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "/file/upload")
                .build();

        Call task = okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"postFile onFailure -> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.d(TAG,"postFile onResponse code -> " + code);

                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody requestBody = response.body();
                    if (requestBody != null) {
                        Log.d(TAG,"postComment onResponse requestBody -> " + requestBody.toString());
                    }
                }
            }
        });
    }

    public void postFiles(View view) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000,TimeUnit.MILLISECONDS)
                .build();

        File file1 = new File("/sdcard/DCIM/Camera/IMG_20170219_195511.jpg");
        File file2 = new File("/sdcard/DCIM/Camera/IMG_20170219_195529.jpg");
        File file3 = new File("/sdcard/DCIM/Camera/IMG_20170713_191905_HDR.jpg");

        MediaType mediaType = MediaType.parse("image/jpeg");
        RequestBody fileBody1 = RequestBody.create(file1,mediaType);
        RequestBody fileBody2 = RequestBody.create(file1,mediaType);
        RequestBody fileBody3 = RequestBody.create(file1,mediaType);


        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("files",file1.getName(),fileBody1)
                .addFormDataPart("files",file2.getName(),fileBody2)
                .addFormDataPart("files",file3.getName(),fileBody3)
                .build();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASE_URL + "files/upload")
                .build();

        Call task = okHttpClient.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                int code = response.code();

                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String s = body.toString();

                    }

                }
            }
        });


    }


    public void downFile(View view) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000,TimeUnit.MILLISECONDS)
                .build();


        Request request = new Request.Builder()
                .get()
                .url(BASE_URL + "download/15")
                .build();


        final Call call = okHttpClient.newCall(request);

        // 异步
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                if (code== HttpURLConnection.HTTP_OK) {
                    Headers headers = response.headers();
                    downloadFile(response.body().byteStream(), headers);
                }
            }
        });


        // 同步
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Response execute = call.execute();
//
//                    int code = execute.code();
//                    if (code == HttpURLConnection.HTTP_OK) {
//                        Headers headers = execute.headers();
//
//                        for (int i = 0; i < headers.size(); i++) {
//
//                            String key = headers.name(i);
//                            String value = headers.value(i);
//
//                            Log.d(TAG,"headers size - > " + headers.size());
//                            Log.d(TAG,"headers :" + i + " key - > " + key);
//                            Log.d(TAG,"headers :" + i + " value - > " + value);
//                        }
//
//                        downloadFile(execute.body().byteStream(), headers);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();


    }

    private void downloadFile(InputStream inputStream, Headers headers) throws IOException {
        String contentType = headers.get("Content-disposition");
        String fileName = contentType.replace("attachment; filename", "");

        File outFile = new File(OkHttpActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + fileName);

        if (!outFile.getParentFile().exists()) {
            outFile.mkdirs();
        }

        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(outFile);

        byte[] buffer = new byte[1024];
        int len;

        while ((len = inputStream.read(buffer,0,buffer.length)) != -1) {
            fileOutputStream.write(buffer,0,len);
        }

        fileOutputStream.flush();


        IOUtils.closeIO(fileOutputStream);
        IOUtils.closeIO(inputStream);

    }


}
