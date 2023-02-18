package com.bht.chatgpt.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChatGPTCommonService {
    @Value("${token}")
    private String token;
    public JSONObject getOpenAiResult(Map<String, String> headers, JSONObject json) {
        HttpResponse response = HttpRequest.post("https://api.openai.com/v1/completions")
                .headerMap(headers, false)
                .bearerAuth(token)
                .body(String.valueOf(json))
                .timeout(5 * 60 * 1000)
                .execute();
        String body = response.body();
        JSONObject entries = JSONUtil.parseObj(body);
        Object choices = entries.get("choices");
        Object o = JSONUtil.parseArray(choices).get(0);
        return JSONUtil.parseObj(o);
    }
}
