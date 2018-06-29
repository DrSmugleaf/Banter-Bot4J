/*
 * EVE Swagger Interface
 * An OpenAPI for EVE Online
 *
 * OpenAPI spec version: 0.8.1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.github.drsmugleaf.eve.esi.api;

import com.github.drsmugleaf.eve.esi.ApiCallback;
import com.github.drsmugleaf.eve.esi.ApiClient;
import com.github.drsmugleaf.eve.esi.ApiException;
import com.github.drsmugleaf.eve.esi.ApiResponse;
import com.github.drsmugleaf.eve.esi.Configuration;
import com.github.drsmugleaf.eve.esi.Pair;
import com.github.drsmugleaf.eve.esi.ProgressRequestBody;
import com.github.drsmugleaf.eve.esi.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import com.github.drsmugleaf.eve.esi.model.BadGateway;
import com.github.drsmugleaf.eve.esi.model.BadRequest;
import com.github.drsmugleaf.eve.esi.model.Forbidden;
import com.github.drsmugleaf.eve.esi.model.GetCharactersCharacterIdClonesOk;
import com.github.drsmugleaf.eve.esi.model.InternalServerError;
import com.github.drsmugleaf.eve.esi.model.ServiceUnavailable;
import com.github.drsmugleaf.eve.esi.model.Unauthorized;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClonesApi {
    private ApiClient apiClient;

    public ClonesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ClonesApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getCharactersCharacterIdClones
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getCharactersCharacterIdClonesCall(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/characters/{character_id}/clones/"
            .replaceAll("\\{" + "character_id" + "\\}", apiClient.escapeString(characterId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (datasource != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("datasource", datasource));
        if (token != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("token", token));
        if (userAgent != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("user_agent", userAgent));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        if (ifNoneMatch != null)
        localVarHeaderParams.put("If-None-Match", apiClient.parameterToString(ifNoneMatch));
        if (xUserAgent != null)
        localVarHeaderParams.put("X-User-Agent", apiClient.parameterToString(xUserAgent));

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "evesso" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getCharactersCharacterIdClonesValidateBeforeCall(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'characterId' is set
        if (characterId == null) {
            throw new ApiException("Missing the required parameter 'characterId' when calling getCharactersCharacterIdClones(Async)");
        }
        

        com.squareup.okhttp.Call call = getCharactersCharacterIdClonesCall(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get clones
     * A list of the character&#39;s clones  --- Alternate route: &#x60;/dev/characters/{character_id}/clones/&#x60;  Alternate route: &#x60;/v3/characters/{character_id}/clones/&#x60;  --- This route is cached for up to 120 seconds
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @return GetCharactersCharacterIdClonesOk
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public GetCharactersCharacterIdClonesOk getCharactersCharacterIdClones(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent) throws ApiException {
        ApiResponse<GetCharactersCharacterIdClonesOk> resp = getCharactersCharacterIdClonesWithHttpInfo(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
        return resp.getData();
    }

    /**
     * Get clones
     * A list of the character&#39;s clones  --- Alternate route: &#x60;/dev/characters/{character_id}/clones/&#x60;  Alternate route: &#x60;/v3/characters/{character_id}/clones/&#x60;  --- This route is cached for up to 120 seconds
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @return ApiResponse&lt;GetCharactersCharacterIdClonesOk&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<GetCharactersCharacterIdClonesOk> getCharactersCharacterIdClonesWithHttpInfo(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent) throws ApiException {
        com.squareup.okhttp.Call call = getCharactersCharacterIdClonesValidateBeforeCall(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent, null, null);
        Type localVarReturnType = new TypeToken<GetCharactersCharacterIdClonesOk>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get clones (asynchronously)
     * A list of the character&#39;s clones  --- Alternate route: &#x60;/dev/characters/{character_id}/clones/&#x60;  Alternate route: &#x60;/v3/characters/{character_id}/clones/&#x60;  --- This route is cached for up to 120 seconds
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getCharactersCharacterIdClonesAsync(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent, final ApiCallback<GetCharactersCharacterIdClonesOk> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getCharactersCharacterIdClonesValidateBeforeCall(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<GetCharactersCharacterIdClonesOk>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getCharactersCharacterIdImplants
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getCharactersCharacterIdImplantsCall(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/characters/{character_id}/implants/"
            .replaceAll("\\{" + "character_id" + "\\}", apiClient.escapeString(characterId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (datasource != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("datasource", datasource));
        if (token != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("token", token));
        if (userAgent != null)
        localVarQueryParams.addAll(apiClient.parameterToPair("user_agent", userAgent));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        if (ifNoneMatch != null)
        localVarHeaderParams.put("If-None-Match", apiClient.parameterToString(ifNoneMatch));
        if (xUserAgent != null)
        localVarHeaderParams.put("X-User-Agent", apiClient.parameterToString(xUserAgent));

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] { "evesso" };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getCharactersCharacterIdImplantsValidateBeforeCall(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'characterId' is set
        if (characterId == null) {
            throw new ApiException("Missing the required parameter 'characterId' when calling getCharactersCharacterIdImplants(Async)");
        }
        

        com.squareup.okhttp.Call call = getCharactersCharacterIdImplantsCall(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Get active implants
     * Return implants on the active clone of a character  --- Alternate route: &#x60;/dev/characters/{character_id}/implants/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/implants/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/implants/&#x60;  --- This route is cached for up to 300 seconds
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @return List&lt;Integer&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<Integer> getCharactersCharacterIdImplants(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent) throws ApiException {
        ApiResponse<List<Integer>> resp = getCharactersCharacterIdImplantsWithHttpInfo(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
        return resp.getData();
    }

    /**
     * Get active implants
     * Return implants on the active clone of a character  --- Alternate route: &#x60;/dev/characters/{character_id}/implants/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/implants/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/implants/&#x60;  --- This route is cached for up to 300 seconds
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @return ApiResponse&lt;List&lt;Integer&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<Integer>> getCharactersCharacterIdImplantsWithHttpInfo(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent) throws ApiException {
        com.squareup.okhttp.Call call = getCharactersCharacterIdImplantsValidateBeforeCall(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent, null, null);
        Type localVarReturnType = new TypeToken<List<Integer>>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get active implants (asynchronously)
     * Return implants on the active clone of a character  --- Alternate route: &#x60;/dev/characters/{character_id}/implants/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/implants/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/implants/&#x60;  --- This route is cached for up to 300 seconds
     * @param characterId An EVE character ID (required)
     * @param datasource The server name you would like data from (optional, default to tranquility)
     * @param ifNoneMatch ETag from a previous request. A 304 will be returned if this matches the current ETag (optional)
     * @param token Access token to use if unable to set a header (optional)
     * @param userAgent Client identifier, takes precedence over headers (optional)
     * @param xUserAgent Client identifier, takes precedence over User-Agent (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getCharactersCharacterIdImplantsAsync(Integer characterId, String datasource, String ifNoneMatch, String token, String userAgent, String xUserAgent, final ApiCallback<List<Integer>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getCharactersCharacterIdImplantsValidateBeforeCall(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<Integer>>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}