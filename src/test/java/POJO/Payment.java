package POJO;
/** {
 "cards": [
 {
 "id": "ccof:uIbfJXhXETSP197M3GB",
 "billing_address": {
 "address_line_1": "500 Electric Ave",
 "address_line_2": "Suite 600",
 "locality": "New York",
 "administrative_district_level_1": "NY",
 "postal_code": "10003",
 "country": "US"
 },
 "fingerprint": "ex-p-cs80EK9Flz7LsCMv-szbptQ_ssAGrhemzSTsPFgt9nzyE6t7okiLIQc-qw_quqKX4Q",
 "bin": "411111",
 "card_brand": "VISA",
 "card_type": "CREDIT",
 "cardholder_name": "Amelia Earhart",
 "customer_id": "VDKXEEKPJN48QDG3BGGFAK05P8",
 "enabled": true,
 "exp_month": 11,
 "exp_year": 2022,
 "last_4": "1111",
 "merchant_id": "6SSW7HV8K2ST5",
 "prepaid_type": "NOT_PREPAID",
 "reference_id": "user-id-1",
 "version": 1
 }
 ]
 }
 */

public class Payment {

    String user_id;
    int amount;
    String currency;
    int card_number;
    int exp_month;
    int exp_year;
    int cvc;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getCard_number() {
        return card_number;
    }

    public void setCard_number(int card_number) {
        this.card_number = card_number;
    }

    public int getExp_month() {
        return exp_month;
    }

    public void setExp_month(int exp_month) {
        this.exp_month = exp_month;
    }

    public int getExp_year() {
        return exp_year;
    }

    public void setExp_year(int exp_year) {
        this.exp_year = exp_year;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "user_id='" + user_id + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", card_number=" + card_number +
                ", exp_month=" + exp_month +
                ", exp_year=" + exp_year +
                ", cvc=" + cvc +
                '}';
    }
}
