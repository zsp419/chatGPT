package com.bht.chatgpt.service;

import java.util.Map;

public interface ChatGPTService {
    Map<String, String> getResultMap(String question);
}
