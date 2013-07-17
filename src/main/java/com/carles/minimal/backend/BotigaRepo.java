package com.carles.minimal.backend;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.carles.minimal.domain.Botiga;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;

@Repository
public class BotigaRepo extends BasicDAO<Botiga, ObjectId> {

/*- ***************************************************************************** */
/*- ***** CONSTRUCTORS ***** */
/*- ***************************************************************************** */
//public BotigaRepo(Class<Botiga> entityClass, Datastore ds) {
//	super(entityClass, ds);
//}
@Autowired public BotigaRepo(Datastore ds) {
	super(Botiga.class, ds);
}
}
