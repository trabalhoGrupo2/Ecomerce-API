package org.serratec.h2.grupo2.categoria;

import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaDTO toDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        return new CategoriaDTO(categoria.getId(), categoria.getNome());
    }
    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setNome(categoriaDTO.getNome());
        return categoria;
    }
}