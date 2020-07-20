package innometrics.agent.jenkins.repository;

import innometrics.agent.jenkins.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job,String> {

    public List<Job> findAllByUsernameAndHostUrl(String username, String hostUrl);

    public Job findByUsernameAndHostUrlAndName(String username, String hostUrl, String name);

}
