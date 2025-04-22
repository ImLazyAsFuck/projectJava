package ra.edu.business.model.enrollment;

import java.time.LocalDateTime;

public class Enrollment{
    private int id;
    private int cId;
    private int sId;
    private EnrollmentStatus status;
    private LocalDateTime registeredAt;

    public Enrollment(int id, int cId, int sId, EnrollmentStatus status, LocalDateTime registeredAt){
        this.id = id;
        this.cId = cId;
        this.sId = sId;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    public Enrollment(){
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getcId(){
        return cId;
    }

    public void setcId(int cId){
        this.cId = cId;
    }

    public int getsId(){
        return sId;
    }

    public void setsId(int sId){
        this.sId = sId;
    }

    public EnrollmentStatus getStatus(){
        return status;
    }

    public void setStatus(EnrollmentStatus status){
        this.status = status;
    }

    public LocalDateTime getRegisteredAt(){
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt){
        this.registeredAt = registeredAt;
    }

}
