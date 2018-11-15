
import java.util.List;

import ControladoresJPA.ClienteJpaController;

public interface CiudadDAO {

	public void addPerson(ClienteJpaController p);
	public void updatePerson(ClienteJpaController p);
	public List<ClienteJpaController> listPersons();
	public ClienteJpaController getPersonById(int id);
	public void removePerson(int id);
}