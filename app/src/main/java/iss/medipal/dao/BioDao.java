package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.HealthBio;
import iss.medipal.model.PersonalBio;


/**
 * Created by Thirumal on 2/21/2017.
 */

public interface BioDao {
    public int createPersonalBio(PersonalBio bioData);
    public int updatePersonalBio(PersonalBio bioData);
    public int deletePersonalBio(int id);
    public PersonalBio findpersonalBiobyId(int id);

    public int createHealthBio(HealthBio bioData);
    public int updateHealthBio(HealthBio bioData);
    public int deleteHealthBio(int id);
    public List<HealthBio> getAllHealthBio(int id);


}
