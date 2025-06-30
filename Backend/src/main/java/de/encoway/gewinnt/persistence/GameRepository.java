package de.encoway.gewinnt.persistence;

import de.encoway.gewinnt.model.DatabaseObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Aleksandar Pantelic
 */
@Repository
public interface GameRepository extends CrudRepository<DatabaseObject, String> {

}
