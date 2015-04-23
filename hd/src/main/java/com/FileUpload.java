package com;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by OTITAN on 2015/4/23.
 * 文件异步上传类
 */
public class FileUpload {
    Context context;

    public FileUpload() {
    }

    public void UpLoad(String url, File file, Context Context) throws FileNotFoundException {
        this.context = Context;
        AsyncHttpClient asyclient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("db", file);

        asyclient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
                System.out.print("上传成功");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
                System.out.print("上传失败");
            }
        });
    }

}
