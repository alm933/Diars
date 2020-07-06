package com.example.minimarket2.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minimarket2.entity.Categoria;

import com.example.minimarket2.service.CategoriaService;




@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {
	@Autowired
	private CategoriaService categoriaService;
	
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Categoria> > fetchCategorias(){
		try {
			List<Categoria> categorias = categoriaService.findAll();
			return new ResponseEntity<List<Categoria>>(categorias,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Categoria>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria){
		try {
			Categoria newCategoria = categoriaService.save(categoria);
			return new ResponseEntity<Categoria>(newCategoria,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Categoria>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,	
								produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Categoria > updateCategoria(@PathVariable ("id") String id,
			@RequestBody Categoria categoria){
		
		try {
			if(id.equals(categoria.getId())) {
				Optional<Categoria> optional = categoriaService.findById(id);
					if(optional.isPresent()) {
						Categoria updateCategoria = categoriaService.update(categoria);
						return new ResponseEntity<Categoria>(updateCategoria, HttpStatus.OK);
					}else {
						return new ResponseEntity<Categoria>(HttpStatus.NOT_FOUND);
					}
			}else {
				return new ResponseEntity<Categoria>(HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Categoria>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity< Categoria > deleteCategoria(@PathVariable ("id") String id){
		try {
			Optional<Categoria> optional = categoriaService.findById(id);
			if(optional.isPresent()) {
				categoriaService.deleteById(id);
				return new ResponseEntity<Categoria>(HttpStatus.OK);
	}else {
		return new ResponseEntity<Categoria>(HttpStatus.NOT_FOUND);
		}
	} catch (Exception e) {
		return new ResponseEntity<Categoria>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
	
	
	
}
