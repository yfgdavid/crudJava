package br.com.crud.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bd")
public class CrudController {

    @Autowired
    private CrudRepository repository;

    @PostMapping("/criar")
    public CrudModel criar(@RequestBody CrudModel usuario) {
        return repository.save(usuario);
    }

    @GetMapping("/listar")
    public List<CrudModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/pegar/{id}")
    public CrudModel pegar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/editar/{id}")
public ResponseEntity<CrudModel> editar(@PathVariable Long id, @RequestBody CrudModel dadosAtualizados) {
    return repository.findById(id)
        .map(usuarioExistente -> {
            usuarioExistente.setNome(dadosAtualizados.getNome());
            usuarioExistente.setEmail(dadosAtualizados.getEmail());
            CrudModel atualizado = repository.save(usuarioExistente);
            return ResponseEntity.ok(atualizado);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
}

    @DeleteMapping("/delete/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
