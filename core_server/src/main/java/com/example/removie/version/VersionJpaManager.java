package com.example.removie.version;

import com.example.removie.movie.jpaManager.UpdateJpaManager;
import com.example.removie.version.repository.VersionEntity;
import com.example.removie.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class VersionJpaManager implements UpdateJpaManager<Integer> {
    private final VersionRepository versionRepository;

    @Autowired
    public VersionJpaManager(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Transactional
    public Integer createNewVersion(){
        return versionRepository.findMaxVersion().orElse(0) + 1;
    }

    @Transactional
    public Integer getLatestVersion(){
        return versionRepository.findMaxVersion().orElse(0);
    }

    @Transactional
    public void saveNewVersion(Integer version){
        versionRepository.save(new VersionEntity(version, LocalDate.now()));
    }

    @Override
    @Transactional
    public void update(Integer version) {
        saveNewVersion(version);
    }
}
