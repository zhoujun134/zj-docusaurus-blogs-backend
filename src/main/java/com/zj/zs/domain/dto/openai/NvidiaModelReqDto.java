package com.zj.zs.domain.dto.openai;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

import static com.zj.zs.domain.dto.openai.NvidiaConstants.*;

/**
 * @ClassName NvidiaModelReqDto
 * @Author zj
 * @Description
 * @Date 2024/4/27 11:27
 * @Version v1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NvidiaModelReqDto {
    private String model = MODEL_NAME;
    private List<NvidiaModelMessageDto> messages;
    private double temperature = NVIDIA_TEMPERATURE;
    private double top_p = NVIDIA_TOP_P;
    private int max_tokens = MAX_TOKENS;
    private boolean stream = STREAM;

    public NvidiaModelReqDto(String content) {
        List<NvidiaModelMessageDto> messages = Lists.newArrayList();
        messages.add(new NvidiaModelMessageDto().setContent(content + ", 请使用中文回答我的问题!"));
        this.messages = messages;
    }
}
