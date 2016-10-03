package com.realdolmen.chiro;

import org.junit.Test;
import org.springframework.boot.context.embedded.PortInUseException;

/**
 * Created by WVDAZ49 on 3/10/2016.
 */
public class ApplicationTest {

    @Test(expected = PortInUseException.class)
    public void testApplicationMain(){
        Application application = new Application();
        application.main(new String[0]);
        application.main(new String[0]);
    }
}
