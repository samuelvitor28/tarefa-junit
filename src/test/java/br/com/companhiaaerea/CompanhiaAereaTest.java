package br.com.companhiaaerea;

import org.example.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanhiaAereaTest {

    private Voo criarVoo(int capacidade) {
        return new Voo("LA3042","Belo Horizonte","São Paulo",capacidade,LocalDate.of(2026, 10, 10),new BigDecimal("100.00"));
    }

    private Passageiro passageiroPadrao() {
        return new Passageiro("João","111",CategoriaCliente.PADRAO);
    }

    private Passageiro passageiroVip() {
        return new Passageiro("Maria","222",CategoriaCliente.VIP);
    }

    @Test
    void deveCadastrarVoo() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        assertEquals(1, companhia.listarVoos().size());
    }

    @Test
    void naoDeveCadastrarVooDuplicadoNaMesmaData() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        assertThrows(VooJaCadastradoException.class,() -> companhia.cadastrarVoo(criarVoo(20)));
    }

    @Test
    void devePermitirMesmoCodigoEmDataDiferente() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        Voo outroVoo = new Voo("LA3042","Belo Horizonte","São Paulo",10,LocalDate.of(2026, 10, 11),new BigDecimal("100"));
        companhia.cadastrarVoo(outroVoo);
        assertEquals(2, companhia.listarVoos().size());
    }

    @Test
    void deveListarTodosOsVoos() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        Voo outroVoo = new Voo("LA3043","São Paulo","Rio de Janeiro",10,LocalDate.of(2026, 10, 10),new BigDecimal("200"));
        companhia.cadastrarVoo(outroVoo);
        assertEquals(2, companhia.listarVoos().size());
    }

    @Test
    void deveBuscarVooPorCodigoEData() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        Voo voo = companhia.buscarVoo("LA3042",LocalDate.of(2026, 10, 10));
        assertEquals("LA3042", voo.getCodigo());
    }

    @Test
    void deveLancarExcecaoQuandoVooNaoForEncontrado() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        assertThrows(VooNaoEncontradoException.class,() -> companhia.buscarVoo("LA9999",LocalDate.of(2026, 10, 10)));
    }

    @Test
    void deveBuscarVoosPorOrigemDestinoEData() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        List<Voo> resultado = companhia.buscarVoos("Belo Horizonte","São Paulo",LocalDate.of(2026, 10, 10));
        assertEquals(1, resultado.size());
        assertEquals("LA3042", resultado.get(0).getCodigo());
    }

    @Test
    void deveFazerReservaParaPassageiroPadrao() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        BigDecimal valor = companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroPadrao());
        assertEquals(new BigDecimal("100.00"),valor);
    }

    @Test
    void deveAplicarDescontoParaPassageiroVip() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        BigDecimal valor = companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroVip());
        assertEquals(new BigDecimal("85.00"),valor);
    }

    @Test
    void deveArredondarDescontoParaDuasCasas() {
        Voo voo = new Voo("LA3042","BH","SP",10,LocalDate.of(2026, 10, 10),new BigDecimal("99.99"));
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(voo);
        BigDecimal valor = companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroVip());
        assertEquals(new BigDecimal("84.99"),valor);
    }

    @Test
    void devePermitirReservaAbaixoDaCapacidade() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(3));
        companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroPadrao());
        assertEquals(1,companhia.buscarVoo("LA3042",LocalDate.of(2026, 10, 10)).getQuantidadeReservas());
    }

    @Test
    void devePermitirReservaExatamenteNaCapacidade() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(2));
        companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroPadrao());
        companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroVip());
        assertEquals(2,companhia.buscarVoo("LA3042",LocalDate.of(2026, 10, 10)).getQuantidadeReservas());
    }

    @Test
    void naoDevePermitirReservaAcimaDaCapacidade() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(1));
        companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroPadrao());
        assertThrows(VooLotadoException.class,() -> companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroVip()));
    }

    @Test
    void deveCancelarVooSemReservas() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        companhia.cancelarVoo("LA3042",LocalDate.of(2026, 10, 10));
        assertEquals(0, companhia.listarVoos().size());
    }

    @Test
    void naoDeveCancelarVooComReservas() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroPadrao());
        assertThrows(VooComReservasException.class,() -> companhia.cancelarVoo("LA3042",LocalDate.of(2026, 10, 10)));
    }

    @Test
    void deveEditarVooSemReservas() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        companhia.editarVoo("LA3042",LocalDate.of(2026, 10, 10),"Rio de Janeiro","Salvador",20,new BigDecimal("300"));
        Voo voo = companhia.buscarVoo("LA3042",LocalDate.of(2026, 10, 10));
        assertEquals("Rio de Janeiro", voo.getOrigem());
        assertEquals("Salvador", voo.getDestino());
        assertEquals(20, voo.getCapacidadeMaxima());
    }

    @Test
    void naoDeveEditarVooComReservas() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),passageiroPadrao());
        assertThrows(VooComReservasException.class,() -> companhia.editarVoo("LA3042",LocalDate.of(2026, 10, 10),"Rio","Salvador",20,new BigDecimal("300")));
    }

    @Test
    void deveAceitarPrecoZero() {
        Voo voo = new Voo("LA3042","BH","SP",10,LocalDate.of(2026, 10, 10),BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO,voo.getPrecoBase());
    }

    @Test
    void naoDevePermitirReservaComPassageiroNulo() {
        CompanhiaAerea companhia = new CompanhiaAerea();
        companhia.cadastrarVoo(criarVoo(10));
        assertThrows(PassageiroInvalidoException.class,() -> companhia.reservar("LA3042",LocalDate.of(2026, 10, 10),null));
    }
}