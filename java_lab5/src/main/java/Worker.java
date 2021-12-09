

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Worker implements Serializable {
    @Min(1)
    private int Id;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]")
    private String Name;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]")
    private String Surname;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]")
    private String Position;

    public Worker(){
        Name ="";
        Surname = "";
        Position = "";
    }

    public static class Builder{
        private Worker Worker;

        public Builder() {
            Worker = new Worker();
        }

        public Builder addId(){
            Random random = new Random();
            Worker.Id = random.nextInt();
            return this;
        }
        public Builder addId(int id){
            Worker.Id = id;
            return this;
        }
        public Builder addName(String name){
            Worker.Name = name;
            return this;
        }
        public Builder addSurname(String surname){
            Worker.Surname = surname;
            return this;
        }
        public Builder addPosition(String position){
            Worker.Position = position;
            return this;
        }
        public Worker build(){
            validate(Worker);
            return Worker;
        }
    }
    private static <T> void validate(T val) throws IllegalArgumentException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<T>> check =  validator.validate(val);
        StringBuilder stringBuilder = new StringBuilder();

        for (ConstraintViolation<T> c : check) {
            stringBuilder.append("Error").append(c.getInvalidValue()).append(" because ").append(c.getMessage()).append("\n");
        }
        if(stringBuilder.length()>0){
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(Id, worker.Id) && Objects.equals(Name, worker.Name) && Objects.equals(Surname, worker.Surname) && Objects.equals(Position, worker.Position);
    }


    @Override
    public int hashCode() {
        return Objects.hash(Id, Name, Surname, Position);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Position='" + Position + '\'' +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }
}
