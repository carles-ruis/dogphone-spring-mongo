package com.carles.minimal.backend;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.carles.minimal.domain.Usuari;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.dao.BasicDAO;

@Repository 
public class UsuariRepo extends BasicDAO<Usuari, ObjectId> {

/*- ***************************************************************************** */
/*- ***** CONSTRUCTORS ***** */
/*- ***************************************************************************** */
//public UsuariRepo(Class<Usuari> entityClass, Datastore ds) {
//	super(entityClass, ds);
//}
@Autowired public UsuariRepo(Datastore ds) {
	super(Usuari.class, ds);
}

}
