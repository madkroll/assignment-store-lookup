# assignment-store-lookup
The goal of this test assignment is to implement Java Service returning 5 closest stores for the given coordinate.
Solution is implemented as a Java Spring Boot application performing search using Apache Lucene framework.
Apache Lucene is a framework specifically designed for doing highly performing very fast searches.
It provides reach API and Query language making it very easy to find entries by complex compositions of conditions, 
at the same time finding results very fast.

Application loads original data from JSON located in classpath during the initialization.
During startup, it performs indexing using Apache Lucene utilities and makes all documents available for searching and querying.

Once application is up and running - REST endpoints are published and provide access to indexed data.
All calculations and query processing happens in Apache Lucene, including geospatial calculations.

There are two endpoints available:
- /lookup/store - to search only for stores with the possibility to show only those having a pickup point
- /lookup/pickup - to search only for pickup locations, not necessary stores

As another idea what is possible to add as a new feature: show only stores open now.

# Build
`mvn clean package`

# Run
`java -jar ./store-lookup-rest/target/store-lookup-rest-1.0.0-SNAPSHOT.jar`

# [Original task] Query 5 closest stores
```shell
curl -X GET "http://localhost:8080/lookup/store?latitude=51.503687&longitude=5.414346" \
    -H "Accept: application/json"
```

# [Additional task] Query 5 closest stores but only with pickup point
```shell
curl -X GET "http://localhost:8080/lookup/store?latitude=51.503687&longitude=5.414346&collectionPointAvailable=false" \
    -H "Accept: application/json"
````

# [Additional task] Query 5 closest pickup points, not necessary stores
```shell
curl -X GET "http://localhost:8080/lookup/pickup?latitude=51.503687&longitude=5.414346" \
    -H "Accept: application/json"
```

