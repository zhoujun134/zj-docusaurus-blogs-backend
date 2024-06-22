package com.zj.zs.domain.dto.openai;

/**
 * @ClassName NvidiaConstants
 * @Author zj
 * @Description
 * @Date 2024/4/27 11:31
 * @Version v1.0
 **/
public class NvidiaConstants {
    public static final String MODEL_NAME = "meta/llama3-70b-instruct";
    public static final String NVIDIA_MODEL_URL = "https://integrate.api.nvidia.com/v1/chat/completions";
    public static final double NVIDIA_TEMPERATURE = 0.5;
    public static final double NVIDIA_TOP_P = 0.7;
    public static final int MAX_TOKENS = 1024;
    public static final boolean STREAM = false;



}
