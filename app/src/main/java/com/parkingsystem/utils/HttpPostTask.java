package com.parkingsystem.utils;

import android.os.AsyncTask;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostTask extends AsyncTask<String, String, String> {

    // BaseActivity 中基础问题的处理 handler
    private Handler mHandler;

    // 返回信息处理回调接口
    private ResponseHandler rHandler;

    // 请求类对象
    private CommonRequest request;


    public HttpPostTask(CommonRequest request, Handler mHandler,
                        ResponseHandler rHandler) {
        this.request = request;
        this.mHandler = mHandler;
        this.rHandler = rHandler;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder resultBuf = new StringBuilder();
        try {
            URL url = new URL(params[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(request.getJsonStr());
            out.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("连接成功");
                InputStream in = connection.getInputStream();
                BufferedReader read = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = read.readLine()) != null) {
                    resultBuf.append(line);
                }

                return resultBuf.toString();
            } else {
                System.out.println("连接失败");
                // 异常情况
                mHandler.obtainMessage(Constant.HANDLER_HTTP_RECIVE_FAIL,
                        "[" + responseCode + "]" + connection.getResponseMessage()).sendToTarget();
            }
        } catch (IOException e) {
            // 网路请求过程中发生IO异常
            mHandler.obtainMessage(Constant.HANDLER_HTTP_SEND_FAIL,
                    e.getClass().getName() + " : " + e.getMessage()).sendToTarget();

        }

        return resultBuf.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if (rHandler != null) {
            if (!"".equals(result)) {
                /* 交易成功时需要在处理返回结果时手动关闭 Loading 对话框,
                可以灵活处理连续请求对个接口时 Loading 框不断弹出,关闭的情况 */

                CommonResponse response = new CommonResponse(result);
                // 这里 response.getResCode() 为多少表示业务完成也是和服务器
                // 约定好的
                if ("0".equals(response.getResCode())) { // 正确
                    rHandler.success(response);
                } else if ("1".equals(response.getResCode())) {
                    rHandler.success1(response);
                } else if ("2".equals(response.getResCode())) {
                    rHandler.success2(response);
                } else {
                    rHandler.fail(response.getResCode(), response.getResMsg());
                }

            }
        }
    }
}
