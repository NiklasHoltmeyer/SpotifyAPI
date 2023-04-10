package de.holtmeyer.niklas.spotify.data.entity.io.response;

import kong.unirest.HttpResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data @NoArgsConstructor(access=AccessLevel.PACKAGE)
public class Response<T> {
    Optional<T> body;
    Boolean success;
    HttpResponse<T> httpResponse;

    public Response(HttpResponse<T> httpResponse) {
        this.body = Optional.ofNullable(httpResponse.getBody());
        this.httpResponse = httpResponse;
    }

    public Response(Response response, T body) {
        this.body = Optional.ofNullable(body);
        this.httpResponse = httpResponse;
    }

    public Response(T body, boolean success) {
        this.body = Optional.ofNullable(body);
        this.success = success;
    }

    public boolean isSuccess(){
        if(this.success!=null) return this.success;
        return httpResponse.getStatus() >= 200 && httpResponse.getStatus() < 300;
    }

    public void setSuccess(int statusCode){
        this.success = statusCode >= 200 && statusCode < 300;
    }
}
