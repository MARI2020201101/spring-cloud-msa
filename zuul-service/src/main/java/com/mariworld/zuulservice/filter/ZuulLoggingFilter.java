package com.mariworld.zuulservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.logging.Logger;

@Slf4j
@Component
public class ZuulLoggingFilter extends ZuulFilter {
//    Logger logger = (Logger) LoggerFactory.getLogger(ZuulLoggingFilter.class);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("********** printing logs *********");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
//        log.info("request info : {} , {} , {} , {}" , request.getPathInfo() , request.getContextPath(), request.getMethod(), request.getQueryString());
        log.info("********** request uri: " + request.getRequestURI());
        return null;
    }
}
