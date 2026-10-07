package org.example;

import java.math.BigDecimal;

public class Reserva {

    private Passageiro passageiro;
    private BigDecimal valorPago;

    public Reserva(Passageiro passageiro, BigDecimal valorPago) {
        this.passageiro = passageiro;
        this.valorPago = valorPago;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }
}
