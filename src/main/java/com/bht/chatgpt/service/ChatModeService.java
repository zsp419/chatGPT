package com.bht.chatgpt.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bht.chatgpt.enums.ModeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("chat")
public class ChatModeService implements ChatGPTService{
    @Value("${token}")
    private String token;

    public Map<String, String> getResultMap(String question) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        json.set("prompt", question+"?|");
        json.set("temperature",0.9);
        json.set("max_tokens",2048);
        json.set("top_p",1);
        json.set("frequency_penalty",0);
        json.set("presence_penalty",0.6);
        List<String> list1 = new ArrayList<>();
        list1.add("Human:");
        list1.add("AI:");
        json.set("stop",list1);
        json.set("best_of", 1);
        json.set("echo", true);
        json.set("logprobs", 0);
        json.set("stream", false);

        HttpResponse response = HttpRequest.post("https://api.openai.com/v1/engines/text-curie-001-playground/completions")
                .headerMap(headers, false)
                .bearerAuth(token)
                .body(String.valueOf(json))
                .timeout(5 * 60 * 1000)
                .execute();
        String body = response.body();
        JSONObject entries = JSONUtil.parseObj(body);
        Object choices = entries.get("choices");
        Object o = JSONUtil.parseArray(choices).get(0);
        JSONObject entries1 = JSONUtil.parseObj(o);
        Map<String, String> resultMap = new HashMap<>();
        String[] texts = entries1.get("text").toString().split("\\|");
        resultMap.put("answer", CollectionUtils.lastElement(Arrays.asList(texts)));
        return resultMap;
    }
}
