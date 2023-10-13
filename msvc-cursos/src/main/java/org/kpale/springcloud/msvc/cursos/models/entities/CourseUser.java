package org.kpale.springcloud.msvc.cursos.models.entities;
import jakarta.persistence.*;

import java.awt.*;

@Entity
@Table(name= "cursos_usuarios")
public class CourseUser {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", unique = true)
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return userId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.userId = usuarioId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return  true;
        }
        if(!(obj instanceof CourseUser)){
            return false;
        }

        CourseUser courseUser = (CourseUser) obj;

        return this.userId != null && this.userId.equals(courseUser.userId);
    }
}
