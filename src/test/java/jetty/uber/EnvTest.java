package jetty.uber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EnvTest
{
    @Test
    public void testEnv()
    {
        System.out.println("$HOME = " + System.getenv("HOME"));
        System.out.println("$M2_HOME = " + System.getenv("M2_HOME"));
        System.out.println("$MAVEN_HOME = " + System.getenv("MAVEN_HOME"));
        System.out.println("$JAVA_HOME = " + System.getenv("JAVA_HOME"));
    
        new RuntimeException("Ooops").printStackTrace(System.err);
        
        System.getProperties().entrySet().stream()
                .filter(entry -> entry.getKey().toString().matches("^(java|sun|user)\\..*"))
                .sorted((e1, e2) -> e1.getKey().toString().compareTo(e2.getKey().toString()))
                .forEach(entry -> System.out.println("  " + entry.getKey() + " = " + entry.getValue()));
        
        assertThat("BRANCH Exists", System.getenv("BRANCH"), nullValue());
        assertThat("JOB_NAME", System.getenv("JOB_NAME"), is("Foo"));

    }
}
