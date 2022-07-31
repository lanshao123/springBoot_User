package com.wh.filter;

import com.alibaba.nacos.client.config.utils.MD5;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wh.entity.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class LoginFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器的业务逻辑
     * @param exchange：请求和响应的上下文,存储了Request和Response对象
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String whToken=request.getHeaders().getFirst("whToken");
        String whName=request.getHeaders().getFirst("whName");
        // if (token == null) {
        //     exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //     return exchange.getResponse().setComplete();//结束请求
        // }
        //返回json数据
        if (request.getPath().toString().contains("/user/login")) {
            return chain.filter(exchange);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        DataBufferFactory bufferFactory  = response.bufferFactory();
        if(!StringUtils.isEmpty(whToken) &&!StringUtils.isEmpty(whName)&&!whName.equals("null")&&!whToken.equals("null")){
            //如果有token就对token进行校验
            MD5 instance = MD5.getInstance();
            String md5String = instance.getMD5String(whName);
            if(md5String.equals(whToken)){//对token进行校验
                //校验成功放行
                return chain.filter(exchange);//放行
            }else{
                //校验失败
                response.setStatusCode(HttpStatus.OK);
                try {
                    DataBuffer wrap = bufferFactory.wrap(objectMapper.writeValueAsBytes(new Result(0, "token校验失败")));
                    return response.writeWith(Mono.fromSupplier(() -> wrap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response.setComplete();
            }
        }else{
            //校验失败
            response.setStatusCode(HttpStatus.OK);
            try {
                DataBuffer wrap = bufferFactory.wrap(objectMapper.writeValueAsBytes(new Result(0, "用户未登录")));
                return response.writeWith(Mono.fromSupplier(() -> wrap));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response.setComplete();
        }

    }

    /**
     * 过滤器的执行顺序：通过整数表示顺序，数值越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}