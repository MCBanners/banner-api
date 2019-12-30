package com.mcbanners.backend.net;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public abstract class BasicHttpClient {
    private static final String USER_AGENT = "MCBanners";
    private final String baseURL;
    private final HttpHeaders headers;
    private final RestTemplate template;

    public BasicHttpClient(String baseURL) {
        this.baseURL = baseURL;

        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", USER_AGENT);
        this.headers = headers;

        this.template = new RestTemplate();
    }

    /**
     * Make a GET request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> get(String endpoint, Class<T> responseType) {
        return makeRequest(endpoint, HttpMethod.GET, responseType);
    }

    /**
     * Make a POST request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> post(String endpoint, Class<T> responseType) {
        return makeRequest(endpoint, HttpMethod.POST, responseType);
    }

    /**
     * Make a PUT request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> put(String endpoint, Class<T> responseType) {
        return makeRequest(endpoint, HttpMethod.POST, responseType);
    }

    /**
     * Make a PATCH request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> patch(String endpoint, Class<T> responseType) {
        return makeRequest(endpoint, HttpMethod.PATCH, responseType);
    }

    /**
     * Make a DELETE request to the Base URL + Endpoint, receiving the specified T in return.
     *
     * @param endpoint     the endpoint of the Base URL
     * @param responseType the class to attempt to serialize the response into
     * @param <T>          the arbitrary type of responseType
     * @return the successfully deserialized T or null
     */
    public final <T> ResponseEntity<T> delete(String endpoint, Class<T> responseType) {
        return makeRequest(endpoint, HttpMethod.DELETE, responseType);
    }

    private <T> ResponseEntity<T> makeRequest(String endpoint, HttpMethod method, Class<T> responseType) {
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try {
            return this.template.exchange(
                    baseURL + endpoint,
                    method,
                    entity,
                    responseType
            );
        } catch (Exception ignored) {
            return null;
        }
    }
}
