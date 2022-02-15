package com.bugprovider.seed.http;

/**
 * @author BugProvider
 * @date 2022/2/15
 * @apiNote 请求第三方http示例
 * https://forest.dtflyx.com
 */

import com.dtflys.forest.annotation.*;

/**
 * @BaseRequest 为配置接口层级请求信息的注解
 * 其属性会成为该接口下所有请求的默认属性
 * 但可以被方法上定义的属性所覆盖
 */
@BaseRequest(
        baseURL = "${xUrl}",     // yml中forest.variables.xul 默认域名
        headers = {
                "Accept:text/plain"                // 默认请求头
        }
)
public interface MyClient {

    // 方法的URL不必再写域名部分
    @Get("/hello/user")
    String send1(@Query("username") String username);

    // 若方法的URL是完整包含http://开头的，那么会以方法的URL中域名为准，不会被接口层级中的baseURL属性覆盖
    @Get("http://www.xxx.com/hello/user")
    String send2(@Query("username") String username);


    @Get(
            url = "/hello/user",
            headers = {
                    "Accept:application/json"      // 覆盖接口层级配置的请求头信息
            }
    )
    String send3(@Query("username") String username);

    /**
     * 按键值对分别修饰不同的参数
     * 这时每个参数前的 @JSONBody 注解必须填上 value 属性或 name 属性的值，作为JSON的字段名称
     */
    @Post("http://localhost:8080/hello/user")
    String helloUser(@JSONBody("username") String username, @JSONBody("password") String password);

}