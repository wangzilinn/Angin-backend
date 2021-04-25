package com.wangzilin.site.requset;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Author: wangzilinn@gmail.com
 * @Description:
 * @Date: Created in 12:37 PM 07/01/2020
 * @Modified By:wangzilinn@gmail.com
 */
@FeignClient(name = "GithubInfoFeignClient", url = "https://github-contributions.vercel.app/api/v1")
public interface GithubInfoFeignClient {

    @GetMapping(value = "/{userName}")
    Map<String, ?> info(@PathVariable String userName);
}
