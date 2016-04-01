package org.vaadin.peholmst.samples.dddwebinar.domain.doctors;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;

@Service
public class LicenseService {

    @Autowired
    LicenseRepository licenseRepository;

    public Optional<License> selectBestLicense(Collection<Procedure> procedures, Collection<License> licenses) {
        Set<License> availableLicenses = licenses.stream()
            .filter(l -> procedures.stream().allMatch(p -> p.getCategory().getLicenseTypes().containsKey(l.getType())))
            .collect(Collectors.toSet());
        if (availableLicenses.isEmpty()) {
            return Optional.empty();
        } else {
            License best = null;
            int bestRank = Integer.MAX_VALUE;
            for (License l : availableLicenses) {
                for (Procedure p : procedures) {
                    int rank = p.getCategory().getLicenseTypes().get(l.getType());
                    if (rank < bestRank) {
                        bestRank = rank;
                        best = l;
                    }
                }
            }
            return Optional.ofNullable(best);
        }
    }

    public Optional<License> selectBestLicense(Collection<Procedure> procedures, Doctor doctor) {
        return selectBestLicense(procedures, licenseRepository.findByDoctor(doctor));
    }
}
