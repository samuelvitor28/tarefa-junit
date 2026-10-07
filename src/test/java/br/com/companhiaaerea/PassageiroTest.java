package br.com.companhiaaerea;

import org.example.CategoriaCliente;
import org.example.Passageiro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassageiroTest {

    @Test
    void deveCriarPassageiroValido() {
        Passageiro passageiro = new Passageiro("Samuel","12345678900",CategoriaCliente.PADRAO);
        assertEquals("Samuel", passageiro.getNome());
        assertEquals("12345678900", passageiro.getDocumento());
        assertEquals(CategoriaCliente.PADRAO,passageiro.getCategoria());
    }

    @Test
    void naoDeveAceitarNomeNulo() {
        assertThrows(IllegalArgumentException.class,() -> new Passageiro(null,"123",CategoriaCliente.PADRAO));
    }

    @Test
    void naoDeveAceitarNomeVazio() {
        assertThrows(IllegalArgumentException.class,() -> new Passageiro("","123",CategoriaCliente.PADRAO));
    }

    @Test
    void naoDeveAceitarDocumentoNulo() {
        assertThrows(IllegalArgumentException.class,() -> new Passageiro("Samuel",null,CategoriaCliente.PADRAO));
    }

    @Test
    void naoDeveAceitarDocumentoVazio() {
        assertThrows(IllegalArgumentException.class,() -> new Passageiro("Samuel","",CategoriaCliente.PADRAO));
    }

    @Test
    void naoDeveAceitarCategoriaNula() {
        assertThrows( IllegalArgumentException.class,() -> new Passageiro("Samuel","123",null));
    }
}
