package dio.desafio2.service;

import dio.desafio2.model.Cliente;

public interface ClienteService {

    Iterable<Cliente> buscarTodos();

    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar (Long id, Cliente cliente);

    void deletar (Long id);
}
