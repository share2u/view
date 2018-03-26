package site.share2u.view.service;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import site.share2u.view.pojo.Message;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
            // go ! 事实对象
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            //放到工作内存中会返回一个facthandler（句柄）对象
            kSession.insert(message);
            kSession.fireAllRules();
            System.out.println(message.getMessage());
            kSession.dispose();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
