package com.realdolmen.chiro.mspservice;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.domain.dto.UserDTO;
import com.realdolmen.chiro.mspdto.Data;
import com.realdolmen.chiro.mspdto.OrderDto;
import com.realdolmen.chiro.mspdto.StatusDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.InvalidParameterException;
import java.util.Date;

@Service
public class MultiSafePayService {

    @Autowired
    private MultiSafePayConfiguration configuration;

    private RestTemplate restTemplate = new RestTemplate();

    private Logger logger = LoggerFactory.getLogger(MultiSafePayService.class);

    private static String getCurrentTimeStamp() {
        Long test = new Date().getTime();
        return "-" + test;
    }

    /**
     * Initiates a payment at Multisafepay.
     *
     * @param participant the order id that is given to Multisafepay. This is used for later callbacks. For now, we consider
     *                    the ad-number of the person that is being paid for as order id.
     * @param amount      specifies the amount that has to be paid in eurocents
     * @return returns the JSON response in object form (an OrderDto object)
     */
    public OrderDto createPayment(RegistrationParticipant participant, Integer amount, User currentUser) throws InvalidPaymentOrderIdException {
        if (!createPaymentParamsAreValid(participant.getAdNumber(), amount))
            throw new InvalidParameterException("cannot create a payment with those params");
        JSONObject jsonObject = this.createPaymentJsonObject(participant, /*amount todo:prod*/ 1, currentUser);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        String url = configuration.getURL() + "?api_key=" + configuration.getApiKey();


        try {
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, OrderDto.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            // Assume failure to be due  to a duplicate OrderID.
            logger.warn("Request to Payment Site failed with status " + ex.getMessage());
            throw new InvalidPaymentOrderIdException();
        }
    }

    /**
     * Creates order where all data is retrieved from the currentUser. Example scenario: A user registers a list of
     * other registration participants from their basket.
     *
     * @param amount specifies the amount that has to be paid in eurocents
     * @return returns the JSON response in object form (an OrderDto object)
     * @throws InvalidPaymentOrderIdException
     */
    public OrderDto createPayment(Integer amount, UserDTO currentUser) throws InvalidPaymentOrderIdException {
        if (!createPaymentParamsAreValid(currentUser.getAdNumber(), amount))
            throw new InvalidParameterException("cannot create a payment with those params");
        JSONObject jsonObject = this.createPaymentJsonObject(/*amount todo:prod*/ 1, currentUser);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        String url = configuration.getURL() + "?api_key=" + configuration.getApiKey();


        try {
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            ResponseEntity<OrderDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, OrderDto.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            // Assume failure to be due  to a duplicate OrderID.
            logger.warn("Request to Payment Site failed with status " + ex.getMessage());
            throw new InvalidPaymentOrderIdException();
        }
    }

    private boolean createPaymentParamsAreValid(String orderId, Integer amount) {
        boolean res = true;

        if (orderId == null) {
            logger.error("orderId payment = null");
            res = false;
        }

        if (amount == null || amount < 0) {
            logger.error("amount payment = null or negative");
            res = false;
        }
        return res;
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
    private JSONObject createPaymentJsonObject(RegistrationParticipant participant, Integer amount, User currentUser) {
        JSONObject paymentOptions = configuration.getPaymentOptions();
        JSONObject customer = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        customer.put("locale", "nl_BE");

        if (participant.getAddress() != null) {
            customer.put("address1", participant.getAddress().getStreet());
            customer.put("house_number", participant.getAddress().getHouseNumber());
            customer.put("city", participant.getAddress().getCity());
            customer.put("zip_code", participant.getAddress().getPostalCode());
        }
        customer.put("country", "BE");

        customer.put("phone", participant.getPhoneNumber());

        if (participant.getEmailSubscriber() != null && !participant.getEmailSubscriber().trim().isEmpty()) {
            customer.put("first_name", currentUser.getFirstname());
            customer.put("last_name", currentUser.getLastname());
            customer.put("email", participant.getEmailSubscriber());
            jsonObject.put("description", "Betaling voor Krinkel voor:" + participant.getFirstName() + " " + participant.getLastName());
        } else {
            customer.put("first_name", participant.getFirstName());
            customer.put("last_name", participant.getLastName());
            customer.put("email", participant.getEmail());
            jsonObject.put("description", "Betaling voor Krinkel");
        }


        jsonObject.put("type", "redirect");
        jsonObject.put("order_id", participant.getAdNumber() + getCurrentTimeStamp());
        jsonObject.put("currency", "EUR");
        jsonObject.put("amount", amount);
        jsonObject.put("payment_options", paymentOptions);
        jsonObject.put("customer", customer);

        return jsonObject;
    }

    private JSONObject createPaymentJsonObject(Integer amount, UserDTO currentUser) {
        JSONObject paymentOptions = configuration.getPaymentOptions();
        JSONObject customer = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        customer.put("locale", "nl_BE");

        customer.put("country", "BE");

        customer.put("first_name", currentUser.getFirstName());
        customer.put("last_name", currentUser.getLastName());
        customer.put("email", currentUser.getEmail());
        jsonObject.put("description", "Betaling voor Krinkel");


        jsonObject.put("type", "redirect");
        jsonObject.put("order_id", currentUser.getAdNumber() + getCurrentTimeStamp());
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
    public String getParticipantPaymentUri(RegistrationParticipant p, Integer amount, User currentUser) throws InvalidPaymentOrderIdException {
        return this.createPayment(p, amount, currentUser).getData().getPayment_url();

    }

    public String getBasketPaymentUri(Integer amount, UserDTO currentUser) throws InvalidPaymentOrderIdException {
        return this.createPayment(amount, currentUser).getData().getPayment_url();
    }

    public String getVolunteerPaymentUri(RegistrationVolunteer v, Integer amount, User currentUser) throws InvalidPaymentOrderIdException {
        return this.createPayment(v, amount, currentUser).getData().getPayment_url();
    }

    public boolean orderIsPaid(String testOrderId) {
        boolean res = false;
        String url = configuration.getURL() + "/" + testOrderId + "?api_key=" + configuration.getApiKey();
        ResponseEntity<StatusDto> response = restTemplate.exchange(url, HttpMethod.GET, null, StatusDto.class);
        Data data = response.getBody().getData();
        if (data != null && data.getStatus().equals("completed"))
            res = true;


        return res;
    }


    public static class InvalidPaymentOrderIdException extends Exception {
    }
}
