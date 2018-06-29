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
import com.github.drsmugleaf.eve.esi.model.InternalServerError;
import com.github.drsmugleaf.eve.esi.model.PostUiOpenwindowNewmailNewMail;
import com.github.drsmugleaf.eve.esi.model.PostUiOpenwindowNewmailUnprocessableEntity;
import com.github.drsmugleaf.eve.esi.model.ServiceUnavailable;
import com.github.drsmugleaf.eve.esi.model.Unauthorized;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UserInterfaceApi
 */
@Ignore
public class UserInterfaceApiTest {

    private final UserInterfaceApi api = new UserInterfaceApi();

    
    /**
     * Set Autopilot Waypoint
     *
     * Set a solar system as autopilot waypoint  --- Alternate route: &#x60;/dev/ui/autopilot/waypoint/&#x60;  Alternate route: &#x60;/v2/ui/autopilot/waypoint/&#x60; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postUiAutopilotWaypointTest() throws ApiException {
        Boolean addToBeginning = null;
        Boolean clearOtherWaypoints = null;
        Long destinationId = null;
        String datasource = null;
        String token = null;
        String userAgent = null;
        String xUserAgent = null;
        api.postUiAutopilotWaypoint(addToBeginning, clearOtherWaypoints, destinationId, datasource, token, userAgent, xUserAgent);

        // TODO: test validations
    }
    
    /**
     * Open Contract Window
     *
     * Open the contract window inside the client  --- Alternate route: &#x60;/dev/ui/openwindow/contract/&#x60;  Alternate route: &#x60;/legacy/ui/openwindow/contract/&#x60;  Alternate route: &#x60;/v1/ui/openwindow/contract/&#x60; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postUiOpenwindowContractTest() throws ApiException {
        Integer contractId = null;
        String datasource = null;
        String token = null;
        String userAgent = null;
        String xUserAgent = null;
        api.postUiOpenwindowContract(contractId, datasource, token, userAgent, xUserAgent);

        // TODO: test validations
    }
    
    /**
     * Open Information Window
     *
     * Open the information window for a character, corporation or alliance inside the client  --- Alternate route: &#x60;/dev/ui/openwindow/information/&#x60;  Alternate route: &#x60;/legacy/ui/openwindow/information/&#x60;  Alternate route: &#x60;/v1/ui/openwindow/information/&#x60; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postUiOpenwindowInformationTest() throws ApiException {
        Integer targetId = null;
        String datasource = null;
        String token = null;
        String userAgent = null;
        String xUserAgent = null;
        api.postUiOpenwindowInformation(targetId, datasource, token, userAgent, xUserAgent);

        // TODO: test validations
    }
    
    /**
     * Open Market Details
     *
     * Open the market details window for a specific typeID inside the client  --- Alternate route: &#x60;/dev/ui/openwindow/marketdetails/&#x60;  Alternate route: &#x60;/legacy/ui/openwindow/marketdetails/&#x60;  Alternate route: &#x60;/v1/ui/openwindow/marketdetails/&#x60; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postUiOpenwindowMarketdetailsTest() throws ApiException {
        Integer typeId = null;
        String datasource = null;
        String token = null;
        String userAgent = null;
        String xUserAgent = null;
        api.postUiOpenwindowMarketdetails(typeId, datasource, token, userAgent, xUserAgent);

        // TODO: test validations
    }
    
    /**
     * Open New Mail Window
     *
     * Open the New Mail window, according to settings from the request if applicable  --- Alternate route: &#x60;/dev/ui/openwindow/newmail/&#x60;  Alternate route: &#x60;/legacy/ui/openwindow/newmail/&#x60;  Alternate route: &#x60;/v1/ui/openwindow/newmail/&#x60; 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void postUiOpenwindowNewmailTest() throws ApiException {
        PostUiOpenwindowNewmailNewMail newMail = null;
        String datasource = null;
        String token = null;
        String userAgent = null;
        String xUserAgent = null;
        api.postUiOpenwindowNewmail(newMail, datasource, token, userAgent, xUserAgent);

        // TODO: test validations
    }
    
}