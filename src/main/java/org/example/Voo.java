package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Voo {

    private String codigo;
    private String origem;
    private String destino;
    private int capacidadeMaxima;
    private LocalDate data;
    private BigDecimal precoBase;
    private List<Reserva> reservas;

    public Voo(String codigo, String origem, String destino, int capacidadeMaxima, LocalDate data, BigDecimal precoBase) {
        validarCodigo(codigo);
        validarTexto(origem, "Origem");
        validarTexto(destino, "Destino");

        if (capacidadeMaxima <= 0) {
            throw new VooVazioException("A capacidade deve ser maior que zero.");
        }

        if (data == null) {
            throw new DataInvalidaException("A data do voo não pode ser nula.");
        }

        if (precoBase == null || precoBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new PrecoInvalidoException("O preço base não pode ser nulo ou negativo.");
        }

        this.codigo = codigo;
        this.origem = origem;
        this.destino = destino;
        this.capacidadeMaxima = capacidadeMaxima;
        this.data = data;
        this.precoBase = precoBase;
        this.reservas = new ArrayList<>();
    }

    private void validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new CodigoInvalidoException("Código do voo não pode ser nulo ou vazio.");
        }

        if (!Pattern.matches("[A-Z]{2}[0-9]{4}", codigo)) {
            throw new CodigoInvalidoException("Código do voo deve possuir duas letras maiúsculas seguidas de quatro números.");
        }
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " não pode ser nulo ou vazio.");
        }
    }

    public void adicionarReserva(Reserva reserva) {
        if (reservas.size() >= capacidadeMaxima) {
            throw new VooLotadoException("Não é possível realizar a reserva. O voo está lotado.");
        }
        reservas.add(reserva);
    }

    public boolean possuiReservas() {
        return !reservas.isEmpty();
    }

    public int getQuantidadeReservas() {
        return reservas.size();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public LocalDate getData() {
        return data;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public List<Reserva> getReservas() {
        return List.copyOf(reservas);
    }

    public void editar(String origem, String destino, int capacidadeMaxima,BigDecimal precoBase) {
        if (possuiReservas()) {
            throw new VooComReservasException("Não é possível editar um voo que possui reservas.");
        }
        validarTexto(origem, "Origem");
        validarTexto(destino, "Destino");
        if (capacidadeMaxima <= 0) {
            throw new VooVazioException("A capacidade deve ser maior que zero.");
        }
        if (precoBase == null || precoBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço base não pode ser nulo ou negativo.");
        }
        this.origem = origem;
        this.destino = destino;
        this.capacidadeMaxima = capacidadeMaxima;
        this.precoBase = precoBase;
    }
}
