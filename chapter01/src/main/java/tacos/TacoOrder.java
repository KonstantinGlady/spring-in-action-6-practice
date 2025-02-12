package tacos;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Data
public class TacoOrder {

    @NotBlank(message = "Name required")
    private String deliveryName;

    @NotBlank(message = "Street required")
    private String deliveryStreet;

    @NotBlank(message = "City required")
    private String deliveryCity;

    @NotBlank(message = "State required")
    private String deliveryState;

    @NotBlank(message = "zip required")
    private String deliveryZip;

    @CreditCardNumber(message = "Not a valid credit card")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])(\\/)([2-9][0-9])$")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
