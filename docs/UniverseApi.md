# UniverseApi

All URIs are relative to *https://esi.evetech.net/latest*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getUniverseAncestries**](UniverseApi.md#getUniverseAncestries) | **GET** /universe/ancestries/ | Get ancestries
[**getUniverseAsteroidBeltsAsteroidBeltId**](UniverseApi.md#getUniverseAsteroidBeltsAsteroidBeltId) | **GET** /universe/asteroid_belts/{asteroid_belt_id}/ | Get asteroid belt information
[**getUniverseBloodlines**](UniverseApi.md#getUniverseBloodlines) | **GET** /universe/bloodlines/ | Get bloodlines
[**getUniverseCategories**](UniverseApi.md#getUniverseCategories) | **GET** /universe/categories/ | Get item categories
[**getUniverseCategoriesCategoryId**](UniverseApi.md#getUniverseCategoriesCategoryId) | **GET** /universe/categories/{category_id}/ | Get item category information
[**getUniverseConstellations**](UniverseApi.md#getUniverseConstellations) | **GET** /universe/constellations/ | Get constellations
[**getUniverseConstellationsConstellationId**](UniverseApi.md#getUniverseConstellationsConstellationId) | **GET** /universe/constellations/{constellation_id}/ | Get constellation information
[**getUniverseFactions**](UniverseApi.md#getUniverseFactions) | **GET** /universe/factions/ | Get factions
[**getUniverseGraphics**](UniverseApi.md#getUniverseGraphics) | **GET** /universe/graphics/ | Get graphics
[**getUniverseGraphicsGraphicId**](UniverseApi.md#getUniverseGraphicsGraphicId) | **GET** /universe/graphics/{graphic_id}/ | Get graphic information
[**getUniverseGroups**](UniverseApi.md#getUniverseGroups) | **GET** /universe/groups/ | Get item groups
[**getUniverseGroupsGroupId**](UniverseApi.md#getUniverseGroupsGroupId) | **GET** /universe/groups/{group_id}/ | Get item group information
[**getUniverseMoonsMoonId**](UniverseApi.md#getUniverseMoonsMoonId) | **GET** /universe/moons/{moon_id}/ | Get moon information
[**getUniversePlanetsPlanetId**](UniverseApi.md#getUniversePlanetsPlanetId) | **GET** /universe/planets/{planet_id}/ | Get planet information
[**getUniverseRaces**](UniverseApi.md#getUniverseRaces) | **GET** /universe/races/ | Get character races
[**getUniverseRegions**](UniverseApi.md#getUniverseRegions) | **GET** /universe/regions/ | Get regions
[**getUniverseRegionsRegionId**](UniverseApi.md#getUniverseRegionsRegionId) | **GET** /universe/regions/{region_id}/ | Get region information
[**getUniverseStargatesStargateId**](UniverseApi.md#getUniverseStargatesStargateId) | **GET** /universe/stargates/{stargate_id}/ | Get stargate information
[**getUniverseStarsStarId**](UniverseApi.md#getUniverseStarsStarId) | **GET** /universe/stars/{star_id}/ | Get star information
[**getUniverseStationsStationId**](UniverseApi.md#getUniverseStationsStationId) | **GET** /universe/stations/{station_id}/ | Get station information
[**getUniverseStructures**](UniverseApi.md#getUniverseStructures) | **GET** /universe/structures/ | List all public structures
[**getUniverseStructuresStructureId**](UniverseApi.md#getUniverseStructuresStructureId) | **GET** /universe/structures/{structure_id}/ | Get structure information
[**getUniverseSystemJumps**](UniverseApi.md#getUniverseSystemJumps) | **GET** /universe/system_jumps/ | Get system jumps
[**getUniverseSystemKills**](UniverseApi.md#getUniverseSystemKills) | **GET** /universe/system_kills/ | Get system kills
[**getUniverseSystems**](UniverseApi.md#getUniverseSystems) | **GET** /universe/systems/ | Get solar systems
[**getUniverseSystemsSystemId**](UniverseApi.md#getUniverseSystemsSystemId) | **GET** /universe/systems/{system_id}/ | Get solar system information
[**getUniverseTypes**](UniverseApi.md#getUniverseTypes) | **GET** /universe/types/ | Get types
[**getUniverseTypesTypeId**](UniverseApi.md#getUniverseTypesTypeId) | **GET** /universe/types/{type_id}/ | Get type information
[**postUniverseIds**](UniverseApi.md#postUniverseIds) | **POST** /universe/ids/ | Bulk names to IDs
[**postUniverseNames**](UniverseApi.md#postUniverseNames) | **POST** /universe/names/ | Get names and categories for a set of ID&#39;s


<a name="getUniverseAncestries"></a>
# **getUniverseAncestries**
> List&lt;GetUniverseAncestries200Ok&gt; getUniverseAncestries(datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get ancestries

Get all character ancestries  --- Alternate route: &#x60;/dev/universe/ancestries/&#x60;  Alternate route: &#x60;/legacy/universe/ancestries/&#x60;  Alternate route: &#x60;/v1/universe/ancestries/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetUniverseAncestries200Ok> result = apiInstance.getUniverseAncestries(datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseAncestries");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetUniverseAncestries200Ok&gt;**](GetUniverseAncestries200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseAsteroidBeltsAsteroidBeltId"></a>
# **getUniverseAsteroidBeltsAsteroidBeltId**
> GetUniverseAsteroidBeltsAsteroidBeltIdOk getUniverseAsteroidBeltsAsteroidBeltId(asteroidBeltId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get asteroid belt information

Get information on an asteroid belt  --- Alternate route: &#x60;/dev/universe/asteroid_belts/{asteroid_belt_id}/&#x60;  Alternate route: &#x60;/legacy/universe/asteroid_belts/{asteroid_belt_id}/&#x60;  Alternate route: &#x60;/v1/universe/asteroid_belts/{asteroid_belt_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer asteroidBeltId = 56; // Integer | asteroid_belt_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseAsteroidBeltsAsteroidBeltIdOk result = apiInstance.getUniverseAsteroidBeltsAsteroidBeltId(asteroidBeltId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseAsteroidBeltsAsteroidBeltId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **asteroidBeltId** | **Integer**| asteroid_belt_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseAsteroidBeltsAsteroidBeltIdOk**](GetUniverseAsteroidBeltsAsteroidBeltIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseBloodlines"></a>
# **getUniverseBloodlines**
> List&lt;GetUniverseBloodlines200Ok&gt; getUniverseBloodlines(datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get bloodlines

Get a list of bloodlines  --- Alternate route: &#x60;/dev/universe/bloodlines/&#x60;  Alternate route: &#x60;/legacy/universe/bloodlines/&#x60;  Alternate route: &#x60;/v1/universe/bloodlines/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetUniverseBloodlines200Ok> result = apiInstance.getUniverseBloodlines(datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseBloodlines");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetUniverseBloodlines200Ok&gt;**](GetUniverseBloodlines200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseCategories"></a>
# **getUniverseCategories**
> List&lt;Integer&gt; getUniverseCategories(datasource, ifNoneMatch, userAgent, xUserAgent)

Get item categories

Get a list of item categories  --- Alternate route: &#x60;/dev/universe/categories/&#x60;  Alternate route: &#x60;/legacy/universe/categories/&#x60;  Alternate route: &#x60;/v1/universe/categories/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseCategories(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseCategories");
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

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseCategoriesCategoryId"></a>
# **getUniverseCategoriesCategoryId**
> GetUniverseCategoriesCategoryIdOk getUniverseCategoriesCategoryId(categoryId, datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get item category information

Get information of an item category  --- Alternate route: &#x60;/dev/universe/categories/{category_id}/&#x60;  Alternate route: &#x60;/legacy/universe/categories/{category_id}/&#x60;  Alternate route: &#x60;/v1/universe/categories/{category_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer categoryId = 56; // Integer | An Eve item category ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseCategoriesCategoryIdOk result = apiInstance.getUniverseCategoriesCategoryId(categoryId, datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseCategoriesCategoryId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **categoryId** | **Integer**| An Eve item category ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseCategoriesCategoryIdOk**](GetUniverseCategoriesCategoryIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseConstellations"></a>
# **getUniverseConstellations**
> List&lt;Integer&gt; getUniverseConstellations(datasource, ifNoneMatch, userAgent, xUserAgent)

Get constellations

Get a list of constellations  --- Alternate route: &#x60;/dev/universe/constellations/&#x60;  Alternate route: &#x60;/legacy/universe/constellations/&#x60;  Alternate route: &#x60;/v1/universe/constellations/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseConstellations(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseConstellations");
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

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseConstellationsConstellationId"></a>
# **getUniverseConstellationsConstellationId**
> GetUniverseConstellationsConstellationIdOk getUniverseConstellationsConstellationId(constellationId, datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get constellation information

Get information on a constellation  --- Alternate route: &#x60;/dev/universe/constellations/{constellation_id}/&#x60;  Alternate route: &#x60;/legacy/universe/constellations/{constellation_id}/&#x60;  Alternate route: &#x60;/v1/universe/constellations/{constellation_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer constellationId = 56; // Integer | constellation_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseConstellationsConstellationIdOk result = apiInstance.getUniverseConstellationsConstellationId(constellationId, datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseConstellationsConstellationId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **constellationId** | **Integer**| constellation_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseConstellationsConstellationIdOk**](GetUniverseConstellationsConstellationIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseFactions"></a>
# **getUniverseFactions**
> List&lt;GetUniverseFactions200Ok&gt; getUniverseFactions(datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get factions

Get a list of factions  --- Alternate route: &#x60;/dev/universe/factions/&#x60;  Alternate route: &#x60;/v2/universe/factions/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetUniverseFactions200Ok> result = apiInstance.getUniverseFactions(datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseFactions");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetUniverseFactions200Ok&gt;**](GetUniverseFactions200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseGraphics"></a>
# **getUniverseGraphics**
> List&lt;Integer&gt; getUniverseGraphics(datasource, ifNoneMatch, userAgent, xUserAgent)

Get graphics

Get a list of graphics  --- Alternate route: &#x60;/dev/universe/graphics/&#x60;  Alternate route: &#x60;/legacy/universe/graphics/&#x60;  Alternate route: &#x60;/v1/universe/graphics/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseGraphics(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseGraphics");
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

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseGraphicsGraphicId"></a>
# **getUniverseGraphicsGraphicId**
> GetUniverseGraphicsGraphicIdOk getUniverseGraphicsGraphicId(graphicId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get graphic information

Get information on a graphic  --- Alternate route: &#x60;/dev/universe/graphics/{graphic_id}/&#x60;  Alternate route: &#x60;/legacy/universe/graphics/{graphic_id}/&#x60;  Alternate route: &#x60;/v1/universe/graphics/{graphic_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer graphicId = 56; // Integer | graphic_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseGraphicsGraphicIdOk result = apiInstance.getUniverseGraphicsGraphicId(graphicId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseGraphicsGraphicId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **graphicId** | **Integer**| graphic_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseGraphicsGraphicIdOk**](GetUniverseGraphicsGraphicIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseGroups"></a>
# **getUniverseGroups**
> List&lt;Integer&gt; getUniverseGroups(datasource, ifNoneMatch, page, userAgent, xUserAgent)

Get item groups

Get a list of item groups  --- Alternate route: &#x60;/dev/universe/groups/&#x60;  Alternate route: &#x60;/legacy/universe/groups/&#x60;  Alternate route: &#x60;/v1/universe/groups/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
Integer page = 1; // Integer | Which page of results to return
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseGroups(datasource, ifNoneMatch, page, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseGroups");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **page** | **Integer**| Which page of results to return | [optional] [default to 1]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseGroupsGroupId"></a>
# **getUniverseGroupsGroupId**
> GetUniverseGroupsGroupIdOk getUniverseGroupsGroupId(groupId, datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get item group information

Get information on an item group  --- Alternate route: &#x60;/dev/universe/groups/{group_id}/&#x60;  Alternate route: &#x60;/legacy/universe/groups/{group_id}/&#x60;  Alternate route: &#x60;/v1/universe/groups/{group_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer groupId = 56; // Integer | An Eve item group ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseGroupsGroupIdOk result = apiInstance.getUniverseGroupsGroupId(groupId, datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseGroupsGroupId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **groupId** | **Integer**| An Eve item group ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseGroupsGroupIdOk**](GetUniverseGroupsGroupIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseMoonsMoonId"></a>
# **getUniverseMoonsMoonId**
> GetUniverseMoonsMoonIdOk getUniverseMoonsMoonId(moonId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get moon information

Get information on a moon  --- Alternate route: &#x60;/dev/universe/moons/{moon_id}/&#x60;  Alternate route: &#x60;/legacy/universe/moons/{moon_id}/&#x60;  Alternate route: &#x60;/v1/universe/moons/{moon_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer moonId = 56; // Integer | moon_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseMoonsMoonIdOk result = apiInstance.getUniverseMoonsMoonId(moonId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseMoonsMoonId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **moonId** | **Integer**| moon_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseMoonsMoonIdOk**](GetUniverseMoonsMoonIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniversePlanetsPlanetId"></a>
# **getUniversePlanetsPlanetId**
> GetUniversePlanetsPlanetIdOk getUniversePlanetsPlanetId(planetId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get planet information

Get information on a planet  --- Alternate route: &#x60;/dev/universe/planets/{planet_id}/&#x60;  Alternate route: &#x60;/legacy/universe/planets/{planet_id}/&#x60;  Alternate route: &#x60;/v1/universe/planets/{planet_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer planetId = 56; // Integer | planet_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniversePlanetsPlanetIdOk result = apiInstance.getUniversePlanetsPlanetId(planetId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniversePlanetsPlanetId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **planetId** | **Integer**| planet_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniversePlanetsPlanetIdOk**](GetUniversePlanetsPlanetIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseRaces"></a>
# **getUniverseRaces**
> List&lt;GetUniverseRaces200Ok&gt; getUniverseRaces(datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get character races

Get a list of character races  --- Alternate route: &#x60;/dev/universe/races/&#x60;  Alternate route: &#x60;/legacy/universe/races/&#x60;  Alternate route: &#x60;/v1/universe/races/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetUniverseRaces200Ok> result = apiInstance.getUniverseRaces(datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseRaces");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetUniverseRaces200Ok&gt;**](GetUniverseRaces200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseRegions"></a>
# **getUniverseRegions**
> List&lt;Integer&gt; getUniverseRegions(datasource, ifNoneMatch, userAgent, xUserAgent)

Get regions

Get a list of regions  --- Alternate route: &#x60;/dev/universe/regions/&#x60;  Alternate route: &#x60;/legacy/universe/regions/&#x60;  Alternate route: &#x60;/v1/universe/regions/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseRegions(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseRegions");
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

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseRegionsRegionId"></a>
# **getUniverseRegionsRegionId**
> GetUniverseRegionsRegionIdOk getUniverseRegionsRegionId(regionId, datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get region information

Get information on a region  --- Alternate route: &#x60;/dev/universe/regions/{region_id}/&#x60;  Alternate route: &#x60;/legacy/universe/regions/{region_id}/&#x60;  Alternate route: &#x60;/v1/universe/regions/{region_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer regionId = 56; // Integer | region_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseRegionsRegionIdOk result = apiInstance.getUniverseRegionsRegionId(regionId, datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseRegionsRegionId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **regionId** | **Integer**| region_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseRegionsRegionIdOk**](GetUniverseRegionsRegionIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseStargatesStargateId"></a>
# **getUniverseStargatesStargateId**
> GetUniverseStargatesStargateIdOk getUniverseStargatesStargateId(stargateId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get stargate information

Get information on a stargate  --- Alternate route: &#x60;/dev/universe/stargates/{stargate_id}/&#x60;  Alternate route: &#x60;/legacy/universe/stargates/{stargate_id}/&#x60;  Alternate route: &#x60;/v1/universe/stargates/{stargate_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer stargateId = 56; // Integer | stargate_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseStargatesStargateIdOk result = apiInstance.getUniverseStargatesStargateId(stargateId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseStargatesStargateId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stargateId** | **Integer**| stargate_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseStargatesStargateIdOk**](GetUniverseStargatesStargateIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseStarsStarId"></a>
# **getUniverseStarsStarId**
> GetUniverseStarsStarIdOk getUniverseStarsStarId(starId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get star information

Get information on a star  --- Alternate route: &#x60;/dev/universe/stars/{star_id}/&#x60;  Alternate route: &#x60;/legacy/universe/stars/{star_id}/&#x60;  Alternate route: &#x60;/v1/universe/stars/{star_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer starId = 56; // Integer | star_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseStarsStarIdOk result = apiInstance.getUniverseStarsStarId(starId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseStarsStarId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **starId** | **Integer**| star_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseStarsStarIdOk**](GetUniverseStarsStarIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseStationsStationId"></a>
# **getUniverseStationsStationId**
> GetUniverseStationsStationIdOk getUniverseStationsStationId(stationId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get station information

Get information on a station  --- Alternate route: &#x60;/dev/universe/stations/{station_id}/&#x60;  Alternate route: &#x60;/v2/universe/stations/{station_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer stationId = 56; // Integer | station_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseStationsStationIdOk result = apiInstance.getUniverseStationsStationId(stationId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseStationsStationId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **stationId** | **Integer**| station_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseStationsStationIdOk**](GetUniverseStationsStationIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseStructures"></a>
# **getUniverseStructures**
> List&lt;Long&gt; getUniverseStructures(datasource, ifNoneMatch, userAgent, xUserAgent)

List all public structures

List all public structures  --- Alternate route: &#x60;/dev/universe/structures/&#x60;  Alternate route: &#x60;/legacy/universe/structures/&#x60;  Alternate route: &#x60;/v1/universe/structures/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Long> result = apiInstance.getUniverseStructures(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseStructures");
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

**List&lt;Long&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseStructuresStructureId"></a>
# **getUniverseStructuresStructureId**
> GetUniverseStructuresStructureIdOk getUniverseStructuresStructureId(structureId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get structure information

Returns information on requested structure, if you are on the ACL. Otherwise, returns \&quot;Forbidden\&quot; for all inputs.  --- Alternate route: &#x60;/dev/universe/structures/{structure_id}/&#x60;  Alternate route: &#x60;/legacy/universe/structures/{structure_id}/&#x60;  Alternate route: &#x60;/v1/universe/structures/{structure_id}/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

UniverseApi apiInstance = new UniverseApi();
Long structureId = 789L; // Long | An Eve structure ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseStructuresStructureIdOk result = apiInstance.getUniverseStructuresStructureId(structureId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseStructuresStructureId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **structureId** | **Long**| An Eve structure ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseStructuresStructureIdOk**](GetUniverseStructuresStructureIdOk.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseSystemJumps"></a>
# **getUniverseSystemJumps**
> List&lt;GetUniverseSystemJumps200Ok&gt; getUniverseSystemJumps(datasource, ifNoneMatch, userAgent, xUserAgent)

Get system jumps

Get the number of jumps in solar systems within the last hour ending at the timestamp of the Last-Modified header, excluding wormhole space. Only systems with jumps will be listed  --- Alternate route: &#x60;/dev/universe/system_jumps/&#x60;  Alternate route: &#x60;/legacy/universe/system_jumps/&#x60;  Alternate route: &#x60;/v1/universe/system_jumps/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetUniverseSystemJumps200Ok> result = apiInstance.getUniverseSystemJumps(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseSystemJumps");
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

[**List&lt;GetUniverseSystemJumps200Ok&gt;**](GetUniverseSystemJumps200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseSystemKills"></a>
# **getUniverseSystemKills**
> List&lt;GetUniverseSystemKills200Ok&gt; getUniverseSystemKills(datasource, ifNoneMatch, userAgent, xUserAgent)

Get system kills

Get the number of ship, pod and NPC kills per solar system within the last hour ending at the timestamp of the Last-Modified header, excluding wormhole space. Only systems with kills will be listed  --- Alternate route: &#x60;/dev/universe/system_kills/&#x60;  Alternate route: &#x60;/v2/universe/system_kills/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetUniverseSystemKills200Ok> result = apiInstance.getUniverseSystemKills(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseSystemKills");
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

[**List&lt;GetUniverseSystemKills200Ok&gt;**](GetUniverseSystemKills200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseSystems"></a>
# **getUniverseSystems**
> List&lt;Integer&gt; getUniverseSystems(datasource, ifNoneMatch, userAgent, xUserAgent)

Get solar systems

Get a list of solar systems  --- Alternate route: &#x60;/dev/universe/systems/&#x60;  Alternate route: &#x60;/legacy/universe/systems/&#x60;  Alternate route: &#x60;/v1/universe/systems/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseSystems(datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseSystems");
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

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseSystemsSystemId"></a>
# **getUniverseSystemsSystemId**
> GetUniverseSystemsSystemIdOk getUniverseSystemsSystemId(systemId, datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get solar system information

Get information on a solar system  --- Alternate route: &#x60;/dev/universe/systems/{system_id}/&#x60;  Alternate route: &#x60;/v3/universe/systems/{system_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer systemId = 56; // Integer | system_id integer
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseSystemsSystemIdOk result = apiInstance.getUniverseSystemsSystemId(systemId, datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseSystemsSystemId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **systemId** | **Integer**| system_id integer |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseSystemsSystemIdOk**](GetUniverseSystemsSystemIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseTypes"></a>
# **getUniverseTypes**
> List&lt;Integer&gt; getUniverseTypes(datasource, ifNoneMatch, page, userAgent, xUserAgent)

Get types

Get a list of type ids  --- Alternate route: &#x60;/dev/universe/types/&#x60;  Alternate route: &#x60;/legacy/universe/types/&#x60;  Alternate route: &#x60;/v1/universe/types/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
Integer page = 1; // Integer | Which page of results to return
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<Integer> result = apiInstance.getUniverseTypes(datasource, ifNoneMatch, page, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseTypes");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **page** | **Integer**| Which page of results to return | [optional] [default to 1]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

**List&lt;Integer&gt;**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getUniverseTypesTypeId"></a>
# **getUniverseTypesTypeId**
> GetUniverseTypesTypeIdOk getUniverseTypesTypeId(typeId, datasource, ifNoneMatch, language, userAgent, xUserAgent)

Get type information

Get information on a type  --- Alternate route: &#x60;/dev/universe/types/{type_id}/&#x60;  Alternate route: &#x60;/v3/universe/types/{type_id}/&#x60;  --- This route expires daily at 11:05

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
Integer typeId = 56; // Integer | An Eve item type ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetUniverseTypesTypeIdOk result = apiInstance.getUniverseTypesTypeId(typeId, datasource, ifNoneMatch, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#getUniverseTypesTypeId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **typeId** | **Integer**| An Eve item type ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetUniverseTypesTypeIdOk**](GetUniverseTypesTypeIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postUniverseIds"></a>
# **postUniverseIds**
> PostUniverseIdsOk postUniverseIds(names, datasource, language, userAgent, xUserAgent)

Bulk names to IDs

Resolve a set of names to IDs in the following categories: agents, alliances, characters, constellations, corporations factions, inventory_types, regions, stations, and systems. Only exact matches will be returned. All names searched for are cached for 12 hours.  --- Alternate route: &#x60;/dev/universe/ids/&#x60;  Alternate route: &#x60;/legacy/universe/ids/&#x60;  Alternate route: &#x60;/v1/universe/ids/&#x60; 

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
List<String> names = Arrays.asList(new List<String>()); // List<String> | The names to resolve
String datasource = "tranquility"; // String | The server name you would like data from
String language = "en-us"; // String | Language to use in the response
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    PostUniverseIdsOk result = apiInstance.postUniverseIds(names, datasource, language, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#postUniverseIds");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **names** | **List&lt;String&gt;**| The names to resolve |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **language** | **String**| Language to use in the response | [optional] [default to en-us] [enum: de, en-us, fr, ja, ru, zh]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**PostUniverseIdsOk**](PostUniverseIdsOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postUniverseNames"></a>
# **postUniverseNames**
> List&lt;PostUniverseNames200Ok&gt; postUniverseNames(ids, datasource, userAgent, xUserAgent)

Get names and categories for a set of ID&#39;s

Resolve a set of IDs to names and categories. Supported ID&#39;s for resolving are: Characters, Corporations, Alliances, Stations, Solar Systems, Constellations, Regions, Types.  --- Alternate route: &#x60;/dev/universe/names/&#x60;  Alternate route: &#x60;/v2/universe/names/&#x60; 

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.UniverseApi;


UniverseApi apiInstance = new UniverseApi();
List<Integer> ids = Arrays.asList(new List<Integer>()); // List<Integer> | The ids to resolve
String datasource = "tranquility"; // String | The server name you would like data from
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<PostUniverseNames200Ok> result = apiInstance.postUniverseNames(ids, datasource, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling UniverseApi#postUniverseNames");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | **List&lt;Integer&gt;**| The ids to resolve |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;PostUniverseNames200Ok&gt;**](PostUniverseNames200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

