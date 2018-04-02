package site.share2u.view.job;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;

/**
 * Created by Administrator on 2018/4/1.
 */
public class KettleTest {
    public static void main(String[] args){
        KettleTest.callNativeJob("E://kettlework/dwJOB.kjb");
    }
    public static void callNativeJob(String fiePath){
        try{
            KettleEnvironment.init();
            JobMeta jobMeta = new JobMeta(fiePath, null);
            Job job = new Job(null, jobMeta);
            job.setLogLevel(LogLevel.BASIC);
            // Start the Job, as it is a Thread itself by Kettle.
            job.start();
            job.waitUntilFinished();

            if (job.getResult() != null && job.getResult().getNrErrors() != 0) {
                //Do something here.
            }
            // Now the job task is finished, mark it as finished.
            job.setFinished(true);

            // Cleanup the parameters used by the job. Post that invoke GC.
            jobMeta.eraseParameters();
            job.eraseParameters();
        }catch (Exception e){
            System.out.println(e);
        }


    }
}
