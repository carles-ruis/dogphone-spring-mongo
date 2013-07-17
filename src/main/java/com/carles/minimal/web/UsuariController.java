package com.carles.minimal.web;


import java.util.Locale;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.carles.minimal.backend.UsuariRepo;
import com.carles.minimal.base.BaseController;
import com.carles.minimal.domain.Usuari;

@Controller
@RequestMapping("/usuari")
public class UsuariController extends BaseController {
	
@Autowired UsuariRepo repo;

/*- ***************************************************************************** */
/*- ***** REQUEST MAPPING ***** */
/*- ***************************************************************************** */
@RequestMapping(method = RequestMethod.GET, value = "/logout")
public void logout(HttpSession sessio, HttpServletResponse response) {
	sessio.removeAttribute("usuari");
}

@RequestMapping(method = RequestMethod.POST, value = "/login")
public synchronized @ResponseBody String login(@RequestBody Usuari form, HttpSession sessio, Locale locale) {
	String ret = "";
	String nom = form.getNom();
	String password = form.getPassword();
	Usuari usuari = repo.findOne("nom", nom);
	
	if ( usuari==null || usuari.getPassword().equals(password)==false ) {
		ret = getMessage("login.error", locale);
	} else {
		sessio.setAttribute("usuari", usuari);
	}
	return ret;
}

@RequestMapping(method = RequestMethod.POST, value = "/registre")
public synchronized @ResponseBody String registre(@RequestBody Usuari usuari, HttpSession sessio, Locale locale) {
	String ret = "";
	String nom = usuari.getNom();
	String password = usuari.getPassword();
	String confirmacio = usuari.getConfirmacio();
	
	if ( repo.findOne("nom",nom)!=null ) {
		ret = getMessage("registre.error.existent",locale);
	} else if (password.equals(confirmacio)==false) {
		ret = getMessage("registre.error.password",locale);		
	} else {
		repo.save(usuari);
		sessio.setAttribute("usuari", usuari);
		ret = getMessage("registre.ok",locale,nom);
	}
	return ret;
}

/*- ***************************************************************************** */
/*- ***** PRIVATE ***** */
/*- ***************************************************************************** */		

}
