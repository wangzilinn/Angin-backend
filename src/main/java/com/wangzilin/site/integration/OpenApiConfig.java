package com.wangzilin.site.integration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 4:26 PM 5/16/2020
 * @Modified By:wangzilinn@gmail.com
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Angin's API")
                .contact(new Contact().name("wangzilin").email("wangzilinn@gmail.com").url("zilinn.wang"))
                .description("Angin的api们")
                .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                .version("1.0")
        );
    }
}
