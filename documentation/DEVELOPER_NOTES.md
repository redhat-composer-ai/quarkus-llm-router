# Developer Notes

## General Application Architecture

### Using Microprofile CDI to Set and Override Default Values

**Configuration Update**

#### 2025-Feb-26 

Discovered that for some reason, CDI (ArC) was not injecting the interface config definitions nor the @ConfigProperty values from _application.properties_ into the base class _BaseRetrieverConnectionEntity.java_.  Also @PostConstuct was not being called.  This also occurred in sample code using the Quarkus QuickStarts sample file “develop-config”.  

As a workaround, the injection happens in the _AssistantInfoService#createUpdateRetrieverConnectionEntity_
and in this method if **maxResults** and **minScore** are null, the  default values are applied.

Please see the tests within _AssistantAdminApiRetrConnExampleTest.java_ for examples of how the methods are tested.


#### 2025-Feb-25

See [REDSAIA-135](https://issues.redhat.com/browse/REDSAIA-135) for more information on this story.

**Objective**

After implementation of the solution of the story, configuration properties for **maxResults**, **minScore** existed in 2 places:
* _application.properties_ with defaults, can be overridden by higher precedence configs - from the latest fix for 136
* _ContentRetrieverConfig.java_  with hard-coded defaults values applied using microprofile ConfigMapping

>[!NOTE] Note also that the langchain4j class EmbeddingStoreContentRetriever also provides defaults if maxResults and minScore are undefined.



**Recommended Approach** 

Using Microprofile ConfigMapping already written: _ContentRetrieverConfig.java_, remove the annotations that define the defaults from that file.  The reason is that Microprofile config already handles missing configurations in application.properties, or any of the other configuration environments that may exist.

For example, in the application.properties file, max-results and min-score are defined with environmental values to be overridden, or defaults as the values following the colon (5) or (0.0) as below:
```java
conductor.retriever.max-results = ${CONDUCTOR_RETRIEVER_MAX_RESULTS:5}
conductor.retriever.min-score   = ${CONDUCTOR_RETRIEVER_MIN_SCORE:0.0}
```
If these values are note defined e.g. were commented out, and NOT defined from either an environment variable such as:
```bash
	export CONDUCTOR_RETRIEVER_MAX_RESULTS=7
```    
or using the system variable e.g. 
```bash
	-Dconductor.retriever.max-results=25 
```    
on the command line, the application will fail to compile.

So the recommendation is to remove the defaults in _ContentRetrieverConfig.java_ in deference to the _application.properties_, that places the configuration in a single location that can be overridden at runtime as desired.

