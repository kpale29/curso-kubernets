package org.kpale.springcloud.msvc.cursos.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.kpale.springcloud.msvc.cursos.models.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cursos")
public class Course
{
    public Course() {
        courseUsers = new ArrayList<>();
        users = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CourseUser> courseUsers;

    @Transient
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseUser> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(List<CourseUser> courseUsers) {
        this.courseUsers = courseUsers;
    }

    public void addCourseUser(CourseUser courseUser){
        this.courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser){
        this.courseUsers.remove(courseUser);
    }

}
