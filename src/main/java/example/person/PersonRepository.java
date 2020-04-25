package example.person;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

  Optional<Person> findByLastName(String lastName);

}
