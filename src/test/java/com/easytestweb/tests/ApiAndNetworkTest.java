package com.easytestweb.tests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EasyTestWeb - API Testing Suite with Playwright")
public class ApiAndNetworkTest {

    private static Playwright playwright;
    private static APIRequestContext requestContext;

    @BeforeAll
    public static void setUpApi() {
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://jsonplaceholder.typicode.com"));
    }

    @AfterAll
    public static void tearDownApi() {
        if (requestContext != null) {
            requestContext.dispose();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    @DisplayName("Should fetch a post via GET request and return HTTP 200")
    public void testGetPost() {
        APIResponse response = requestContext.get("/posts/1");

        assertThat(response.status())
                .as("Status code should be 200 OK")
                .isEqualTo(200);

        assertThat(response.text())
                .as("Response body should contain userId and title")
                .contains("\"userId\": 1")
                .contains("\"title\":");
    }

    @Test
    @DisplayName("Should create a new resource via POST request")
    public void testCreatePost() {
        String payload = "{\"title\": \"easytestweb-playwright\", \"body\": \"automated test payload\", \"userId\": 10}";

        APIResponse response = requestContext.post("/posts",
                com.microsoft.playwright.options.RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(payload));

        assertThat(response.status())
                .as("Created resource should return HTTP 201")
                .isEqualTo(201);

        assertThat(response.text())
                .contains("easytestweb-playwright");
    }
}
