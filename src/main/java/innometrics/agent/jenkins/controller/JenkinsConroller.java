package innometrics.agent.jenkins.controller;

import com.offbytwo.jenkins.model.Job;
import innometrics.agent.jenkins.service.JenkinsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
public class JenkinsConroller {

    @Autowired
    JenkinsService jenkinsService;


    @ApiOperation(
            value = "get list of all jobs on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @PostMapping("/jobs/lookup")
    public Map<String, Job> getAllJobs(@RequestParam String url, @RequestParam String username, @RequestParam String pass) throws Exception {
        return jenkinsService.connect(url,username,pass);
    }


    @ApiOperation(
            value = "fetch all jobs on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @PostMapping("/jobs/fetch")
    public List<innometrics.agent.jenkins.model.Job> fetchAllJobs(@RequestParam String url, @RequestParam String username, @RequestParam String pass) throws Exception {
        return jenkinsService.fetchAllJobs(url,username,pass);
    }

    @ApiOperation(
            value = "fetch a job on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @PostMapping("/jobs/fetch/{jobName}")
    public innometrics.agent.jenkins.model.Job fetchJob(@PathVariable String jobName, @RequestParam String url, @RequestParam String username, @RequestParam String pass) throws Exception {
        return jenkinsService.fetchJob(jobName,url,username,pass);
    }

    @ApiOperation(
            value = "fetch all builds for that job on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Build.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @PostMapping("/builds/{jobName}")
    public List<innometrics.agent.jenkins.model.Build> fetchAllBuildsForJob(@PathVariable String jobName, @RequestParam String url, @RequestParam String username, @RequestParam String pass) throws Exception {
        return jenkinsService.fetchAllBuildsForJob(jobName, url,username,pass);
    }

    @ApiOperation(
            value = "fetch a job with builds in one time on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @PostMapping("/jobs/builds/fetch/{jobName}")
    public innometrics.agent.jenkins.model.Job fetchJobWithBuilds(@PathVariable String jobName, @RequestParam String url, @RequestParam String username, @RequestParam String pass) throws Exception {
        return jenkinsService.fetchJobWithBuilds(jobName,url,username,pass);
    }

    @ApiOperation(
            value = "fetch all jobs with their builds on that host in one time"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @PostMapping("/jobs/builds/fetch")
    public List<innometrics.agent.jenkins.model.Job> fetchAllJobsWithBuilds(@RequestParam String url, @RequestParam String username, @RequestParam String pass) throws Exception {
        return jenkinsService.fetchAllJobsWithBuilds(url,username,pass);
    }

    @ApiOperation(
            value = "get all fetched jobs on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @GetMapping("/jobs")
    public List<innometrics.agent.jenkins.model.Job> getFetchedJobs(@RequestParam String url, @RequestParam String username){
        return jenkinsService.getAllJobs(url, username);
    }

    @ApiOperation(
            value = "get a certain fetched job on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @GetMapping("/jobs/{jobName}")
    public innometrics.agent.jenkins.model.Job getAFetchedJob(@PathVariable String jobName, @RequestParam String url, @RequestParam String username){
        return jenkinsService.getAJob(jobName, url, username);
    }

    @ApiOperation(
            value = "get builds for a certain fetched job on that host"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successful",
                    response = innometrics.agent.jenkins.model.Job.class
            ),
            @ApiResponse(
                    code = 400,
                    message = "Some problem arrived, message will contain information",
                    response = ResponseStatusException.class
            )
    })
    @GetMapping("/builds/{jobName}")
    public List<innometrics.agent.jenkins.model.Build> getBuildsForAFetchedJob(@PathVariable String jobName, @RequestParam String username){
        return jenkinsService.getBuildsForAJob(jobName, username);
    }

}
