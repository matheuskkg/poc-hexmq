package muralis.poc.mensageria.veiculos;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "veiculos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "placa")
})
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String categoria;

    @CreatedDate
    @Column(name = "criado_em")
    private LocalDate dataCriacao;

    public Veiculo() {
    }

    public Veiculo(Long id, String placa, String modelo, String categoria, LocalDate dataCriacao) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
