package org.hackthon.api.config;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OpenAIConfig {

    @Value("${openai.api-key}")
    private String key;
    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.withApiKey(key);
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.withMaxMessages(2);
    }

    @Bean
    public ConversationalChain conversationalChain(ChatLanguageModel model, ChatMemory chatMemory) {
        return ConversationalChain.builder()
                .chatLanguageModel(model)
                .chatMemory(chatMemory)
                .build();
    }
}
