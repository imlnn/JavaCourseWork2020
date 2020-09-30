package main.web;

import com.google.gson.Gson;
import main.exception.DataTransformFailureException;
import main.model.DiagnosisModel;
import main.model.PatientModel;
import main.model.TokenModel;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiagnosisRequestController extends RequestController {
    private static final String url = baseUrl + "/diagnoses";

    public static void add(TokenModel token, DiagnosisModel diagnosis) {
        HttpPost httpPost = new HttpPost(url + "/add");
        String json = new Gson().toJson(diagnosis);
        StringEntity entity;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            throw new DataTransformFailureException("Failed to encode json");
        }
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        addToken(httpPost, token);
        makeRequest(httpPost);
    }

    public static void delete(TokenModel token, Long id) {
        HttpDelete httpDelete = new HttpDelete(url + "/" + id.toString());
        addToken(httpDelete, token);
        makeRequest(httpDelete);
    }

    public static List<DiagnosisModel> getAll(TokenModel token) {
        HttpGet httpGet = new HttpGet(url + "/list");
        addToken(httpGet, token);
        String content = makeRequest(httpGet);
        if (content.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(new Gson().fromJson(content, DiagnosisModel[].class));
    }

    public static List<PatientModel> getPatientsWithDiagnosis(TokenModel token, Long id) {
        HttpGet httpGet = new HttpGet(url + "/" + id.toString());
        addToken(httpGet, token);
        String content = makeRequest(httpGet);
        if (content.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(new Gson().fromJson(content, PatientModel[].class));
    }
}
