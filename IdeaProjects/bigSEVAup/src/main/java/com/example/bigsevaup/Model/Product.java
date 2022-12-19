package com.example.bigsevaup.Model;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Поле должно содержать от 1 до 50 символов")
    private String nameproduct;

    @NotNull(message = "Поле не может быть пустым")
    @Min(value = 1, message ="Стоимость не может быть меньше 0" )
    private Integer costproduct;

    @ManyToOne(fetch = FetchType.LAZY)
    private Postavshik postavshik;

    @ManyToOne(fetch = FetchType.LAZY)
    private KategoryProduct kategoryProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    private Proizvoditel proizvoditel;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<ProductCheck> lstproduct = new ArrayList<>();

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public Integer getCostproduct() {
        return costproduct;
    }

    public void setCostproduct(Integer costproduct) {
        this.costproduct = costproduct;
    }

    public Postavshik getPostavshik() {
        return postavshik;
    }

    public void setPostavshik(Postavshik postavshik) {
        this.postavshik = postavshik;
    }

    public KategoryProduct getKategoryProduct() {
        return kategoryProduct;
    }

    public void setKategoryProduct(KategoryProduct kategoryProduct) {
        this.kategoryProduct = kategoryProduct;
    }

    public Proizvoditel getProizvoditel() {
        return proizvoditel;
    }

    public void setProizvoditel(Proizvoditel proizvoditel) {
        this.proizvoditel = proizvoditel;
    }

    public List<ProductCheck> getLstproduct() {
        return lstproduct;
    }

    public void setLstproduct(List<ProductCheck> lstproduct) {
        this.lstproduct = lstproduct;
    }
    public String getnazvprod() {return kategoryProduct.getNazvprod();}
}
