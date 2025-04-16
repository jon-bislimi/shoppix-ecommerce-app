package org.bisha.ecommercefinal.services.feignClients;

import org.bisha.ecommercefinal.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "QUICKBUYMS", url = "http://microservice:8090")
public interface QuickBuyFeignClient {

    @GetMapping("/api/credentials/get-user-by-otp")
    ResponseEntity<UserDto> getUserByOtp(@RequestParam Integer otp);
}
