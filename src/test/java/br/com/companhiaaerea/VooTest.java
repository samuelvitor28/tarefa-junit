package br.com.companhiaaerea;

import org.example.Voo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class VooTest {

    private Voo criarVoo() {
        return new Voo("LA3042","Belo Horizonte","São Paulo",100,LocalDate.of(2026, 10, 10),new BigDecimal("500.00"));
    }

    @Test
    void deveCriarVooComDadosValidos() {
        Voo voo = criarVoo();
        assertEquals("LA3042", voo.getCodigo());
        assertEquals("Belo Horizonte", voo.getOrigem());
        assertEquals("São Paulo", voo.getDestino());
        assertEquals(100, voo.getCapacidadeMaxima());
        assertEquals(new BigDecimal("500.00"), voo.getPrecoBase());
    }

    @Test
    void naoDeveAceitarCodigoNulo() {
        assertThrows(IllegalArgumentException.class,() -> new Voo(null,"BH","SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarCodigoVazio() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("","BH","SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarCodigoComFormatoInvalido() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("la3042","BH","SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarCodigoComQuantidadeErradaDeNumeros() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA304","BH","SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarCodigoComQuantidadeErradaDeLetras() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("L3042","BH","SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarOrigemNula() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042",null,"SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarOrigemVazia() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","","SP",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarDestinoNulo() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","BH",null,100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarDestinoVazio() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","BH","",100,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarCapacidadeZero() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","BH","SP",0, LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarCapacidadeNegativa() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","BH","SP",-10,LocalDate.now(),new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarDataNula() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","BH","SP",100,null,new BigDecimal("500")));
    }

    @Test
    void naoDeveAceitarPrecoNegativo() {
        assertThrows(IllegalArgumentException.class,() -> new Voo("LA3042","BH","SP",100,LocalDate.now(),new BigDecimal("-1")));
    }
}
