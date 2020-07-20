//package innometrics.agent.jenkins;
//
//import com.offbytwo.jenkins.JenkinsServer;
//import com.offbytwo.jenkins.model.Job;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.Map;
//
//@Component
//public class Runner implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("hello there");
//        JenkinsServer jenkins = new JenkinsServer(new URI("http://18.224.24.176:8080"), "admin", "admin");
//        Map<String, Job> jobs = jenkins.getJobs();
//
//
//
//
//        jobs.values().forEach(job -> {
//            try {
//                System.out.println(job.details());
//
//
//
//
//
//                job.details().getUpstreamProjects();
//                job.details().getUpstreamProjects();
//                job.details().getQueueItem();
//
//
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//}
