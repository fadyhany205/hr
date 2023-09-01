package com.hr.bean;

import com.hr.model.Department;
import com.hr.model.Employee;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.hibernate.cfg.Configuration;

@ManagedBean(name = "employeeManagerBean")
@ViewScoped
public class EmployeeBean implements Serializable {

    private List<Employee> employeeList = new ArrayList<>();
    private List<Department> departmentList = new ArrayList<>();
    private Employee newEmployee;
    SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    Session session = null;

    public List<Employee> getEmployees() {

        try {

            session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Employee.class);
            employeeList = criteria.list();
            if(session.isOpen()){
                    session.close();  
                }
            return employeeList;
        } catch (Exception ex) {
            
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ex.getMessage()));
            System.out.println(ex.getMessage());
                 if(session.isOpen()){
                     session.close();
                 }
            return null;
        }
    }

    public List<Department> getDepartments() {

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Department.class);
            departmentList = criteria.list();
            if(session.isOpen()){
                    session.close();  
                }
            return departmentList;
        } catch (Exception ex) {
            
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ex.getMessage()));
            System.out.println(ex.getMessage());
                 if(session.isOpen()){
                     session.close();
                 }
            return null;
        }
    }
public void resetPage(){
    employeeList=new ArrayList<>();
    newEmployee = new Employee();
    getEmployees();
}
    public void addEmployee() {
        System.out.println("in function");
        Transaction tx = null;
        if (newEmployee != null) {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                session.saveOrUpdate(newEmployee);
                tx.commit();
                resetPage();
                if(session.isOpen()){
                    session.close();  
                }
                
            } catch (Exception e) {
                
                System.out.println("Error");
                 FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
                 System.out.println(e.getMessage());
                 if(session.isOpen()){
                     session.close();
                 }
                 
            }

        }

    }

    public EmployeeBean() {
        newEmployee = new Employee();
        getEmployees();
        getDepartments();
    }

    @PostConstruct
    public void init() {

    }

    public Employee getNewEmployee() {
        return newEmployee;
    }

    public void setNewEmployee(Employee newEmployee) {
        this.newEmployee = newEmployee;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

}
