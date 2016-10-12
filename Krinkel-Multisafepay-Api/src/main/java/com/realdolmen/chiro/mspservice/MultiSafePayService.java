package com.realdolmen.chiro.mspservice;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.mspdto.Data;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspdto.StatusDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Service
public class MultiSafePayService {
    private static final String URL = "https://testapi.multisafepay.com/v1/json/orders";
    private static final String API_KEY = "a9026c8f9a1d49da542dd2f51d702a4442612e54";
    private RestTemplate restTemplate = new RestTemplate();

    private Logger logger = LoggerFactory.getLogger(MultiSafePayService.class);

    /**
     * Initiates a payment at Multisafepay.
     *
     * @param participant the order id that is given to Multisafepay. This is used for later callbacks. For now, we consider
     *                    the ad-number of the person that is being paid for as order id.
     * @param amount      specifies the amount that has to be paid in eurocents
     * @return returns the JSON response in object form (an OrderDto object)
     */
    public OrderDto createPayment(RegistrationParticipant participant, Integer amount) throws InvalidPaymentOrderIdException {
        if (!createPaymentParamsAreValid(participant.getAdNumber(), amount))
            throw new InvalidParameterException("cannot create a payment with those params");
        JSONObject jsonObject = this.createPaymentJsonObject(participant, amount);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        String url = URL + "?api_key=" + API_KEY;


        try {
            ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, OrderDto.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            // Assume failure to be due  to a duplicate OrderID.
            logger.warn("Request to Payment Site failed with status " + ex.getStatusCode().value());

            throw new InvalidPaymentOrderIdException();
        }
    }

    private boolean createPaymentParamsAreValid(String orderId, Integer amount) {
        boolean res = true;

        if (orderId == null)
            res = false;

        if (amount == null || amount < 0)
            res = false;

        return res;
    }

    public static String getCurrentTimeStamp() {
        Long test = new Date().getTime();
        return  "-" + test;
    }

    /**
     * Creates the JSON object that will be used in the request to initiate a multisafepay payment.
     * <p>
     * {
     * "type": "redirect",
     * "order_id": "A100",
     * "description": "teraf",
     * "currency": "EUR",
     * "amount": 1000,
     * "payment_options": {
     * "notification_url": null,
     * "redirect_url": null,
     * "cancel_url": null
     * },
     * "customer": {
     * "locale": "nl_BE",
     * "first_name": null,
     * "last_name": null,
     * "address1": null,
     * "house_number": null,
     * "zip_code": null,
     * "city": null,
     * "country": "BE"
     * "phone": null,
     * "email": null,
     * "disable_send_email": false
     * }
     * }
     */
    private JSONObject createPaymentJsonObject(RegistrationParticipant participant, Integer amount) {
        JSONObject paymentOptions = new JSONObject();
        JSONObject customer = new JSONObject();

        //TODO: update this with correct urls (use profiles for dev, test & production)
        paymentOptions.put("notification_url", "https://krinkel.be/notify");
        paymentOptions.put("redirect_url", "http://localhost:8080/payment/success");
        paymentOptions.put("cancel_url", "http://localhost:8080/payment/failure");

        customer.put("locale", "nl_BE");
        customer.put("first_name", participant.getFirstName());
        customer.put("last_name", participant.getLastName());

        if (participant.getAddress() != null) {
            customer.put("address1", participant.getAddress().getStreet());
            customer.put("house_number", participant.getAddress().getHouseNumber());
            customer.put("city", participant.getAddress().getCity());
            customer.put("zip_code", participant.getAddress().getPostalCode());
        }
        customer.put("country", "BE");

        customer.put("phone", participant.getPhoneNumber());
        customer.put("email", participant.getEmail());


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("type", "redirect");
        jsonObject.put("order_id", participant.getAdNumber() + getCurrentTimeStamp());
        jsonObject.put("description", "Payment for Krinkel ");
        jsonObject.put("currency", "EUR");
        jsonObject.put("amount", amount);
        jsonObject.put("payment_options", paymentOptions);
        jsonObject.put("customer", customer);

        return jsonObject;
    }


    /**
     * Returns the url that holds the payment page for the registration of a attendant of the camp.
     *
     * @param p      the participant or volunteer
     * @param amount the amount the participant or volunteer has to pay
     * @return the url at which the payment page is located
     */
    public String getParticipantPaymentUri(RegistrationParticipant p, Integer amount) throws InvalidPaymentOrderIdException {
        return this.createPayment(p, amount).getData().getPayment_url();

    }

    public String getVolunteerPaymentUri(RegistrationVolunteer v, Integer amount) throws InvalidPaymentOrderIdException {
        return this.createPayment(v, amount).getData().getPayment_url();
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


    public static class InvalidPaymentOrderIdException extends Exception {
    }
}
