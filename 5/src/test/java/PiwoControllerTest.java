import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;

public class PiwoControllerTest {
    @Mock
    PiwoController controller;

    @Before
    public void setUpValidate(){
        controller = new PiwoController(new Repositorium());

        controller.add("Tyskie", 4);
        controller.add("Guiness", 6);
        controller.add("Książęce", 5);
        controller.add("Żubr", 4);
    }

    @Test
    public void validateExistingObjectDelete(){
        String result = controller.delete("Tyskie");

        assertThat(result).isEqualTo("done");
    }

    @Test
    public void validateNonExistingObjectDelete(){
        String result = controller.delete("Łomża");

        assertThat(result).isEqualTo("not found");
    }

    @Test
    public void validateNonExistingObjectDownload(){
        String result = controller.find("Guinesss");

        assertThat(result).isEqualTo("not found");
    }

    @Test
    public void validateExistingObjectDownload(){
        String result = controller.find("Guiness");

        assertThat(result).isEqualTo("Guiness 6");
    }

    @Test
    public void validateExistingObjectSave(){
        String result = controller.add("Corona", 3);

        assertThat(result).isEqualTo("done");
    }

    @Test
    public void validateNonExistingObjectSave(){
        String result = controller.add("Tyskie", 3);

        assertThat(result).isEqualTo("bad request");
    }
}
