# ProductApi

All URIs are relative to */*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getProducts**](ProductApi.md#getProducts) | **GET** /Product | 
[**productIdDelete**](ProductApi.md#productIdDelete) | **DELETE** /Product/{id} | 
[**productIdGet**](ProductApi.md#productIdGet) | **GET** /Product/{id} | 
[**productIdPut**](ProductApi.md#productIdPut) | **PUT** /Product/{id} | 
[**productPost**](ProductApi.md#productPost) | **POST** /Product | 

<a name="getProducts"></a>
# **getProducts**
> kotlin.Array&lt;Product&gt; getProducts()



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = ProductApi()
try {
    val result : kotlin.Array<Product> = apiInstance.getProducts()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProductApi#getProducts")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProductApi#getProducts")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**kotlin.Array&lt;Product&gt;**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json

<a name="productIdDelete"></a>
# **productIdDelete**
> kotlin.String productIdDelete(id)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = ProductApi()
val id : kotlin.String = id_example // kotlin.String | 
try {
    val result : kotlin.String = apiInstance.productIdDelete(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProductApi#productIdDelete")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProductApi#productIdDelete")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**|  |

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json

<a name="productIdGet"></a>
# **productIdGet**
> Product productIdGet(id)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = ProductApi()
val id : kotlin.String = id_example // kotlin.String | 
try {
    val result : Product = apiInstance.productIdGet(id)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProductApi#productIdGet")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProductApi#productIdGet")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**|  |

### Return type

[**Product**](Product.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain, application/json, text/json

<a name="productIdPut"></a>
# **productIdPut**
> kotlin.String productIdPut(id, body)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = ProductApi()
val id : kotlin.String = id_example // kotlin.String | 
val body : Product =  // Product | 
try {
    val result : kotlin.String = apiInstance.productIdPut(id, body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProductApi#productIdPut")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProductApi#productIdPut")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **kotlin.String**|  |
 **body** | [**Product**](Product.md)|  | [optional]

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/_*+json
 - **Accept**: text/plain, application/json, text/json

<a name="productPost"></a>
# **productPost**
> kotlin.String productPost(body)



### Example
```kotlin
// Import classes:
//import io.swagger.client.infrastructure.*
//import io.swagger.client.models.*;

val apiInstance = ProductApi()
val body : Product =  // Product | 
try {
    val result : kotlin.String = apiInstance.productPost(body)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling ProductApi#productPost")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling ProductApi#productPost")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | [**Product**](Product.md)|  | [optional]

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json, text/json, application/_*+json
 - **Accept**: text/plain, application/json, text/json

