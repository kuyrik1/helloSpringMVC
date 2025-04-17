package kr.ac.hansung.cse.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.model.Offer;
import kr.ac.hansung.cse.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OfferController {
    // Controller -> service -> dao
    @Autowired
    private OfferService offerService;

    @GetMapping("/offers")
    public String showOffers(Model model) {
        List<Offer> offers = offerService.getAllOffers();
        model.addAttribute("id_offers", offers);
        return "offers";
    }

    @GetMapping("/createoffer")
    public String createOffer(Model model) {
        model.addAttribute("offer", new Offer());   // model에 빈 offer 객체를 넣어줌
        return "createoffer";
    }

    @PostMapping("/docreate")
    public String doCreate(Model model, @Valid Offer offer, BindingResult result) {  // model에 이미 offer 객체의 내용이 들어가있음. offer 객체의 내용의 띄어질거임.
        //System.out.println(offer);

        if(result.hasErrors()) {
            System.out.println("== Form data does not validated ==");

            List<ObjectError> errors = result.getAllErrors();

            for(ObjectError error:errors) {
                System.out.println(error.getDefaultMessage());
            }

            return "createoffer";
        }

        // controller -> service -> Dao -> DB저장
        offerService.insertOffer(offer);     // DB에 저장
        return "offercreated";
    }
}
