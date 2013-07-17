package com.carles.minimal.base;

import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Version;

//@MappedSuperclass
public abstract class Entitat {

@Id
private ObjectId id;

@Version
private Long version;

/*- ***************************************************************************** */
/*- ***** OVERRIDE ***** */
/*- ***************************************************************************** */
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Entitat other = (Entitat) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}

/*- ***************************************************************************** */
/*- ***** GETTERS I SETTERS ***** */
/*- ***************************************************************************** */
public ObjectId getId() {
	return id;
}
public void setId(ObjectId id) {
	this.id = id;
}
public Long getVersion() {
	return version;
}
public void setVersion(Long version) {
	this.version = version;
}

}
