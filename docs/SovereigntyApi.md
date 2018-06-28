# SovereigntyApi

All URIs are relative to *https://esi.evetech.net/latest*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getSovereigntyCampaigns**](SovereigntyApi.md#getSovereigntyCampaigns) | **GET** /sovereignty/campaigns/ | List sovereignty campaigns
[**getSovereigntyMap**](SovereigntyApi.md#getSovereigntyMap) | **GET** /sovereignty/map/ | List sovereignty of systems
[**getSovereigntyStructures**](SovereigntyApi.md#getSovereigntyStructures) | **GET** /sovereignty/structures/ | List sovereignty structures


<a name="getSovereigntyCampaigns"></a>
# **getSovereigntyCampaigns**
> List&lt;GetSovereigntyCampaigns200Ok&gt; getSovereigntyCampaigns(datasource, ifNoneMatch, userAgent, xUserAgent)

List sovereignty campaigns

Shows sovereignty data for campaigns.  --- Alternate route: &#x60;/dev/sovereignty/campaigns/&#x60;  Alternate route: &#x60;/legacy/sovereignty/campaigns/&#x60;  Alternate route: &#x60;/v1/sovereignty/campaigns/&#x60;  --- This route is cached for up to 5 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.SovereigntyApi;


SovereigntyApi apiInstance = new SovereigntyApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetSovereigntyCampaigns200Ok> result = apiInstance.getSovereigntyCampaigns(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SovereigntyApi#getSovereigntyCampaigns");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetSovereigntyCampaigns200Ok&gt;**](GetSovereigntyCampaigns200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSovereigntyMap"></a>
# **getSovereigntyMap**
> List&lt;GetSovereigntyMap200Ok&gt; getSovereigntyMap(datasource, ifNoneMatch, userAgent, xUserAgent)

List sovereignty of systems

Shows sovereignty information for solar systems  --- Alternate route: &#x60;/dev/sovereignty/map/&#x60;  Alternate route: &#x60;/legacy/sovereignty/map/&#x60;  Alternate route: &#x60;/v1/sovereignty/map/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.SovereigntyApi;


SovereigntyApi apiInstance = new SovereigntyApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetSovereigntyMap200Ok> result = apiInstance.getSovereigntyMap(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SovereigntyApi#getSovereigntyMap");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetSovereigntyMap200Ok&gt;**](GetSovereigntyMap200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getSovereigntyStructures"></a>
# **getSovereigntyStructures**
> List&lt;GetSovereigntyStructures200Ok&gt; getSovereigntyStructures(datasource, ifNoneMatch, userAgent, xUserAgent)

List sovereignty structures

Shows sovereignty data for structures.  --- Alternate route: &#x60;/dev/sovereignty/structures/&#x60;  Alternate route: &#x60;/legacy/sovereignty/structures/&#x60;  Alternate route: &#x60;/v1/sovereignty/structures/&#x60;  --- This route is cached for up to 120 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.SovereigntyApi;


SovereigntyApi apiInstance = new SovereigntyApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetSovereigntyStructures200Ok> result = apiInstance.getSovereigntyStructures(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling SovereigntyApi#getSovereigntyStructures");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetSovereigntyStructures200Ok&gt;**](GetSovereigntyStructures200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

