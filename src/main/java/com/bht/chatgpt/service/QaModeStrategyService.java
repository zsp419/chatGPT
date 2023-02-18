package com.bht.chatgpt.service;

import cn.hutool.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("qa")
@RequiredArgsConstructor
public class QaModeStrategyService implements ChatGPTStrategyService {

    private final ChatGPTCommonService chatGPTCommonService;
    public Map<String, String> getResultMap(String question) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        json.set("model","text-curie-001");
        json.set("prompt", question);
        json.set("temperature",0);
        json.set("max_tokens",100);
        json.set("top_p",1);
        json.set("frequency_penalty",0.0);
        json.set("presence_penalty",0.0);
        json.set("stop","stop");
        JSONObject openAiResult = chatGPTCommonService.getOpenAiResult(headers, json);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("answer", openAiResult.get("text").toString());
        return resultMap;
    }
}
