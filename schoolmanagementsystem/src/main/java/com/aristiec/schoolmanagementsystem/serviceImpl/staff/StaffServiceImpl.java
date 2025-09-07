package com.aristiec.schoolmanagementsystem.serviceImpl.staff;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aristiec.schoolmanagementsystem.constant.enums.StaffDesignation;
import com.aristiec.schoolmanagementsystem.dto.staff.StaffDTO;
import com.aristiec.schoolmanagementsystem.modal.staff.Department;
import com.aristiec.schoolmanagementsystem.modal.staff.Staff;
import com.aristiec.schoolmanagementsystem.repository.department.DepartmentRepository;
import com.aristiec.schoolmanagementsystem.repository.staff.StaffRepository;
import com.aristiec.schoolmanagementsystem.service.staff.StaffService;

@Service
public class StaffServiceImpl implements StaffService {
     
    @Autowired
    private StaffRepository staffRepository;
      
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Staff addStaff(StaffDTO staffDTO) {
          
        Staff staff=new Staff();
        staff.setFullName(staffDTO.getFullName());
        staff.setEmail(staffDTO.getEmail());
        staff.setPhone(staffDTO.getPhone());
        staff.setStaffId(staffDTO.getStaffId());
        staff.setSalary(staffDTO.getSalary());
        staff.setDataOfJoining(staffDTO.getDateOfJoining());
        staff.setDesignation(staffDTO.getDesignation());
         Optional<Department> departmentOpt = departmentRepository.findById(staffDTO.getDepartmentId());
        if(departmentOpt.isPresent()) {
            staff.setDepartment(departmentOpt.get());
        } else {
            throw new RuntimeException("Department not found with id: " + staffDTO.getDepartmentId());
        }

        return staffRepository.save(staff);
    
    }

    @Override
    public List<Staff> getAllStaff() {
       return staffRepository.findAll();
    }

    @Override
    public List<Staff> getstaffByDepartment(Long departmentId) {
        
        Department department=departmentRepository.findById(departmentId)
        .orElseThrow(()->new RuntimeException("Department not found with id:"+departmentId));

        return staffRepository.findAll()
            .stream()
            .filter(staff -> staff.getDepartment() != null && departmentId.equals(staff.getDepartment().getId()))
            .collect(Collectors.toList());
    }

    @Override
    public Staff getStaffById(Long id) {
          return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
    }

    @Override
    public void deleteStaff(Long id) {
       if(!staffRepository.existsById(id)){
                       throw new RuntimeException("Staff not found with id: " + id);

       }
    staffRepository.deleteById(id);

    }

    @Override
    public Staff updateStaff(Long id, StaffDTO staffDTO) {
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));

        existingStaff.setFullName(staffDTO.getFullName());;
        existingStaff.setEmail(staffDTO.getEmail());
        existingStaff.setPhone(staffDTO.getPhone());
        existingStaff.setStaffId(staffDTO.getStaffId());
        existingStaff.setSalary(staffDTO.getSalary());
        existingStaff.setDataOfJoining(staffDTO.getDateOfJoining());
        existingStaff.setDesignation(staffDTO.getDesignation());

        Optional<Department> departmentOpt = departmentRepository.findById(staffDTO.getDepartmentId());
        if(departmentOpt.isPresent()) {
            existingStaff.setDepartment(departmentOpt.get());
        } else {
            throw new RuntimeException("Department not found with id: " + staffDTO.getDepartmentId());
        }

        return staffRepository.save(existingStaff);
    }

   

    @Override
    public List<Staff> findByDesignation(StaffDesignation designation) {
             return staffRepository.findByDesignation(designation);

   
    }

   
   
    
}
