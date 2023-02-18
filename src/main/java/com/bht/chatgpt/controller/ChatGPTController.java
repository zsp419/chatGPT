package com.bht.chatgpt.controller;

import cn.hutool.json.JSONObject;
import com.bht.chatgpt.enums.ModeEnum;
import com.bht.chatgpt.service.ChatGPTStrategyService;
import com.bht.chatgpt.service.ChatModeStrategyService;
import com.bht.chatgpt.service.QaModeStrategyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/chatGPT")
public class ChatGPTController {

    private final Map<String, ChatGPTStrategyService> chatGPTServiceMap;

    @PostMapping("getResult")
    public Map<String, String> getResult(HttpServletRequest httpRequest, @RequestBody JSONObject jsonObject){
        log.info("=============接口调用成功!");
        // 判断user-agent，防止恶意调用
        String userAgent = httpRequest.getHeader( "user-agent" ).toLowerCase();
        if (!userAgent.contains("micromessenger")){
            return null;
        }
        String question = jsonObject.getStr("question");
        String mode = jsonObject.getStr("mode");
        log.info("=============参数：question:"+question +";mode:"+mode);
        mode = Objects.isNull(mode)? ModeEnum.qa.getKey():mode;
        Map<String, String> resultMap = chatGPTServiceMap.get(mode).getResultMap(question);
        log.info("=============返回值："+resultMap);
        return resultMap;
    }
}
