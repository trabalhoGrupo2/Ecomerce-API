package org.serratec.h2.grupo2.Conta;

import org.serratec.h2.grupo2.Conta.domain.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
