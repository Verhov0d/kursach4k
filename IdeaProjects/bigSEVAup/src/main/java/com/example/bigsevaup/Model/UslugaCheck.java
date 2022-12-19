package com.example.bigsevaup.Model;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UslugaCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Check check2;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Usluga usluga;

    public UslugaCheck() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Check getCheck2() {
        return check2;
    }

    public void setCheck2(Check check2) {
        this.check2 = check2;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
    }

}
