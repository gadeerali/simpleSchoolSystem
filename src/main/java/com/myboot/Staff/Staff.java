package com.myboot.Staff;


import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import java.util.Objects;
@Entity
@SQLDelete(sql = "UPDATE STAFF SET deleted = true WHERE id = ?")
@FilterDef(name = "deletedStaffFilter",parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedStaffFilter" , condition = "deleted = :isDeleted")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String title;
    private boolean deleted = false;



    public Staff() {
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return id == staff.id && Objects.equals(name, staff.name) && Objects.equals(title, staff.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title);
    }
}
