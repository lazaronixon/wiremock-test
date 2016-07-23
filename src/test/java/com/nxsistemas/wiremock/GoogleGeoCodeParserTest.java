/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nxsistemas.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.File;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GoogleGeoCodeParserTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(GoogleGeoCodeParser.class)
                .addAsLibraries(getMavenDependence("com.github.tomakehurst:wiremock"));
    }
    
    public static File[] getMavenDependence(String description) {
        return Maven.resolver().loadPomFromFile("pom.xml").resolve(description).withTransitivity().asFile();
    }    

    @Test
    public void testGetLatLng() throws Exception {
        stubFor(get(urlPathEqualTo("/maps"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"results\" : [{ \"geometry\":{ \"location\":{ \"lat\":0, \"lng\":0 } } }] }")));

        double[] result = GoogleGeoCodeParser.getLatLng("Abadiania", "GO");
        assertArrayEquals(new double[]{0, 0}, result, 0);
    }
    
    

}
