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
     * Function to POST a new ticket purchase.
     * @param ticketDTO DTO containing information about the payment. Has following values.
     * {
            type: Type of ticket wanted (VALUES: TRAIN of BON)
            ticketAmount: Amount of tickets wanted(1 for TRAIN and Number of coupons for BON)
            timesOrdered: The number of times the ticket is ordered,
            firstName: First name of person ordering tickets,
            lastName: Last name of person ordering tickets,
            email: Email address of person ordering tickets,
            phoneNumber: Phone number of person ordering tickets,
            address: {
                street: Street of person ordering tickets,
                houseNumber: House number of person ordering tickets,
                postalCode: Postal code of person ordering tickets,
                city: City of person ordering tickets
            }
        }
     */
    submitPurchase(ticketDTO) {
        return this.$http.post(`${this.BASEURL}/api/tickets/purchase`, ticketDTO).then((resp) => {
            return resp;
        });
    }

    /**
     * Gets user address and some additional information about the registered participant for Krinkel,
     * if one is not found by the backend, it must be provided by the user before submitting purchase.
     */
    getParticipantInfo() {
        return this.$http.get(`${this.BASEURL}/api/tickets/participantInfo`).then((resp) => {
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

    /**
     * Retrieves the tickets prices for train tickets from backend.
     */
    getTrainTicketPrices() {
        return this.$http.get(`${this.BASEURL}/api/tickets/prices/train`).then((resp) => {
            if(resp.status === 200) {
                return resp.data;
            } else {
                return null;
            }
        });
    }

    /**
     * Retrieves the tickets prices for food/drink coupons from backend.
     */
    getCouponPrices() {
        return this.$http.get(`${this.BASEURL}/api/tickets/prices/coupons`).then((resp) => {
            if(resp.status === 200) {
                return resp.data;
            } else {
                return null;
            }
        });
    }


}

TicketService.$inject = ['$http', 'BASEURL', '$window', '$filter', 'AuthService'];
