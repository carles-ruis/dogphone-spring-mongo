package com.carles.minimal.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.carles.minimal.backend.BotigaRepo;
import com.carles.minimal.backend.TerminalRepo;
import com.carles.minimal.base.BaseController;
import com.carles.minimal.domain.Botiga;
import com.carles.minimal.domain.Terminal;

@Controller
@RequestMapping("/resources")
public class ResourcesController extends BaseController {

@Autowired private BotigaRepo botigaRepo;
@Autowired private TerminalRepo terminalRepo;

private List<Botiga> botigues = new ArrayList<Botiga>();
private List<Terminal> terminals = new ArrayList<Terminal>();
private static final int TERMINALS_PER_PAGE = 4;
private int totalPages = 0;

/*- ***************************************************************************** */
/*- ***** INICIALITZACIONS ***** */
/*- ***************************************************************************** */
@PostConstruct
public void loadBotigues() {
	botigues = botigaRepo.find().asList();
	this.ordenarBotiguesPerCiutat(botigues);
	context.setAttribute("botigues", botigues);
}

@PostConstruct
public void loadTerminals() {
	terminals = terminalRepo.find().asList();
	totalPages = (int)Math.ceil( terminals.size() / TERMINALS_PER_PAGE );
	context.setAttribute("totalPages", totalPages);
	context.setAttribute("terminalsPerPage", TERMINALS_PER_PAGE);
	context.setAttribute("terminals", terminals);
}

/*- ***************************************************************************** */
/*- ***** REQUEST MAPPING ***** */
/*- ***************************************************************************** */
@RequestMapping(method = RequestMethod.GET, value="/botigues")
public @ResponseBody List<Botiga> getBotigues(HttpSession sessio){
	return (List<Botiga>) context.getAttribute("botigues");
}

@RequestMapping(method = RequestMethod.GET, value="/terminals")
public @ResponseBody List<Terminal> getTerminals(HttpSession sessio) {
	return (List<Terminal>) context.getAttribute("terminals");
}

/*- ***************************************************************************** */
/*- ***** PRIVATE ***** */
/*- ***************************************************************************** */
private void ordenarBotiguesPerCiutat(List<Botiga> botigues) {
	Collections.sort(botigues, new Comparator<Botiga>() {
		@Override
		public int compare(Botiga o1, Botiga o2) {
			int compCiutats = o1.getCiutat().compareTo(o2.getCiutat());
			if (compCiutats != 0) {
				return compCiutats;
			} else {
				return o1.getNom().compareTo(o2.getNom());
			}
		}
	});
}
}
