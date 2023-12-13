package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import com.example.cardapio.food.FoodResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FoodResponseDTO> getAll(){

        List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
        return foodList;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) {
        try {
            // Verifique se o produto com o ID fornecido existe
            if (repository.existsById(id)) {
                // Se existir, exclua o produto
                repository.deleteById(id);
                return new ResponseEntity<>("Produto excluído com sucesso", HttpStatus.OK);
            } else {
                // Se não existir, retorne um status 404 (Não Encontrado)
                return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Se ocorrer algum erro durante a exclusão, retorne um status 500 (Erro Interno do Servidor)
            return new ResponseEntity<>("Erro ao excluir o produto", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
