package com.carles.minimal.backend;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.carles.minimal.domain.Terminal;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;

@Repository 
public class TerminalRepo extends BasicDAO<Terminal, ObjectId> {

/*- ***************************************************************************** */
/*- ***** CONSTRUCTORS ***** */
/*- ***************************************************************************** */
//public TerminalRepo(Class<Terminal> entityClass, Datastore ds) {
//	super(entityClass, ds);
//}
@Autowired public TerminalRepo(Datastore ds) {
	super(Terminal.class, ds);
}

}
