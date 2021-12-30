package com.zhangws.send_dingmassage.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhangWs
 * @Date: 2021/12/30 11:10
 * @Classname: DingConfig
 * @Description: TODO
 */

@Data
@Configuration
public class DingConfig {

    @Value("${ding.url}")
    private String url;

    @Value("${ding.token}")
    private String token;

    @Value("${ding.secret}")
    private String secret;
}
