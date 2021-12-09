

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Animal implements Comparable<Animal> {

    @Min(1)
    private int Id;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]")
    private String Type;
    @NotNull
    @Pattern(regexp = "[A-Z][a-z]")
    private String Name;
    @NotNull
    private boolean Sex;
    @Min(0)
    private int Age;

    public Animal() {
    }

    @Override
    public int compareTo(Animal o) {
        return Name.compareTo(o.Name);
    }

    public static class Builder  implements  Serializable{


        private Animal Animal;
        private boolean addSex;

        public Builder() {
            Animal = new Animal();
            addSex = false;
        }
        public Builder addId(){
            Random random = new Random();
            Animal.Id = random.nextInt();
            return this;
        }
        public Builder addId(int id){
            Animal.Id = id;
            return this;
        }
        public Builder addType(String typeAnimal){
            Animal.Type = typeAnimal;
            return this;
        }
        public Builder addName(String name){
            Animal.Name = name;
            return this;
        }
        /**
         * 1 - man, 0 - woman
         * @param sex
         * @return
         */
        public Builder addSex(boolean sex){
            Animal.Sex = sex;
            addSex = true;
            return this;
        }
        public Builder addAge(int age){
            Animal.Age = age;
            return this;
        }
        public Animal build(){
            validate(addId().Animal);
            return Animal;
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
        Animal animal = (Animal) o;
        return Sex == animal.Sex && Age == animal.Age && Objects.equals(Id, animal.Id) && Objects.equals(Type, animal.Type) && Objects.equals(Name, animal.Name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Type, Name, Sex, Age);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "Id=" + Id +
                ", Type='" + Type + '\'' +
                ", Name='" + Name + '\'' +
                ", Sex=" + Sex +
                ", Age=" + Age +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSex() {
        return Sex;
    }

    public void setSex(boolean sex) {
        Sex = sex;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }
}
