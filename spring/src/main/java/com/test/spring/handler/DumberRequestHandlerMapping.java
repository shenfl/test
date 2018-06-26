package com.test.spring.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Set;

public class DumberRequestHandlerMapping extends RequestMappingHandlerMapping {
    private static final Logger LOGGER = LoggerFactory.getLogger(DumberRequestHandlerMapping.class);

    /**
     * Checks if handler should be applied to given bean's class. The check is made through looking for DumberRequestMapping annotation.
     */
    @Override
    protected boolean isHandler(Class<?> beanType) {
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(beanType);
        for (Method method : methods) {
            if (AnnotationUtils.findAnnotation(method, DumberRequestMapping.class) != null) {
                LOGGER.debug("[DumberRequestHandlerMapping] Method "+method+" supports @DumberRequestMapping ");
                return true;
            }
        }
        return false;
    }

    /**
     * Make some operations directly before returning HttpServletRequest instance into mapped controller's method. For example, if you add here some attributes to this object, those attributes will be reachable from controller's method which handles the request.
     * RequestMappingInfoHandlerMapping does some of more complicated stuff here like exposing URI template variables or extracting
     * "matrix variable".
     * NOTE : "matrix variables" are name-value pairs within path segments, separated with a semicolon (;). For example in this URL
     * /clubs;country=France;division=Ligue 1, Ligue 2) we can find 2 matrix variables: country (France) and division (list composed by
     * Ligue 1 and Ligue 2)
     */
    @Override
    protected void handleMatch(RequestMappingInfo info, String lookupPath, HttpServletRequest request) {
        LOGGER.debug("[DumberRequestHandlerMapping] handleMatch info "+info+  ", lookupPath ="+ lookupPath + ", request ="+request);
        request.setAttribute("isDumber", true);
        request.setAttribute("handledTime", System.nanoTime());
    }

    /**
     * Method invoked when given lookupPath doesn't match with this handler mapping.
     * Native RequestMappingInfoHandlerMapping uses this method to launch two exceptions :
     * - HttpRequestMethodNotSupportedException - if some URLs match, but no theirs HTTP methods.
     * - HttpMediaTypeNotAcceptableException - if some URLs match, but no theirs content types. For example, a handler can match an URL
     * like /my-page/test, but can expect that the request should be send as application/json. Or, the handler can match the URL but
     * returns an inappropriate response type, for example: text/html instead of application/json.
     */
    @Override
    protected HandlerMethod handleNoMatch(Set<RequestMappingInfo> requestMappingInfos, String lookupPath, HttpServletRequest request) throws ServletException {
        LOGGER.debug("[DumberRequestHandlerMapping] handleNoMatch info "+requestMappingInfos+  ", lookupPath ="+ lookupPath + ", request ="+request);
        return null;
    }

    /**
     * Here we constructs RequestMappingInfo instance for given method.
     * RequestMappingInfo - this object is used to encapsulate mapping conditions. For example, it contains an instance of
     * PatternsRequestCondition which  is used in native Spring's RequestMappingInfoHandlerMapping  handleMatch() method to put URI
     * variables into @RequestMapping pattern.
     * Ie, it will take the following URL /test/1 and match it for URI template /test/{id}. In occurrence, it will found that 1
     * corresponding to @PathVariable represented  by id variable ({id}) and will set its value to 1.
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        LOGGER.debug("[DumberRequestHandlerMapping] getMappingForMethod method "+method+  ", handlerType ="+handlerType);
        RequestMappingInfo info = null;
        // look for @DumberRequestMapping annotation for the Method method from signature
        DumberRequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, DumberRequestMapping.class);
        if (methodAnnotation != null) {
            RequestCondition<?> methodCondition = getCustomMethodCondition(method);
            info = createRequestMappingInfo(methodAnnotation, methodCondition);
        }
        LOGGER.debug("[DumberRequestHandlerMapping] getMappingForMethod method; returns info mapping "+info);
        return info;
    }

    /**
     * Creates RequestMappingInfo object which encapsulates:
     * - PatternsRequestCondition: represents URI template to resolve. Resolving is helped by UrlPathHelper utility class from
     * package org.springframework.web.util.
     * - RequestMethodsRequestCondition: methods accepted by this handler. You can make a test and replace RequestMethod.GET by
     * RequestMethod.POST. You will able to observe that our test won't work.
     * - ParamsRequestCondition:
     * - HeadersRequestCondition: headers which should be send in request to given handler should handle this request. You can,
     * for exemple, put there an header value like "my-header:test" and observe the program behavior.
     * - ConsumesRequestCondition: this condition allows to specify the content-type of request. We can use it for, for example,
     * specify that a method can be handled only for application/json request.
     * - ProducesRequestCondition: this condition allows to specify the content-type of response. We can use it for, for example,
     * specify that a method can be applied only for text/plain response.
     */
    protected RequestMappingInfo createRequestMappingInfo(DumberRequestMapping annotation, RequestCondition<?> customCondition) {
        return new RequestMappingInfo(
                new PatternsRequestCondition(new String[] {annotation.value()}),
                new RequestMethodsRequestCondition(new RequestMethod[]{RequestMethod.GET}),
                new ParamsRequestCondition(new String[]{}),
                new HeadersRequestCondition(new String[] {}),
                new ConsumesRequestCondition(new String[]{}, new String[]{}),
                new ProducesRequestCondition(new String[]{}, new String[]{}, getContentNegotiationManager()),
                customCondition);
    }

}