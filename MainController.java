SDFvgDSvSDfcSDfcSvspackage ru.javastudy.springmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javastudy.springmvc.objects.User;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class MainController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/*", method = RequestMethod.GET)
    public String main(@ModelAttribute User user, Locale locale) {
        System.out.println(locale.getDisplayLanguage());
        System.out.println(messageSource.getMessage("locale", new String[]{locale.getDisplayName(locale)}, locale));
        user.setName("Nikolay"); //заполняет поле имя "name" в index.jsp
        return "index";
    }

    @RequestMapping(value = "/check-user*", method = RequestMethod.POST)
    public ModelAndView checkUser(Locale locale, @Valid @ModelAttribute("user") User user, BindingResult bindingResult) {// !!!BindingResult должен быть следующим после проверяемого значения!!

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locale", messageSource.getMessage("locale", new String[]{locale.getDisplayName(locale)}, locale));

        //если не проходит валидацию @Valid, то возврататься ошибки.
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("index");
        } else {
            modelAndView.setViewName("main");
        }

        return modelAndView;
    }



}
