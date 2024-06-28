package org.hackthon.api.service;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import org.hackthon.api.response.CreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
    private final ChatLanguageModel chatLanguageModel;
    private final ChatMemory chatMemory;

    interface Assistant{
        @SystemMessage("너는 외노자를 위한 노무문제와 직장 내 발생할 수 있는 문제를 해결하는 챗봇이다. 해결책을 구체적으로 제시하며 도움이 되는 신고 번호를 알려준다. 그 외의 문제는 대답하지 않는다")
        String chat(String message);
    }

    private final ConversationalChain conversationalChain;

    @Autowired
    public OpenAIService(ConversationalChain conversationalChain, ChatLanguageModel chatLanguageModel, ChatMemory chatMemory) {
        this.conversationalChain = conversationalChain;
        this.chatLanguageModel = chatLanguageModel;
        this.chatMemory = chatMemory;
    }
    public CreateResponse callOpenAiService(CreateResponse createResponse){
        try {
            String answer = conversationalChain.execute(
                    "근무 중 겪는 성범죄나 임금체불, 협박, 부당한 처우에 대해 질문할 경우 구체적인 신고 번호와 해야할 행동을 제시한다.근무 태도나 해야할 행동을 물어보면 질문의 의도에 맞게 대답한다." + createResponse.getResponse());
            return new CreateResponse(answer);
        } catch (Exception e) {
            return new CreateResponse("Error");
        }
    }
    public CreateResponse callAssistant(CreateResponse createResponse){
        try {
            Assistant assistant = AiServices.builder(Assistant.class)
                    .chatLanguageModel(chatLanguageModel)
                    .chatMemory(chatMemory)
                    .build();
            String answer = assistant.chat(createResponse.getResponse());
            return new CreateResponse(answer);
        } catch (Exception e) {
            return new CreateResponse("Error");
        }
    }

}
