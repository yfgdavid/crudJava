package br.com.crud.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bd")
public class CrudController {

    @Autowired
    private CrudRepository repository;

    // Criar novo usuário
    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody CrudModel usuario) {
        try {
            CrudModel criado = repository.save(usuario);
            return ResponseEntity.ok(criado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    // Listar todos os usuários
    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        try {
            List<CrudModel> lista = repository.findAll();
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao listar usuários: " + e.getMessage());
        }
    }

    // Buscar usuário por ID
    @GetMapping("/pegar/{id}")
    public ResponseEntity<?> pegar(@PathVariable Long id) {
        try {
            Optional<CrudModel> usuario = repository.findById(id);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());
            } else {
                return ResponseEntity.status(404).body("Usuário não encontrado com ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar usuário: " + e.getMessage());
        }
    }

@PutMapping("/editar/{id}")
public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody CrudModel dadosAtualizados) {
    Optional<CrudModel> optionalUsuario = repository.findById(id);

    if (optionalUsuario.isPresent()) {
        CrudModel usuario = optionalUsuario.get();
        usuario.setNome(dadosAtualizados.getNome());
        usuario.setEmail(dadosAtualizados.getEmail());
        CrudModel atualizado = repository.save(usuario);
        return ResponseEntity.ok(atualizado);
    } else {
        return ResponseEntity.status(404).body("Usuário com ID " + id + " não encontrado");
    }
}




    // Deletar usuário por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return ResponseEntity.ok("Usuário com ID " + id + " deletado com sucesso.");
            } else {
                return ResponseEntity.status(404).body("Usuário com ID " + id + " não encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
