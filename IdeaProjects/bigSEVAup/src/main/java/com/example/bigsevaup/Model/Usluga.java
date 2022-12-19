package com.example.bigsevaup.Model;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Usluga {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Поле должно содержать от 1 до 50 символов")
    private String nameusluga;

    @NotNull(message = "Поле не может быть пустым")
    @Min(value = 0, message ="Цена не может быть ниже 0" )
    private Integer costusluga;

    @OneToMany(mappedBy = "usluga", orphanRemoval = true)
    private List<UslugaCheck> lstusluga = new ArrayList<>();

    public Usluga() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameusluga() {
        return nameusluga;
    }

    public void setNameusluga(String nameusluga) {
        this.nameusluga = nameusluga;
    }

    public Integer getCostusluga() {
        return costusluga;
    }

    public void setCostusluga(Integer costusluga) {
        this.costusluga = costusluga;
    }

    public List<UslugaCheck> getLstusluga() {
        return lstusluga;
    }

    public void setLstusluga(List<UslugaCheck> lstusluga) {
        this.lstusluga = lstusluga;
    }
}
