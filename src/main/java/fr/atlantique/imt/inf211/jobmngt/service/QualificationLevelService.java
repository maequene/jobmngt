package fr.atlantique.imt.inf211.jobmngt.service;

import java.util.List;

import fr.atlantique.imt.inf211.jobmngt.entity.QualificationLevel;


public interface QualificationLevelService {

    public List<QualificationLevel> listOfQualificationLevels();

    public long countQualificationLevel();
    
}