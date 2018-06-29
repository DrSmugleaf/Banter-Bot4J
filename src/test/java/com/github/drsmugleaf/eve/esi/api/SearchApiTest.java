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

import com.github.drsmugleaf.eve.esi.ApiException;
import com.github.drsmugleaf.eve.esi.model.BadGateway;
import com.github.drsmugleaf.eve.esi.model.BadRequest;
import com.github.drsmugleaf.eve.esi.model.Forbidden;
import com.github.drsmugleaf.eve.esi.model.GetCharactersCharacterIdSearchOk;
import com.github.drsmugleaf.eve.esi.model.GetSearchOk;
import com.github.drsmugleaf.eve.esi.model.InternalServerError;
import com.github.drsmugleaf.eve.esi.model.ServiceUnavailable;
import com.github.drsmugleaf.eve.esi.model.Unauthorized;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for SearchApi
 */
@Ignore
public class SearchApiTest {

    private final SearchApi api = new SearchApi();

    
    /**
     * Search on a string
     *
     * Search for entities that match a given sub-string.  --- Alternate route: &#x60;/dev/characters/{character_id}/search/&#x60;  Alternate route: &#x60;/v3/characters/{character_id}/search/&#x60;  --- This route is cached for up to 3600 seconds
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getCharactersCharacterIdSearchTest() throws ApiException {
        List<String> categories = null;
        Integer characterId = null;
        String search = null;
        String datasource = null;
        String ifNoneMatch = null;
        String language = null;
        Boolean strict = null;
        String token = null;
        String userAgent = null;
        String xUserAgent = null;
        GetCharactersCharacterIdSearchOk response = api.getCharactersCharacterIdSearch(categories, characterId, search, datasource, ifNoneMatch, language, strict, token, userAgent, xUserAgent);

        // TODO: test validations
    }
    
    /**
     * Search on a string
     *
     * Search for entities that match a given sub-string.  --- Alternate route: &#x60;/dev/search/&#x60;  Alternate route: &#x60;/v2/search/&#x60;  --- This route is cached for up to 3600 seconds
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSearchTest() throws ApiException {
        List<String> categories = null;
        String search = null;
        String datasource = null;
        String ifNoneMatch = null;
        String language = null;
        Boolean strict = null;
        String userAgent = null;
        String xUserAgent = null;
        GetSearchOk response = api.getSearch(categories, search, datasource, ifNoneMatch, language, strict, userAgent, xUserAgent);

        // TODO: test validations
    }
    
}