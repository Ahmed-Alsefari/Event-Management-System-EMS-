package ems.app.service;

import ems.app.model.ServicePackage;
import ems.app.repository.ServicePackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicePackageService {
    private final ServicePackageRepository repo;

    public List<ServicePackage> findAll() {
        return repo.findAll();
    }

    public ServicePackage add(ServicePackage service) {
        return repo.save(service);
    }

    public void updatePrice(Long id, double newPrice) {
        repo.findById(id).ifPresent(s -> {
            s.setPrice(newPrice);
            repo.save(s);
        });
    }

    public void updateService(Long id, String name, String description, double price) {
        repo.findById(id).ifPresent(s -> {
            s.setName(name);
            s.setDescription(description);
            s.setPrice(price);
            repo.save(s);
        });
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // NEW: Toggle active/inactive status
    public void toggleActive(Long id) {
        repo.findById(id).ifPresent(s -> {
            s.setActive(!s.isActive());
            repo.save(s);
        });
    }
}