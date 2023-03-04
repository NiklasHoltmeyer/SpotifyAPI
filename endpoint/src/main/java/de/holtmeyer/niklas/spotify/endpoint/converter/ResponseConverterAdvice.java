package de.holtmeyer.niklas.spotify.endpoint.converter;

import de.holtmeyer.niklas.spotify.data.entity.io.response.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseConverterAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(final MethodParameter returnType, final Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(final Object body, final MethodParameter returnType, final MediaType selectedContentType,
                                  final Class<? extends HttpMessageConverter<?>> selectedConverterType, final ServerHttpRequest request,
                                  final ServerHttpResponse response) {
        if(body instanceof Response responseBody){
            return beforeBodyWrite(responseBody, returnType, selectedContentType, selectedConverterType, request, response);
        }
        return body;
    }

    public Object beforeBodyWrite(final Response body, final MethodParameter returnType, final MediaType selectedContentType,
                                  final Class<? extends HttpMessageConverter<?>> selectedConverterType, final ServerHttpRequest request,
                                  final ServerHttpResponse response) {
        var statusCode = body.getHttpResponse().getStatus();
        var errorMessage = body.getHttpResponse().getStatusText();
        response.setStatusCode(HttpStatusCode.valueOf(statusCode));

        if(body.isSuccess()){
            return body.getBody().get();
        }
        
        return ResponseEntity.badRequest().body(errorMessage).getBody();
    }
}
