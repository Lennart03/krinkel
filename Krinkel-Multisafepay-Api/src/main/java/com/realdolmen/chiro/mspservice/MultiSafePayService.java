package com.realdolmen.chiro.mspservice;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspdto.OrderRequestDto;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;


public class MultiSafePayService {
    private static final String URL = "https://testapi.multisafepay.com/v1/json/orders?api_key=a9026c8f9a1d49da542dd2f51d702a4442612e54";
    private RestTemplate restTemplate = new RestTemplate();


    public MultiSafePayService() {
    }

    public OrderDto createPayment() {
        new JSONObject();
        OrderRequestDto requestDto = new OrderRequestDto();
        System.out.println(requestDto);

        return order;
    }
}
