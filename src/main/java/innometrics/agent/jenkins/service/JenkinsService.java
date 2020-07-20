package innometrics.agent.jenkins.service;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import innometrics.agent.jenkins.repository.BuildRepository;
import innometrics.agent.jenkins.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Service
public class JenkinsService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    BuildRepository buildRepository;


    public Map<String, Job> connect(String url, String username, String pass) throws Exception{
        JenkinsServer jenkins = new JenkinsServer(new URI(url), username, pass);

        return jenkins.getJobs();

    }

    @Transactional
    public List<innometrics.agent.jenkins.model.Job> fetchAllJobs(String url, String username, String pass) throws Exception{

        JenkinsServer jenkins = new JenkinsServer(new URI(url), username, pass);

        Map<String, Job> jobs = jenkins.getJobs();

        List<innometrics.agent.jenkins.model.Job> jobList = new ArrayList<innometrics.agent.jenkins.model.Job>();


        for (Map.Entry<String, Job> entry : jobs.entrySet()) {

                Optional<innometrics.agent.jenkins.model.Job> savedJob = Optional.ofNullable(jobRepository.findByUsernameAndHostUrlAndName(username, url, entry.getKey()));

                if (savedJob.isPresent()){
                    innometrics.agent.jenkins.model.Job job = updateAJob(savedJob.get(),entry.getValue());
                    job.setHostUrl(url);
                    job.setUsername(username);
                    jobRepository.save(job);
                    jobList.add(job);
                } else {
                    innometrics.agent.jenkins.model.Job job = saveAJob(entry.getKey(),entry.getValue());
                    job.setHostUrl(url);
                    job.setUsername(username);
                    jobRepository.save(job);
                    jobList.add(job);
                }
        }

        return jobList;

    }

    @Transactional
    public innometrics.agent.jenkins.model.Job fetchJob(String jobName, String url, String username, String pass) throws Exception{
        JenkinsServer jenkins = new JenkinsServer(new URI(url), username, pass);
        Map<String, Job> jobs = jenkins.getJobs();

        Optional<innometrics.agent.jenkins.model.Job> savedJob = Optional.ofNullable(jobRepository.findByUsernameAndHostUrlAndName(username, url, jobName));

        if (savedJob.isPresent()){
            innometrics.agent.jenkins.model.Job job = updateAJob(savedJob.get(),jobs.get(jobName));
            job.setHostUrl(url);
            job.setUsername(username);
            jobRepository.save(job);
            return job;
        } else {
            innometrics.agent.jenkins.model.Job job = saveAJob(jobName,jobs.get(jobName));
            job.setHostUrl(url);
            job.setUsername(username);
            jobRepository.save(job);
            return job;
        }

    }

    @Transactional
    public List<innometrics.agent.jenkins.model.Build> fetchAllBuildsForJob(String jobName, String url, String username, String pass) throws Exception{
        JenkinsServer jenkins = new JenkinsServer(new URI(url), username, pass);
        Map<String, Job> jobs = jenkins.getJobs();

        Job jobFromServer = jobs.get(jobName);

        List<Build> buildsFromServer = jobFromServer.details().getAllBuilds();

        List<innometrics.agent.jenkins.model.Build> myBuildsList = new ArrayList< innometrics.agent.jenkins.model.Build >();

        for (Build buildFrom : buildsFromServer) {

            Optional<innometrics.agent.jenkins.model.Build> savedBuild = buildRepository.findById(buildFrom.getUrl());

            if(savedBuild.isPresent()){
                innometrics.agent.jenkins.model.Build build = updateABuild(savedBuild.get(),buildFrom);
                build.setUsername(username);
                build.setJobName(jobName);
                buildRepository.save(build);
                myBuildsList.add(build);
            }else {
                innometrics.agent.jenkins.model.Build build = saveABuild(jobName,buildFrom);
                build.setUsername(username);
                buildRepository.save(build);
                myBuildsList.add(build);
            }

        }

        return myBuildsList;

    }

    @Transactional
    public List<innometrics.agent.jenkins.model.Job> fetchAllJobsWithBuilds(String url, String username, String pass) throws Exception{

        JenkinsServer jenkins = new JenkinsServer(new URI(url), username, pass);

        Map<String, Job> jobs = jenkins.getJobs();

        List<innometrics.agent.jenkins.model.Job> jobList = new ArrayList<innometrics.agent.jenkins.model.Job>();


        for (Map.Entry<String, Job> entry : jobs.entrySet()) {


            Optional<innometrics.agent.jenkins.model.Job> savedJob = Optional.ofNullable(jobRepository.findByUsernameAndHostUrlAndName(username, url, entry.getKey()));

            if (savedJob.isPresent()){
                innometrics.agent.jenkins.model.Job job = updateAJob(savedJob.get(),entry.getValue());
                job.setHostUrl(url);
                job.setUsername(username);
                jobRepository.save(job);
                jobList.add(job);
            } else {
                innometrics.agent.jenkins.model.Job job = saveAJob(entry.getKey(),entry.getValue());
                job.setHostUrl(url);
                job.setUsername(username);
                jobRepository.save(job);
                jobList.add(job);
            }


            List<Build> buildsFromServer = entry.getValue().details().getAllBuilds();

            for (Build buildFrom : buildsFromServer) {

                Optional<innometrics.agent.jenkins.model.Build> savedBuild = buildRepository.findById(buildFrom.getUrl());

                if(savedBuild.isPresent()){
                    innometrics.agent.jenkins.model.Build build = updateABuild(savedBuild.get(),buildFrom);
                    build.setUsername(username);
                    build.setJobName(entry.getKey());
                    buildRepository.save(build);

                }else {
                    innometrics.agent.jenkins.model.Build build = saveABuild(entry.getKey(),buildFrom);
                    build.setUsername(username);
                    buildRepository.save(build);

                }
            }

        }

        return jobList;

    }

    @Transactional
    public innometrics.agent.jenkins.model.Job fetchJobWithBuilds(String jobName, String url, String username, String pass) throws Exception{
        JenkinsServer jenkins = new JenkinsServer(new URI(url), username, pass);
        Map<String, Job> jobs = jenkins.getJobs();


        Optional<innometrics.agent.jenkins.model.Job> savedJob = Optional.ofNullable(jobRepository.findByUsernameAndHostUrlAndName(username, url, jobName));

        innometrics.agent.jenkins.model.Job job;

        if (savedJob.isPresent()){
            job = updateAJob(savedJob.get(),jobs.get(jobName));
            job.setHostUrl(url);
            job.setUsername(username);
            jobRepository.save(job);

        } else {
            job = saveAJob(jobName,jobs.get(jobName));
            job.setHostUrl(url);
            job.setUsername(username);
            jobRepository.save(job);

        }

        List<Build> buildsFromServer = jobs.get(jobName).details().getAllBuilds();

        for (Build buildFrom : buildsFromServer) {
            Optional<innometrics.agent.jenkins.model.Build> savedBuild = buildRepository.findById(buildFrom.getUrl());

            if(savedBuild.isPresent()){
                innometrics.agent.jenkins.model.Build build = updateABuild(savedBuild.get(),buildFrom);
                build.setUsername(username);
                build.setJobName(jobName);
                buildRepository.save(build);

            }else {
                innometrics.agent.jenkins.model.Build build = saveABuild(jobName,buildFrom);
                build.setUsername(username);
                buildRepository.save(build);

            }
        }

        return job;

    }

    @Transactional
    public List<innometrics.agent.jenkins.model.Job> getAllJobs(String url, String username){

        return jobRepository.findAllByUsernameAndHostUrl(username, url);

    }

    @Transactional
    public innometrics.agent.jenkins.model.Job getAJob(String jobName,String url, String username){

        return jobRepository.findByUsernameAndHostUrlAndName(username, url, jobName);

    }

    @Transactional
    public List<innometrics.agent.jenkins.model.Build> getBuildsForAJob(String jobName, String username){

        return buildRepository.findAllByJobNameAndUsername(jobName,username);

    }

    private innometrics.agent.jenkins.model.Job saveAJob(String jobName,Job jobFromJenkinsServer) throws IOException {

        innometrics.agent.jenkins.model.Job job = new innometrics.agent.jenkins.model.Job(jobName,jobFromJenkinsServer.getUrl());

        job.setBuildable(jobFromJenkinsServer.details().isBuildable());
        job.setDescription(jobFromJenkinsServer.details().getDescription());
        job.setNextBuildNumber(jobFromJenkinsServer.details().getNextBuildNumber());
        job.setLastBuildId(jobFromJenkinsServer.details().getLastBuild().details().getId());
        job.setFirstBuildId(jobFromJenkinsServer.details().getFirstBuild().details().getId());
        job.setInQueue(jobFromJenkinsServer.details().isInQueue());
        job.setLastCompletedBuildId(jobFromJenkinsServer.details().getLastCompletedBuild().details().getId());
        job.setLastStableBuildId(jobFromJenkinsServer.details().getLastStableBuild().details().getId());
        job.setLastUnstableBuildId(jobFromJenkinsServer.details().getLastUnstableBuild().details().getId());
        job.setLastFailedBuildId(jobFromJenkinsServer.details().getLastFailedBuild().details().getId());
        job.setLastSuccessfulBuildId(jobFromJenkinsServer.details().getLastSuccessfulBuild().details().getId());
        job.setLastUnsuccessfulBuildId(jobFromJenkinsServer.details().getLastUnsuccessfulBuild().details().getId());

        return job;
    }

    private innometrics.agent.jenkins.model.Build saveABuild(String jobName,Build buildFromServer) throws IOException {

        innometrics.agent.jenkins.model.Build build = new innometrics.agent.jenkins.model.Build(buildFromServer.details().getId(),buildFromServer.getUrl());

        build.setDescription(buildFromServer.details().getDescription());
        build.setQueueId(buildFromServer.getQueueId());
        build.setDuration(buildFromServer.details().getDuration());
        build.setEstimatedDuration(buildFromServer.details().getEstimatedDuration());
        build.setDescription(buildFromServer.details().getDescription());
        build.setTimestamp(buildFromServer.details().getTimestamp());
        build.setConsoleOutputText(buildFromServer.details().getConsoleOutputText());
        build.setFullDisplayName(buildFromServer.details().getFullDisplayName());

        build.setJobName(jobName);

        return build;
    }

    private innometrics.agent.jenkins.model.Build updateABuild(innometrics.agent.jenkins.model.Build savedBuild, Build buildFromServer) throws IOException {

        savedBuild.setId(buildFromServer.details().getId());
        savedBuild.setUrl(buildFromServer.getUrl());
        savedBuild.setDescription(buildFromServer.details().getDescription());
        savedBuild.setQueueId(buildFromServer.getQueueId());
        savedBuild.setDuration(buildFromServer.details().getDuration());
        savedBuild.setEstimatedDuration(buildFromServer.details().getEstimatedDuration());
        savedBuild.setDescription(buildFromServer.details().getDescription());
        savedBuild.setTimestamp(buildFromServer.details().getTimestamp());
        savedBuild.setConsoleOutputText(buildFromServer.details().getConsoleOutputText());
        savedBuild.setFullDisplayName(buildFromServer.details().getFullDisplayName());

        return savedBuild;
    }


    private innometrics.agent.jenkins.model.Job updateAJob(innometrics.agent.jenkins.model.Job jobFromBd, Job jobFromJenkinsServer) throws IOException {

        jobFromBd.setName(jobFromJenkinsServer.getName());
        jobFromBd.setUrl(jobFromJenkinsServer.getUrl());
        jobFromBd.setBuildable(jobFromJenkinsServer.details().isBuildable());
        jobFromBd.setDescription(jobFromJenkinsServer.details().getDescription());
        jobFromBd.setNextBuildNumber(jobFromJenkinsServer.details().getNextBuildNumber());
        jobFromBd.setLastBuildId(jobFromJenkinsServer.details().getLastBuild().details().getId());
        jobFromBd.setFirstBuildId(jobFromJenkinsServer.details().getFirstBuild().details().getId());
        jobFromBd.setInQueue(jobFromJenkinsServer.details().isInQueue());
        jobFromBd.setLastCompletedBuildId(jobFromJenkinsServer.details().getLastCompletedBuild().details().getId());
        jobFromBd.setLastStableBuildId(jobFromJenkinsServer.details().getLastStableBuild().details().getId());
        jobFromBd.setLastUnstableBuildId(jobFromJenkinsServer.details().getLastUnstableBuild().details().getId());
        jobFromBd.setLastFailedBuildId(jobFromJenkinsServer.details().getLastFailedBuild().details().getId());
        jobFromBd.setLastSuccessfulBuildId(jobFromJenkinsServer.details().getLastSuccessfulBuild().details().getId());
        jobFromBd.setLastUnsuccessfulBuildId(jobFromJenkinsServer.details().getLastUnsuccessfulBuild().details().getId());

        return jobFromBd;
    }
}
