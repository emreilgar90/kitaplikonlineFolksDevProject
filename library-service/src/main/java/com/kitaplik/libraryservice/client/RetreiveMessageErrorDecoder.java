package com.kitaplik.libraryservice.client;

import com.kitaplik.libraryservice.exception.BookNotFoundException;
import com.kitaplik.libraryservice.exception.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;


import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default(); /**aşağı da kullandım*/

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        try(InputStream body =response.body().asInputStream()) {
            message = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(), //karşıdan aldığımız hata durumunu aldık
                    HttpStatus.resolve(response.status()).getReasonPhrase(),
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    response.request().url());

        }catch (IOException exception){
            return new Exception(exception.getMessage());
        }
        switch (response.status())  {
            case 404:
                throw new BookNotFoundException(message);
            default:
                return errorDecoder.decode(methodKey,response);   /**bunun dışında bir durum gerçekleşiyorsa default olarak
         errorDecoder dan hata fırlat, bunu fırlatması için private final ErrorDecoder errorDecoder = new Default ; dedik*/
          /**handle etmek istediğimiz case'i  (yani çözmek istediğimiz durumun) dışında ki herşeyi default'a bıraktık.*/
        }
    }
}
