package ems.app;

import ems.app.controller.AdminController;
import ems.app.model.ServicePackage;
import ems.app.service.ServicePackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ServicePackagesTest {

    @Mock
    private ServicePackageService packageService;

    @Mock
    private Model model;

    @InjectMocks
    private AdminController adminController;

    private ServicePackage testPackage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testPackage = new ServicePackage();
        testPackage.setId(1L);
        testPackage.setName("Wedding Package");
        testPackage.setDescription("Complete wedding service");
        testPackage.setPrice(5000.0);
        testPackage.setActive(true);
    }

    @Test
    void testManagePackages_Success() {
        // Arrange
        List<ServicePackage> packages = Arrays.asList(testPackage);
        when(packageService.findAll()).thenReturn(packages);

        // Act
        String viewName = adminController.managePackages(model);

        // Assert
        assertEquals("admin_packages", viewName);
        verify(model).addAttribute(eq("packages"), eq(packages));
        verify(model).addAttribute(eq("newPackage"), any(ServicePackage.class));
        verify(packageService).findAll();
    }

    @Test
    void testAddService_Success() {
        // Arrange
        when(packageService.add(any(ServicePackage.class))).thenReturn(testPackage);

        // Act
        String viewName = adminController.addService(testPackage);

        // Assert
        assertEquals("redirect:/admin/packages?added", viewName);
        verify(packageService).add(testPackage);
    }

    @Test
    void testUpdateService_Success() {
        // Arrange
        doNothing().when(packageService).updateService(1L, "Updated Name", "Updated Desc", 6000.0);

        // Act
        String viewName = adminController.updateService(1L, "Updated Name", "Updated Desc", 6000.0);

        // Assert
        assertEquals("redirect:/admin/packages?updated", viewName);
        verify(packageService).updateService(1L, "Updated Name", "Updated Desc", 6000.0);
    }

    @Test
    void testDeleteService_Success() {
        // Arrange
        doNothing().when(packageService).delete(1L);

        // Act
        String viewName = adminController.deleteService(1L);

        // Assert
        assertEquals("redirect:/admin/packages?deleted", viewName);
        verify(packageService).delete(1L);
    }

    @Test
    void testTogglePackageStatus_Success() {
        // Arrange
        doNothing().when(packageService).toggleActive(1L);

        // Act
        String viewName = adminController.togglePackageStatus(1L);

        // Assert
        assertEquals("redirect:/admin/packages?toggled", viewName);
        verify(packageService).toggleActive(1L);
    }
}