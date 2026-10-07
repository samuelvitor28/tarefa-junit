package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompanhiaAerea {

    private List<Voo> voos;

    public CompanhiaAerea() {
        this.voos = new ArrayList<>();
    }

    public void cadastrarVoo(Voo voo) {
        if (voo == null) {
            throw new VooInvalidoException("O voo não pode ser nulo.");
        }
        boolean existe = voos.stream().anyMatch(v ->v.getCodigo().equals(voo.getCodigo()) && v.getData().equals(voo.getData()));
        if (existe) {
            throw new VooJaCadastradoException("Já existe um voo com esse código nessa data.");
        }
        voos.add(voo);
    }

    public List<Voo> listarVoos() {
        return List.copyOf(voos);
    }

    public Voo buscarVoo(String codigo, LocalDate data) {
        return voos.stream().filter(v ->v.getCodigo().equals(codigo) && v.getData().equals(data)).findFirst().orElseThrow(() ->new VooNaoEncontradoException("Voo não encontrado."));
    }

    public List<Voo> buscarVoos(String origem, String destino, LocalDate data) {
        return voos.stream().filter(v -> v.getOrigem().equalsIgnoreCase(origem)&& v.getDestino().equalsIgnoreCase(destino) && v.getData().equals(data)).toList();
    }

    public BigDecimal reservar(String codigo, LocalDate data, Passageiro passageiro){
        Voo voo = buscarVoo(codigo, data);
        if (passageiro == null) {
            throw new PassageiroInvalidoException("O passageiro não pode ser nulo.");
        }
        BigDecimal valorFinal = calcularPreco(voo, passageiro);
        Reserva reserva = new Reserva(passageiro, valorFinal);
        voo.adicionarReserva(reserva);
        return valorFinal;
    }

    private BigDecimal calcularPreco(Voo voo, Passageiro passageiro) {
        BigDecimal preco = voo.getPrecoBase();
        if (passageiro.getCategoria() == CategoriaCliente.VIP) {
            BigDecimal desconto = preco.multiply(new BigDecimal("0.15"));
            preco = preco.subtract(desconto);
        }
        return preco.setScale(2, RoundingMode.HALF_UP);
    }

    public void cancelarVoo(String codigo,LocalDate data) {
        Voo voo = buscarVoo(codigo, data);
        if (voo.possuiReservas()) {
            throw new VooComReservasException("Não é possível cancelar um voo que possui reservas.");
        }
        voos.remove(voo);
    }

    public void editarVoo(String codigo, LocalDate data, String origem, String destino, int capacidadeMaxima, BigDecimal precoBase) {
        Voo voo = buscarVoo(codigo, data);
        voo.editar(origem,destino, capacidadeMaxima, precoBase);
    }
}
