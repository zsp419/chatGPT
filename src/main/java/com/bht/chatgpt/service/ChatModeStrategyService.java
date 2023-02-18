package com.bht.chatgpt.service;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service("chat")
@RequiredArgsConstructor
public class ChatModeStrategyService implements ChatGPTStrategyService {

    private final ChatGPTCommonService chatGPTCommonService;
    public Map<String, String> getResultMap(String question) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        json.set("model", "text-davinci-003");
        json.set("prompt", question+"?|");
        json.set("temperature",0.5);
        json.set("max_tokens",2048);
        json.set("top_p",1.0);
        json.set("frequency_penalty",0.5);
        json.set("presence_penalty",0.0);
        json.set("stop",Collections.singleton("You:"));
        JSONObject openAiResult = chatGPTCommonService.getOpenAiResult(headers, json);
        Map<String, String> resultMap = new HashMap<>();
        String[] texts = openAiResult.get("text").toString().split("\\|");
        resultMap.put("answer", CollectionUtils.lastElement(Arrays.asList(texts)));
        return resultMap;
    }
}
