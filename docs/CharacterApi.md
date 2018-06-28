# CharacterApi

All URIs are relative to *https://esi.evetech.net/latest*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCharactersCharacterId**](CharacterApi.md#getCharactersCharacterId) | **GET** /characters/{character_id}/ | Get character&#39;s public information
[**getCharactersCharacterIdAgentsResearch**](CharacterApi.md#getCharactersCharacterIdAgentsResearch) | **GET** /characters/{character_id}/agents_research/ | Get agents research
[**getCharactersCharacterIdBlueprints**](CharacterApi.md#getCharactersCharacterIdBlueprints) | **GET** /characters/{character_id}/blueprints/ | Get blueprints
[**getCharactersCharacterIdCorporationhistory**](CharacterApi.md#getCharactersCharacterIdCorporationhistory) | **GET** /characters/{character_id}/corporationhistory/ | Get corporation history
[**getCharactersCharacterIdFatigue**](CharacterApi.md#getCharactersCharacterIdFatigue) | **GET** /characters/{character_id}/fatigue/ | Get jump fatigue
[**getCharactersCharacterIdMedals**](CharacterApi.md#getCharactersCharacterIdMedals) | **GET** /characters/{character_id}/medals/ | Get medals
[**getCharactersCharacterIdNotifications**](CharacterApi.md#getCharactersCharacterIdNotifications) | **GET** /characters/{character_id}/notifications/ | Get character notifications
[**getCharactersCharacterIdNotificationsContacts**](CharacterApi.md#getCharactersCharacterIdNotificationsContacts) | **GET** /characters/{character_id}/notifications/contacts/ | Get new contact notifications
[**getCharactersCharacterIdPortrait**](CharacterApi.md#getCharactersCharacterIdPortrait) | **GET** /characters/{character_id}/portrait/ | Get character portraits
[**getCharactersCharacterIdRoles**](CharacterApi.md#getCharactersCharacterIdRoles) | **GET** /characters/{character_id}/roles/ | Get character corporation roles
[**getCharactersCharacterIdStandings**](CharacterApi.md#getCharactersCharacterIdStandings) | **GET** /characters/{character_id}/standings/ | Get standings
[**getCharactersCharacterIdStats**](CharacterApi.md#getCharactersCharacterIdStats) | **GET** /characters/{character_id}/stats/ | Yearly aggregate stats
[**getCharactersCharacterIdTitles**](CharacterApi.md#getCharactersCharacterIdTitles) | **GET** /characters/{character_id}/titles/ | Get character corporation titles
[**getCharactersNames**](CharacterApi.md#getCharactersNames) | **GET** /characters/names/ | Get character names
[**postCharactersAffiliation**](CharacterApi.md#postCharactersAffiliation) | **POST** /characters/affiliation/ | Character affiliation
[**postCharactersCharacterIdCspa**](CharacterApi.md#postCharactersCharacterIdCspa) | **POST** /characters/{character_id}/cspa/ | Calculate a CSPA charge cost


<a name="getCharactersCharacterId"></a>
# **getCharactersCharacterId**
> GetCharactersCharacterIdOk getCharactersCharacterId(characterId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get character&#39;s public information

Public information about a character  --- Alternate route: &#x60;/dev/characters/{character_id}/&#x60;  Alternate route: &#x60;/v4/characters/{character_id}/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;


CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetCharactersCharacterIdOk result = apiInstance.getCharactersCharacterId(characterId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterId");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetCharactersCharacterIdOk**](GetCharactersCharacterIdOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdAgentsResearch"></a>
# **getCharactersCharacterIdAgentsResearch**
> List&lt;GetCharactersCharacterIdAgentsResearch200Ok&gt; getCharactersCharacterIdAgentsResearch(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get agents research

Return a list of agents research information for a character. The formula for finding the current research points with an agent is: currentPoints &#x3D; remainderPoints + pointsPerDay * days(currentTime - researchStartDate)  --- Alternate route: &#x60;/dev/characters/{character_id}/agents_research/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/agents_research/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/agents_research/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdAgentsResearch200Ok> result = apiInstance.getCharactersCharacterIdAgentsResearch(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdAgentsResearch");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdAgentsResearch200Ok&gt;**](GetCharactersCharacterIdAgentsResearch200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdBlueprints"></a>
# **getCharactersCharacterIdBlueprints**
> List&lt;GetCharactersCharacterIdBlueprints200Ok&gt; getCharactersCharacterIdBlueprints(characterId, datasource, ifNoneMatch, page, token, userAgent, xUserAgent)

Get blueprints

Return a list of blueprints the character owns  --- Alternate route: &#x60;/dev/characters/{character_id}/blueprints/&#x60;  Alternate route: &#x60;/v2/characters/{character_id}/blueprints/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
Integer page = 1; // Integer | Which page of results to return
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdBlueprints200Ok> result = apiInstance.getCharactersCharacterIdBlueprints(characterId, datasource, ifNoneMatch, page, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdBlueprints");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **page** | **Integer**| Which page of results to return | [optional] [default to 1]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdBlueprints200Ok&gt;**](GetCharactersCharacterIdBlueprints200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdCorporationhistory"></a>
# **getCharactersCharacterIdCorporationhistory**
> List&lt;GetCharactersCharacterIdCorporationhistory200Ok&gt; getCharactersCharacterIdCorporationhistory(characterId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get corporation history

Get a list of all the corporations a character has been a member of  --- Alternate route: &#x60;/dev/characters/{character_id}/corporationhistory/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/corporationhistory/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/corporationhistory/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;


CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdCorporationhistory200Ok> result = apiInstance.getCharactersCharacterIdCorporationhistory(characterId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdCorporationhistory");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdCorporationhistory200Ok&gt;**](GetCharactersCharacterIdCorporationhistory200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdFatigue"></a>
# **getCharactersCharacterIdFatigue**
> GetCharactersCharacterIdFatigueOk getCharactersCharacterIdFatigue(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get jump fatigue

Return a character&#39;s jump activation and fatigue information  --- Alternate route: &#x60;/dev/characters/{character_id}/fatigue/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/fatigue/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/fatigue/&#x60;  --- This route is cached for up to 300 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetCharactersCharacterIdFatigueOk result = apiInstance.getCharactersCharacterIdFatigue(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdFatigue");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetCharactersCharacterIdFatigueOk**](GetCharactersCharacterIdFatigueOk.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdMedals"></a>
# **getCharactersCharacterIdMedals**
> List&lt;GetCharactersCharacterIdMedals200Ok&gt; getCharactersCharacterIdMedals(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get medals

Return a list of medals the character has  --- Alternate route: &#x60;/dev/characters/{character_id}/medals/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/medals/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/medals/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdMedals200Ok> result = apiInstance.getCharactersCharacterIdMedals(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdMedals");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdMedals200Ok&gt;**](GetCharactersCharacterIdMedals200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdNotifications"></a>
# **getCharactersCharacterIdNotifications**
> List&lt;GetCharactersCharacterIdNotifications200Ok&gt; getCharactersCharacterIdNotifications(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get character notifications

Return character notifications  --- Alternate route: &#x60;/dev/characters/{character_id}/notifications/&#x60;  Alternate route: &#x60;/v2/characters/{character_id}/notifications/&#x60;  --- This route is cached for up to 600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdNotifications200Ok> result = apiInstance.getCharactersCharacterIdNotifications(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdNotifications");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdNotifications200Ok&gt;**](GetCharactersCharacterIdNotifications200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdNotificationsContacts"></a>
# **getCharactersCharacterIdNotificationsContacts**
> List&lt;GetCharactersCharacterIdNotificationsContacts200Ok&gt; getCharactersCharacterIdNotificationsContacts(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get new contact notifications

Return notifications about having been added to someone&#39;s contact list  --- Alternate route: &#x60;/dev/characters/{character_id}/notifications/contacts/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/notifications/contacts/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/notifications/contacts/&#x60;  --- This route is cached for up to 600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdNotificationsContacts200Ok> result = apiInstance.getCharactersCharacterIdNotificationsContacts(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdNotificationsContacts");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdNotificationsContacts200Ok&gt;**](GetCharactersCharacterIdNotificationsContacts200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdPortrait"></a>
# **getCharactersCharacterIdPortrait**
> GetCharactersCharacterIdPortraitOk getCharactersCharacterIdPortrait(characterId, datasource, ifNoneMatch, userAgent, xUserAgent)

Get character portraits

Get portrait urls for a character  --- Alternate route: &#x60;/dev/characters/{character_id}/portrait/&#x60;  Alternate route: &#x60;/v2/characters/{character_id}/portrait/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;


CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetCharactersCharacterIdPortraitOk result = apiInstance.getCharactersCharacterIdPortrait(characterId, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdPortrait");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetCharactersCharacterIdPortraitOk**](GetCharactersCharacterIdPortraitOk.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdRoles"></a>
# **getCharactersCharacterIdRoles**
> GetCharactersCharacterIdRolesOk getCharactersCharacterIdRoles(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get character corporation roles

Returns a character&#39;s corporation roles  --- Alternate route: &#x60;/dev/characters/{character_id}/roles/&#x60;  Alternate route: &#x60;/v2/characters/{character_id}/roles/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    GetCharactersCharacterIdRolesOk result = apiInstance.getCharactersCharacterIdRoles(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdRoles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**GetCharactersCharacterIdRolesOk**](GetCharactersCharacterIdRolesOk.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdStandings"></a>
# **getCharactersCharacterIdStandings**
> List&lt;GetCharactersCharacterIdStandings200Ok&gt; getCharactersCharacterIdStandings(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get standings

Return character standings from agents, NPC corporations, and factions  --- Alternate route: &#x60;/dev/characters/{character_id}/standings/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/standings/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/standings/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdStandings200Ok> result = apiInstance.getCharactersCharacterIdStandings(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdStandings");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdStandings200Ok&gt;**](GetCharactersCharacterIdStandings200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdStats"></a>
# **getCharactersCharacterIdStats**
> List&lt;GetCharactersCharacterIdStats200Ok&gt; getCharactersCharacterIdStats(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Yearly aggregate stats

Returns aggregate yearly stats for a character  --- Alternate route: &#x60;/dev/characters/{character_id}/stats/&#x60;  Alternate route: &#x60;/v2/characters/{character_id}/stats/&#x60;  --- This route is cached for up to 86400 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdStats200Ok> result = apiInstance.getCharactersCharacterIdStats(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdStats");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdStats200Ok&gt;**](GetCharactersCharacterIdStats200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersCharacterIdTitles"></a>
# **getCharactersCharacterIdTitles**
> List&lt;GetCharactersCharacterIdTitles200Ok&gt; getCharactersCharacterIdTitles(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent)

Get character corporation titles

Returns a character&#39;s titles  --- Alternate route: &#x60;/dev/characters/{character_id}/titles/&#x60;  Alternate route: &#x60;/legacy/characters/{character_id}/titles/&#x60;  Alternate route: &#x60;/v1/characters/{character_id}/titles/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersCharacterIdTitles200Ok> result = apiInstance.getCharactersCharacterIdTitles(characterId, datasource, ifNoneMatch, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersCharacterIdTitles");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersCharacterIdTitles200Ok&gt;**](GetCharactersCharacterIdTitles200Ok.md)

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="getCharactersNames"></a>
# **getCharactersNames**
> List&lt;GetCharactersNames200Ok&gt; getCharactersNames(characterIds, datasource, ifNoneMatch, userAgent, xUserAgent)

Get character names

Resolve a set of character IDs to character names  --- Alternate route: &#x60;/dev/characters/names/&#x60;  Alternate route: &#x60;/legacy/characters/names/&#x60;  Alternate route: &#x60;/v1/characters/names/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;


CharacterApi apiInstance = new CharacterApi();
List<Long> characterIds = Arrays.asList(56L); // List<Long> | A comma separated list of character IDs
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<GetCharactersNames200Ok> result = apiInstance.getCharactersNames(characterIds, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#getCharactersNames");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterIds** | [**List&lt;Long&gt;**](Long.md)| A comma separated list of character IDs |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;GetCharactersNames200Ok&gt;**](GetCharactersNames200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postCharactersAffiliation"></a>
# **postCharactersAffiliation**
> List&lt;PostCharactersAffiliation200Ok&gt; postCharactersAffiliation(characters, datasource, ifNoneMatch, userAgent, xUserAgent)

Character affiliation

Bulk lookup of character IDs to corporation, alliance and faction  --- Alternate route: &#x60;/dev/characters/affiliation/&#x60;  Alternate route: &#x60;/legacy/characters/affiliation/&#x60;  Alternate route: &#x60;/v1/characters/affiliation/&#x60;  --- This route is cached for up to 3600 seconds

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;


CharacterApi apiInstance = new CharacterApi();
List<Integer> characters = Arrays.asList(new List<Integer>()); // List<Integer> | The character IDs to fetch affiliations for. All characters must exist, or none will be returned.
String datasource = "tranquility"; // String | The server name you would like data from
String ifNoneMatch = "ifNoneMatch_example"; // String | ETag from a previous request. A 304 will be returned if this matches the current ETag
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    List<PostCharactersAffiliation200Ok> result = apiInstance.postCharactersAffiliation(characters, datasource, ifNoneMatch, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#postCharactersAffiliation");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characters** | **List&lt;Integer&gt;**| The character IDs to fetch affiliations for. All characters must exist, or none will be returned. |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **ifNoneMatch** | **String**| ETag from a previous request. A 304 will be returned if this matches the current ETag | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

[**List&lt;PostCharactersAffiliation200Ok&gt;**](PostCharactersAffiliation200Ok.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="postCharactersCharacterIdCspa"></a>
# **postCharactersCharacterIdCspa**
> Float postCharactersCharacterIdCspa(characterId, characters, datasource, token, userAgent, xUserAgent)

Calculate a CSPA charge cost

Takes a source character ID in the url and a set of target character ID&#39;s in the body, returns a CSPA charge cost  --- Alternate route: &#x60;/dev/characters/{character_id}/cspa/&#x60;  Alternate route: &#x60;/v4/characters/{character_id}/cspa/&#x60; 

### Example
```java
// Import classes:
//import com.github.drsmugleaf.eve.esi.ApiClient;
//import com.github.drsmugleaf.eve.esi.ApiException;
//import com.github.drsmugleaf.eve.esi.Configuration;
//import com.github.drsmugleaf.eve.esi.auth.*;
//import com.github.drsmugleaf.eve.esi.api.CharacterApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure OAuth2 access token for authorization: evesso
OAuth evesso = (OAuth) defaultClient.getAuthentication("evesso");
evesso.setAccessToken("YOUR ACCESS TOKEN");

CharacterApi apiInstance = new CharacterApi();
Integer characterId = 56; // Integer | An EVE character ID
List<Integer> characters = Arrays.asList(new List<Integer>()); // List<Integer> | The target characters to calculate the charge for
String datasource = "tranquility"; // String | The server name you would like data from
String token = "token_example"; // String | Access token to use if unable to set a header
String userAgent = "userAgent_example"; // String | Client identifier, takes precedence over headers
String xUserAgent = "xUserAgent_example"; // String | Client identifier, takes precedence over User-Agent
try {
    Float result = apiInstance.postCharactersCharacterIdCspa(characterId, characters, datasource, token, userAgent, xUserAgent);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CharacterApi#postCharactersCharacterIdCspa");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **characterId** | **Integer**| An EVE character ID |
 **characters** | **List&lt;Integer&gt;**| The target characters to calculate the charge for |
 **datasource** | **String**| The server name you would like data from | [optional] [default to tranquility] [enum: tranquility, singularity]
 **token** | **String**| Access token to use if unable to set a header | [optional]
 **userAgent** | **String**| Client identifier, takes precedence over headers | [optional]
 **xUserAgent** | **String**| Client identifier, takes precedence over User-Agent | [optional]

### Return type

**Float**

### Authorization

[evesso](../README.md#evesso)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

