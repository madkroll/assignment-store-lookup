# assignment-store-lookup
The goal of this test assignment is to implement Java Service returning 5 closest stores for the given coordinate.

# Build
`mvn clean package`

# Run
`java -jar ./store-lookup-rest/target/store-lookup-rest-1.0.0-SNAPSHOT.jar`

# Query 5 closest stores
curl -X GET "http://localhost:8080/lookup/store?latitude=51.503687&longitude=5.414346" \
    -H "Accept: application/json"

# Query 5 closest pickup points
curl -X GET "http://localhost:8080/lookup/pickup?latitude=51.503687&longitude=5.414346" \
    -H "Accept: application/json"
