package org.hackthon.api.controller;

import lombok.RequiredArgsConstructor;
import org.hackthon.api.response.CreateResponse;
import org.hackthon.api.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OpenAIController {
    private final OpenAIService openAIService;
    @PostMapping(value = "/generate-text", produces = "application/json;charset=UTF-8")
    public ResponseEntity<CreateResponse> chatAi(@RequestBody CreateResponse createResponse){
        CreateResponse response = openAIService.callOpenAiService(createResponse);
        if (response.getResponse() == null) {
            response = new CreateResponse("please again");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/generate-text2", produces = "application/json;charset=UTF-8")
    public ResponseEntity<CreateResponse> chatAssistant(@RequestBody CreateResponse createResponse){
        CreateResponse response = openAIService.callAssistant(createResponse);
        if (response.getResponse() == null) {
            response = new CreateResponse("please again");
        }
        return ResponseEntity.ok(response);
    }
}