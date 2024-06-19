package com.example.demo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    private Long id;
    @NotBlank ( message = "tên sản phẩm không được để trống !!!")
    private String name;
    @Length(min =0,max =255, message="Tên hình ảnh không quá 255 ký tự")
    private String image;


    @NotNull( message = (" Gia san pham khong duoc de trong"))
    @Min( value =1, message ="Gia san pham khong duoc nho hon 1")
    @Max(value = 9999999, message = "GIa san pham khong duoc lon hon 999999999")
    private long price;


    @Min(value = 0, message = "Số lượng sản phẩm không được nhỏ hơn 0")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


}
