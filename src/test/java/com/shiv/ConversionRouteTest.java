package com.shiv;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;
@QuarkusTest
public class ConversionRouteTest {
    @Test
    public void testXmlToJsonConversion() {
        String xmlPayload = "<example><name>John</name></example>";
        RestAssured.given()
                .body(xmlPayload)
                .contentType(ContentType.XML)
                .when().post("/convert/xmltojson")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", is("John")); // Adjust if your JSON structure is different
    }
    @Test
    public void testJsonToXmlConversion() {
        String jsonPayload = "{\"name\":\"John\"}";
        RestAssured.given()
                .body(jsonPayload)
                .contentType(ContentType.JSON)
                .when().post("/convert/jsontoxml")
                .then()
                .statusCode(200)
                .contentType(ContentType.XML)
                .body("example.name", is("John")); // Adjust if your XML structure is different
    }
}
