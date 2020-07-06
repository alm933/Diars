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

import com.example.minimarket2.entity.Producto;
import com.example.minimarket2.entity.Proveedor;
import com.example.minimarket2.service.ProductoService;
import com.example.minimarket2.service.ProveedorService;



@RestController
@RequestMapping("/api/proveedores")
public class ProveedorRestController2 {
	@Autowired
	private ProveedorService proveedorService;
	
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Proveedor> > fetchProveedores(){
		try {
			List<Proveedor> proveedores = proveedorService.findAll();
			return new ResponseEntity<List<Proveedor>>(proveedores,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Proveedor>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Proveedor> saveProveedor(@RequestBody Proveedor proveedor){
		try {
			Proveedor newProveedor = proveedorService.save(proveedor);
			return new ResponseEntity<Proveedor>(newProveedor,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Proveedor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,	
								produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity< Proveedor > updateProveedor(@PathVariable ("id") Integer id,
			@RequestBody Proveedor proveedor){
		
		try {
			if(id.equals(proveedor.getId())) {
				Optional<Proveedor> optional = proveedorService.findById(id);
					if(optional.isPresent()) {
						Proveedor updateProveedor = proveedorService.update(proveedor);
						return new ResponseEntity<Proveedor>(updateProveedor, HttpStatus.OK);
					}else {
						return new ResponseEntity<Proveedor>(HttpStatus.NOT_FOUND);
					}
			}else {
				return new ResponseEntity<Proveedor>(HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Proveedor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity< Proveedor > deleteProveedor(@PathVariable ("id") Integer id){
		try {
			Optional<Proveedor> optional = proveedorService.findById(id);
			if(optional.isPresent()) {
				proveedorService.deleteById(id);
				return new ResponseEntity<Proveedor>(HttpStatus.OK);
	}else {
		return new ResponseEntity<Proveedor>(HttpStatus.NOT_FOUND);
		}
	} catch (Exception e) {
		return new ResponseEntity<Proveedor>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
	
	
	
}
