package com.realdolmen.chiro.mspservice;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.mspdto.Data;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspdto.StatusDto;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.util.HashMap;

@Service
public class MultiSafePayService {
    private static final String URL = "https://testapi.multisafepay.com/v1/json/orders";
    private static final String API_KEY = "a9026c8f9a1d49da542dd2f51d702a4442612e54";
    private RestTemplate restTemplate = new RestTemplate();


    /**
     * Initiates a payment at Multisafepay.
     *
     * @param orderId the order id that is given to Multisafepay. This is used for later callbacks. For now, we consider
     *                the ad-number of the person that is being paid for as order id.
     * @param amount  specifies the amount that has to be paid in eurocents
     * @return returns the JSON response in object form (an OrderDto object)
     */
    public OrderDto createPayment(String orderId, Integer amount) {
        if (!createPaymentParamsAreValid(orderId, amount))
            throw new InvalidParameterException("cannot create a payment with those params");
        JSONObject jsonObject = this.createPaymentJsonObject(orderId, amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        String url = URL + "?api_key=" + API_KEY;

        ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, OrderDto.class);

        return response.getBody();
    }

    private boolean createPaymentParamsAreValid(String orderId, Integer amount) {
        boolean res = true;

        if (orderId == null)
            res = false;

        if (amount == null || amount < 0)
            res = false;

        return res;
    }


    /**
     * Creates the JSON object that will be used in the request to initiate a multisafepay payment.
     */
    private JSONObject createPaymentJsonObject(String orderId, Integer amount) {
        JSONObject paymentOptions = new JSONObject();

        //TODO: update this with correct urls (use profiles for dev, test & production)
        paymentOptions.put("notification_url", "https://krinkel.be/notify");
        paymentOptions.put("redirect_url", "http://localhost:8080/payment/success");
        paymentOptions.put("cancel_url", "http://localhost:8080/payment/failure");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", "redirect");
        jsonObject.put("order_id", orderId);
        jsonObject.put("description", "this is the description");
        jsonObject.put("currency", "EUR");
        jsonObject.put("amount", amount);
        jsonObject.put("payment_options", paymentOptions);

        return jsonObject;
    }


    /**
     * Returns the url that holds the payment page for the registration of a attendant of the camp.
     *
     * @param p      the participant or volunteer
     * @param amount the amount the participant or volunteer has to pay
     * @return the url at which the payment page is located
     */
    public String getParticipantPaymentUri(RegistrationParticipant p, Integer amount) {
        return this.createPayment(p.getAdNumber(), amount).getData().getPayment_url();
    }

    public String getVolunteerPaymentUri(RegistrationVolunteer v, Integer amount) {
        return this.createPayment(v.getAdNumber(), amount).getData().getPayment_url();
    }

    public boolean orderIsPaid(String testOrderId) {
        boolean res = false;
        String url = URL + "/" + testOrderId + "?api_key=" + API_KEY;
        ResponseEntity<StatusDto> response = restTemplate.exchange(url, HttpMethod.GET, null, StatusDto.class);
        Data data = response.getBody().getData();
        if (data != null && data.getStatus().equals("completed"))
            res = true;


        return res;
    }
}
