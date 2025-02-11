package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.TacoOrder;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    @GetMapping("/current")
    public String orderForm() {

        return "orderForm";
    }

    @PostMapping()
    public String processOrder(TacoOrder order, SessionStatus sessionStatus) {
        log.info("Order submitted {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
