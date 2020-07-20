package innometrics.agent.jenkins.model;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Job {


    private String name;

    private String username;

    private String hostUrl;

    @Lob
    private String description;

    @Id
    //@Lob
    private String url;

    private boolean isBuildable;
    private boolean isInQueue;
    private int nextBuildNumber;

    private String firstBuildId;
    private String lastBuildId;
    private String lastCompletedBuildId;
    private String lastStableBuildId;
    private String lastUnstableBuildId;
    private String lastFailedBuildId;
    private String lastSuccessfulBuildId;
    private String lastUnsuccessfulBuildId;




    private Job() {
    }

    public Job(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public void setBuildable(boolean buildable) {
        isBuildable = buildable;
    }

    public boolean isInQueue() {
        return isInQueue;
    }

    public void setInQueue(boolean inQueue) {
        isInQueue = inQueue;
    }

    public int getNextBuildNumber() {
        return nextBuildNumber;
    }

    public void setNextBuildNumber(int nextBuildNumber) {
        this.nextBuildNumber = nextBuildNumber;
    }

    public String getFirstBuildId() {
        return firstBuildId;
    }

    public void setFirstBuildId(String firstBuildId) {
        this.firstBuildId = firstBuildId;
    }

    public String getLastBuildId() {
        return lastBuildId;
    }

    public void setLastBuildId(String lastBuildId) {
        this.lastBuildId = lastBuildId;
    }

    public String getLastCompletedBuildId() {
        return lastCompletedBuildId;
    }

    public void setLastCompletedBuildId(String lastCompletedBuildId) {
        this.lastCompletedBuildId = lastCompletedBuildId;
    }

    public String getLastStableBuildId() {
        return lastStableBuildId;
    }

    public void setLastStableBuildId(String lastStableBuildId) {
        this.lastStableBuildId = lastStableBuildId;
    }

    public String getLastUnstableBuildId() {
        return lastUnstableBuildId;
    }

    public void setLastUnstableBuildId(String lastUnstableBuildId) {
        this.lastUnstableBuildId = lastUnstableBuildId;
    }

    public String getLastFailedBuildId() {
        return lastFailedBuildId;
    }

    public void setLastFailedBuildId(String lastFailedBuildId) {
        this.lastFailedBuildId = lastFailedBuildId;
    }

    public String getLastSuccessfulBuildId() {
        return lastSuccessfulBuildId;
    }

    public void setLastSuccessfulBuildId(String lastSuccessfulBuildId) {
        this.lastSuccessfulBuildId = lastSuccessfulBuildId;
    }

    public String getLastUnsuccessfulBuildId() {
        return lastUnsuccessfulBuildId;
    }

    public void setLastUnsuccessfulBuildId(String lastUnsuccessfulBuildId) {
        this.lastUnsuccessfulBuildId = lastUnsuccessfulBuildId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }
}
