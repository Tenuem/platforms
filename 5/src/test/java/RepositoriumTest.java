import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

public class RepositoriumTest {
    @Mock
    Repositorium repository;

    @Before
    public void setUpValidate(){
        repository = new Repositorium();
        repository.add(new Piwo("Tyskie", 4));
        repository.add(new Piwo("Guiness", 6));
        repository.add(new Piwo("Książęce", 5));
        repository.add(new Piwo("Żubr", 4));
    }

    @Test
    public void validateHandlingNotExistingObjectRemoval() {
        try {
            repository.delete("Książence");
        } catch (Throwable ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);

            return;
        }

        assertThat(false).isTrue();
    }

    @Test
    public void validateHandlingNotExistingObjectDownload(){
        Optional<Piwo> found = repository.find("Książence");

        assertThat(found).isEqualTo(Optional.empty());
    }

    @Test
    public void validateHandlingExistingObjectDownload(){
        repository.add(new Piwo("Tatra", 4));
        Optional<Piwo> found = repository.find("Tatra");

        Piwo found2 = found.get();

        assertThat(found2.equals(new Piwo("Tatra", 6))).isTrue();
    }

    @Test
    public void validateHandlingExistingObjectSave(){
        try {
            repository.add(new Piwo("Guiness", 4));
        } catch (Throwable ex) {
            assertThat(ex).isInstanceOf(IllegalArgumentException.class);
            return;
        }

        assertThat(false).isTrue();
    }
}
