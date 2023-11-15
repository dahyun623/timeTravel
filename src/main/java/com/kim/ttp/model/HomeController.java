package com.kim.ttp.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
    @GetMapping("/main.do")
    public String main() {
        return "main";
    }
	
    @PostMapping("/timetravel.do")
    public String timetravel(@RequestParam("month") int month, @RequestParam("day") int day, Model model) throws IOException {
        String url = String.format("https://api.wikimedia.org/feed/v1/wikipedia/en/onthisday/all/%02d/%02d", month, day);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5MGY5NDE0MmY2OWQ4YjEzNTBmNTdkYWY0NTNmNGZlMiIsImp0aSI6ImI1OTYyM2IyNDQ5ZmRjZTRkN2E0NDQzMDdiNjc0NDYzYmM5YjI5MmNjMzU4NDhmMjJkYzEwMWI5NjFlMTUzOGY3NTY5NDMzNTBhODcxMmUzIiwiaWF0IjoxNjk5MTg3MzUyLjU3MjU4NSwibmJmIjoxNjk5MTg3MzUyLjU3MjU4OSwiZXhwIjozMzI1NjA5NjE1Mi41NzEwMywic3ViIjoiNzQxNzk1NzIiLCJpc3MiOiJodHRwczovL21ldGEud2lraW1lZGlhLm9yZyIsInJhdGVsaW1pdCI6eyJyZXF1ZXN0c19wZXJfdW5pdCI6NTAwMCwidW5pdCI6IkhPVVIifSwic2NvcGVzIjpbImJhc2ljIl19.kHV73WnxbYsaRebe1F4-nvc4BPeOMA8DwKLFLcCpA_S0MxaZcxXFRx_yxVaZ2VG6NGhB4Jb3DeYTbsIW29ak6RPJilVd_PZpiXK4qR9i2AO8TOZ6RB5fvio4DC7F02gloMTBRP30SViklAN8E6biQgwMij9-8HyULYeXEamlEJlM-Gie1YjxOqi3dHd41VDS9kycxN7YryoAdV4N0vzvIfv728p00IGeVCAO5iSss7V105WpFndEUnISqC3FUXv8xGnthuhmbSlrgaAhyrJyB_9052un5AIhbFLNdoSZ72Lrc848F-6PrTxNe2RSZS38jwIVqSKnjTE-5LXlYM0CrqB6g5b8OPI8cwRUYLYjb6FwCsbv58oT3hiqOXFsh1DWuWG4ut4Vm9FqeVNl0JXyL28_sGmbcpF73ufBp1aDl4g8Nt6bc7GFPGTVLl43daJrdsJWpv70mcy6d4A_MtV4AcmEGfvwt6JD3DXMNM1dzKHGRyJD1TDm75dUQN8OMjvniKHg3J6KFrbqt1x7aNp_LwmVrJuYJpdP8EFWfQcE97M11u1LibFSvlccsFmFMLQmhseGAKbDnSztMg_RQsrG3ei6Beb_ywhreuafXX4Txsd03Z7juurXhytnZm-02_twDPj9GJTVxGCgH-so9nV41Kk_ovgymmFGa-RqQ_mKU9I")
            .header("Api-User-Agent", "whathappened (dkmellow@naver.com)")
            .build();

        String response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body).join();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseJson = mapper.readTree(response);

        List<Map<String, Object>> events = new ArrayList<>();

        if (responseJson.hasNonNull("selected")) {
            for (JsonNode eventNode : responseJson.get("selected")) {
                String eventText = eventNode.get("text").asText();
                if (eventNode.hasNonNull("pages") && eventNode.get("pages").size() > 0) {
                    JsonNode pageNode = eventNode.get("pages").get(0);
                    if (pageNode.hasNonNull("title") && pageNode.hasNonNull("description") && pageNode.hasNonNull("content_urls")) {
                        String pageTitle = pageNode.get("title").asText();
                        String pageDescription = pageNode.get("description").asText();
                        JsonNode contentUrlsNode = pageNode.get("content_urls");
                        if (contentUrlsNode.hasNonNull("desktop")) {
                            JsonNode desktopNode = contentUrlsNode.get("desktop");
                            if (desktopNode.hasNonNull("page")) {
                                String pageUrl = desktopNode.get("page").asText();
                                Map<String, Object> event = new HashMap<>();
                                event.put("text", eventText);
                                event.put("pageTitle", pageTitle);
                                event.put("pageDescription", pageDescription);
                                event.put("pageUrl", pageUrl);
                                events.add(event);
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("events", events);
        return "detail";
    }
}

