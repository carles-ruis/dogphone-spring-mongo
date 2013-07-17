package com.carles.minimal.main;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.carles.minimal.domain.Botiga;
import com.carles.minimal.domain.Terminal;
import com.google.code.morphia.Datastore;

public class Main_InsertBotiguesAndTerminals {

private static final List<Botiga> BOTIGUES = Arrays.asList(
	new Botiga("Poblenou", "Rambla Poblenou, 33","(555)123 45 56",41.400441d,2.202519d,"Barcelona"),
	new Botiga("Marti Pujol", "c/ Martí i Pujol, 36","(555)123 45 59",41.452119d,2.249765d,"Badalona"),
	new Botiga("Diagonal Mar", "Av Diagonal, 34","(555)123 45 57",41.413509d,2.215605d,"Barcelona"),
	new Botiga("Sant Andreu", "c/ Gran de Sant Andreu, 35","(555)123 45 58",41.411964d,2.188654d,"Barcelona"),
	new Botiga("Canonge", "c/ Canonge Rodó, 36","(555)123 45 59",41.452698d,2.250381d,"Badalona"),
	new Botiga("Santa Coloma", "c/ Santa Coloma, 37","(555)123 45 60",41.455529d,2.204633d,"Santa Coloma") );
private static final String DESCRIPCIO = "Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.";
private static final List<Terminal> TERMINALS = Arrays.asList(
	new Terminal("Iphone 5 16GB Negro",DESCRIPCIO,"iphone.jpg"),
	new Terminal("Sony Xperia Z Negra",DESCRIPCIO,"sony.png"),
	new Terminal("LG Optimus L3 2 E430 blanco",DESCRIPCIO,"lg.jpg"),
	new Terminal("Samsung galaxy Grand Azul",DESCRIPCIO,"samsung.jpg"),
	new Terminal("Nokia Lumia 920 Blanco",DESCRIPCIO,"nokia.jpg"),
	new Terminal("Huawei Ascend Mate Negro",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC One PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Two PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Three PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Four PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Five PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Six PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Seven PerLeft White",DESCRIPCIO,"huawei.jpg"),
	new Terminal("HTC Eight PerLeft White",DESCRIPCIO,"huawei.jpg") );

/*- ***************************************************************************** */
/*- ***** MAIN ***** */
/*- ***************************************************************************** */
public static void main(String[] s) {
//	List<Botiga> botigues = transaccioBotigues();
//	System.out.println("Total n de botigues:"+botigues.size());
//	List<Terminal> terminals = transaccioTerminals();
//	System.out.println("Total n de terminals:"+terminals.size());
	System.out.println(iniBotiguesAndTerminals());
}

/*- ***************************************************************************** */
/*- ***** PRIVATE (SPRING-DATA-MONGODB) ***** */
/*- ***************************************************************************** */
//@Transactional
//private static List<Botiga> transaccioBotigues() {
//	List<Botiga> ret = null;
//	ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
//	BotigaRepo repo = ctx.getBean(BotigaRepo.class);
//	repo.deleteAll();
//	repo.save(BOTIGUES);
//	ret = repo.findAll();
//	return ret;
//}
//@Transactional
//private static List<Terminal> transaccioTerminals() {
//	List<Terminal> ret = null;
//	ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
//	TerminalRepo repo = ctx.getBean(TerminalRepo.class);
//	repo.deleteAll();
//	repo.save(TERMINALS);
//	ret = repo.findAll();
//	return ret;
//}

/*- ***************************************************************************** */
/*- ***** PRIVATE (MORPHIA) ***** */
/*- ***************************************************************************** */
private static String iniBotiguesAndTerminals() {
	ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
	Datastore ds = (Datastore)ctx.getBean("datastore");
//	ds.ensureIndexes();
//	ds.ensureCaps();
	ds.delete(ds.createQuery(Botiga.class));
	ds.save(BOTIGUES);
	ds.delete(ds.createQuery(Terminal.class));
	ds.save(TERMINALS);
	int nBotigues = ds.find(Botiga.class).asList().size();
	int nTerminals= ds.find(Terminal.class).asList().size();
	return "Total botigues:"+nBotigues+"; total terminals:"+nTerminals;
}
}
