/**
 * Service for train tickets and drink/meal tickets.
 */
export class TicketService {
    constructor($http, BASEURL, $window, $filter, authService) {
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;
        this.$filter = $filter;
        this.authService = authService;
    }

    /**
     * Submits a new purchase with the given elements.
     * @param type TicketType
     * @param amount int
     * @return Future chain that receives the post result parsed as a JSON object.
     */
    submitPurchase(ticketdto) {
        return this.$http.post(`${this.BASEURL}/api/tickets/purchase`, ticketdto).then((resp) => {
            return resp.data;
        });
    }

    /**
     * Gets user address, if one is not found by the backend, it must be provided by the user before submitting purchase.
     */
    getUserAddress() {
        return this.$http.get(`${this.BASEURL}/api/tickets/address`).then((resp) => {
            console.log(resp.data);
            return {
                status: resp.status,
                person: {
                    address: resp.data.address,
                    firstName: resp.data.firstName,
                    lastName: resp.data.lastName,
                    email: resp.data.email,
                    phoneNumber: resp.data.phoneNumber
                }
            };
        });
    }

    getTrainTicketPrices() {
        return this.$http.get(`${this.BASEURL}/api/tickets/prices/train`).then((resp) => {
            console.log(resp);
            return resp;
        })
    }

    getCouponPrices() {
        return this.$http.get(`${this.BASEURL}/api/tickets/prices/coupons`).then((resp) => {
            console.log(resp);
            return resp;
        })
    }


}

TicketService.$inject = ['$http', 'BASEURL', '$window', '$filter', 'AuthService'];
