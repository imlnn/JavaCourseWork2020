package main.web;

import com.google.gson.Gson;
import main.exception.DataTransformFailureException;
import main.model.AuthModel;
import main.model.RegisterModel;
import main.model.TokenModel;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

public class AuthRequestController extends RequestController {
    private static final String url = baseUrl + "/auth";

    public static TokenModel signIn(AuthModel authModel) {
        HttpPost httpPost = new HttpPost(url + "/signin");
        String json = new Gson().toJson(authModel);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            throw new DataTransformFailureException("Failed to encode json");
        }
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        String content = makeRequest(httpPost);
        TokenModel token = new Gson().fromJson(content, TokenModel.class);
        return token;
    }

    public static void register(RegisterModel registerModel) {
        HttpPost httpPost = new HttpPost(url + "/register");
        String json = new Gson().toJson(registerModel);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            throw new DataTransformFailureException("Failed to encode json");
        }
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        makeRequest(httpPost);
    }
}
