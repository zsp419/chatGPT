package com.bht.chatgpt;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chatGPT")
public class ChatGPTController {
    @Value("${token}")
    private String token;
    @RequestMapping("getAnswer")
    public Map<String, String> getAnswer(String question){
        return getResultMap(question);
    }

    @PostMapping("getResult")
    public Map<String, String> getResult(@RequestBody JSONObject jsonObject){
        String question = jsonObject.getStr("question");
        return getResultMap(question);
    }

    private Map<String, String> getResultMap(String question) {
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json;charset=UTF-8");
        JSONObject json = new JSONObject();
        //选择模型
        json.set("model","text-davinci-003");
        //添加我们需要输入的内容
        json.set("prompt", question);
        json.set("temperature",0.9);
        json.set("max_tokens",200);
        json.set("top_p",1);
        json.set("frequency_penalty",0.0);
        json.set("presence_penalty",0.6);

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
        JSONObject entries1 = JSONUtil.parseObj(o);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("answer", entries1.get("text").toString());
        return resultMap;
    }
}
