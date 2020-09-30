package main.web;

import main.exception.AccesDeniedException;
import main.exception.DataTransformFailureException;
import main.exception.RequestFailureException;
import main.model.TokenModel;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public abstract class RequestController {
    protected static final String baseUrl = "http://localhost:8080/";

    protected static <T extends HttpUriRequest> String makeRequest(T request) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;
        try {
            response = HttpClients.createDefault().execute(request);
        } catch (IOException e) {
            throw new DataTransformFailureException("Failure in request executing");
        }

        return resolveResponce(response);
    }

    protected static String resolveResponce(CloseableHttpResponse response) {
        int status = response.getStatusLine().getStatusCode();

        if (status < 200 || status >= 300) {
            if (status == 403) {
                throw new AccesDeniedException();
            }
            throw new RequestFailureException("Failure " + status);
        }

        try {
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new DataTransformFailureException("Failed to get entity");
        }
    }

    protected static <T extends HttpUriRequest> void addToken(T request, TokenModel token) {
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.getToken());
    }
}
