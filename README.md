# assignment-store-lookup
The goal of this test assignment is to implement Java Service returning 5 closest stores for the given coordinate.

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

