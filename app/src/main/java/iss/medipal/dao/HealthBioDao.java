package iss.medipal.dao;

import java.util.List;

import iss.medipal.model.HealthBio;

/**
 * Created by junaidramis on 11/3/17.
 */

public interface HealthBioDao {

    public int createHealthBio(HealthBio bioData);
    public int updateHealthBio(HealthBio bioData);
    public int deleteHealthBio(int id);
    public List<HealthBio> getAllHealthBio(int id);
    public void close();
    public List<HealthBio> getAllHealthBio();
}
