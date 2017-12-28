package site.share2u.view.mocktest;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @Description
 * @Author chenweimin
 */

@RunWith(JMockit.class)
public class SchemaMockTest {

    /**
     * mocked 和injectable区别：
     * mocked会将类的所有的对象的方法都mock,(在同一个测试用例中)
     * injectable会将某个对象中的方法进行mock
     */
    @Test
    public void testExpection(@Mocked SimpleTool simpleTool) {
        new Expectations() {{
            simpleTool.fun1(anyString);
            result = new Delegate<String>() {
                public String aDelegateMethod(String str) {
                    return str.equals("x0") ? "x1" : "x2";
                }
            };
        }};
        System.out.println(simpleTool.fun1("x0"));
        System.out.println(simpleTool.fun3("xxx"));
        System.out.println(new UserimplTool().fun1("xxx"));
        new Verifications() {{
            simpleTool.fun1(anyString);
            //需要明确mock的方法的调用的次数
            times = 2;
        }};
    }

    @Test
    public void testMockUp() {
        //影响该类的所有的实例
        new MockUp<SimpleTool>() {
            //未mock函数不受影响,,mock注解
            @Mock
            public String fun1(String str) {
                return "MOCKUP";
            }
        };

        System.out.println(new SimpleTool().fun3("xx"));
        System.out.println(new SimpleTool().fun1("xx"));
    }

    /**
     * 对私有方法进行mock
     */
    @Test
    public void testMockPrivate(){
        new MockUp<SimpleTool>(){
            @Mock
            private String fun2(String str){
                return "Mock";
            }
        };
        System.out.println(new SimpleTool().fun4("private"));
    }
}
