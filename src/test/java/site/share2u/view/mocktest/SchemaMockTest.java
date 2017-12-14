package site.share2u.view.mocktest;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import org.junit.Test;

/**
 * @Description
 * @Author chenweimin
 */

public class SchemaMockTest {
    @Injectable
    SimpleTool simpleTool;
    @Test
    public void testExpection(){
        new Expectations(){
            {
                simpleTool.fun1(anyString);
                result = "MOCK";
            }
        };
        System.out.println(simpleTool.fun1("param"));
        System.out.println(simpleTool.fun3("param"));
        System.out.println(new UserimplTool().fun1("param"));

        new Verifications(){
            {
                simpleTool.fun1(anyString);
                times=5;
            }
        };
    }
}
