package org.serratec.h2.grupo2.funcionario;

import org.serratec.h2.grupo2.funcionario.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
