package example.person;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryIntegrationTest {

  @Autowired
  private PersonRepository subject;

  @After
  public void tearDown() throws Exception {
    subject.deleteAll();
  }

  @Test
  public void shouldSaveAndFetchPerson() throws Exception {
    Person peter = new Person("Peter", "Pan");
    subject.save(peter);

    Optional<Person> maybePeter = subject.findByLastName("Pan");

    assertThat(maybePeter, is(Optional.of(peter)));
  }
}