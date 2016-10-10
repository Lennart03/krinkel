package com.realdolmen.chiro.mspservice;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspdto.OrderRequestDto;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class MultiSafePayService {
    private static final String URL = "https://testapi.multisafepay.com/v1/json/orders?api_key=a9026c8f9a1d49da542dd2f51d702a4442612e54";
    private RestTemplate restTemplate = new RestTemplate();


    public MultiSafePayService() {
    }

    public OrderDto createPayment() {
        OrderRequestDto requestDto = new OrderRequestDto();
        JSONObject paymentOptions = new JSONObject();

        paymentOptions.put("notification_url", requestDto.getPayment_options().getNotification_url());
        paymentOptions.put("redirect_url", requestDto.getPayment_options().getRedirect_url());
        paymentOptions.put("cancel_url", requestDto.getPayment_options().getCancel_url());

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", requestDto.getType());
        jsonObject.put("order_id", requestDto.getOrder_id());
        jsonObject.put("description", requestDto.getDescription());
        jsonObject.put("currency", "EUR");
        jsonObject.put("amount", requestDto.getAmount());
        jsonObject.put("payment_options", paymentOptions);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

        ResponseEntity<OrderDto> response= restTemplate.exchange(URL, HttpMethod.POST, entity, OrderDto.class);

        OrderDto body = response.getBody();
        System.out.println(response.getStatusCode());
        System.out.println(body.toString());
        System.out.println(body.getData().toString());
        return body;
    }
}
