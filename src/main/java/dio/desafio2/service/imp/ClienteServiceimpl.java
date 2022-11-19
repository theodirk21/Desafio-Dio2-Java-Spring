package dio.desafio2.service.imp;

import dio.desafio2.model.Cliente;
import dio.desafio2.model.ClienteRepository;
import dio.desafio2.model.Endereco;
import dio.desafio2.model.EnderecoRepository;
import dio.desafio2.service.ClienteService;
import dio.desafio2.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ClienteServiceimpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        SalvarClienteComCEP(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente){
            Optional<Cliente> clientebd = clienteRepository.findById(id);
            if (clientebd.isPresent()) {
                SalvarClienteComCEP(cliente);
            }
        }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void SalvarClienteComCEP(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
