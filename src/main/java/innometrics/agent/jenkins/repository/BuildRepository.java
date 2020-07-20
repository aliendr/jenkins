package innometrics.agent.jenkins.repository;

import innometrics.agent.jenkins.model.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildRepository extends JpaRepository<Build,String>{

//    public List<Build> findAllByJobNameAndUsername(String jobName, String username);
    List<Build> findAllByJobNameAndUsername(String jobName, String username);

}
