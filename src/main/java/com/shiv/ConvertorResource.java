package com.shiv;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/convert")
public class ConvertorResource extends RouteBuilder {

    @Inject
    CamelContext camelContext;

    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Path("/xmltojson")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response convertXmlToJson(String xmlInput) {
        String jsonOutput = producerTemplate.requestBody("direct:xmlToJson", xmlInput, String.class);
        return Response.ok(jsonOutput).build();
    }

    @POST
    @Path("/jsontoxml")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    public Response convertJsonToXml(String jsonInput) {
        String xmlOutput = producerTemplate.requestBody("direct:jsonToXml", jsonInput, String.class);
        return Response.ok(xmlOutput).build();
    }

    @Override
    public void configure() {
        // Exception handling for XML to JSON conversion
        onException(Exception.class)
                .handled(true)
                .log("Error processing XML to JSON: ${exception.message}")
                .to("log:ERROR");

        // Exception handling for JSON to XML conversion
        from("direct:xmlToJson")
                .onException(Exception.class)
                .handled(true)
                .log("Error processing XML to JSON: ${exception.message}")
                .to("log:ERROR")
                .end()
                .unmarshal().jaxb("com.shiv")
                .marshal().json();

        from("direct:jsonToXml")
                .onException(Exception.class)
                .handled(true)
                .log("Error processing JSON to XML: ${exception.message}")
                .to("log:ERROR")
                .end()
                .unmarshal().json()
                .marshal().jaxb("com.shiv");
    }
}
