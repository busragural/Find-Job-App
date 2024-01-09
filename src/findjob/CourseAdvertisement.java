/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;

import java.time.LocalDate;

/**
 *
 * @author BusraGural
 */
public class CourseAdvertisement extends Advertisement{
    private String courseType;

    // Constructor
    public CourseAdvertisement(int id, int companyId, int appliedCount, String title, String description, String location,
                            LocalDate openDate, LocalDate applicationDeadline, boolean isActive,
                            String courseType) {
        // Advertisement sınıfının constructor'ını çağırarak ortak özellikleri set et
        super(id, companyId, appliedCount, title, description, location, openDate, applicationDeadline, isActive);

        // JobAdvertisement sınıfının özel özelliklerini set et
        this.courseType = courseType;
    }
    
}
