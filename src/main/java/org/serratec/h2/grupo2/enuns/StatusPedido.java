package org.serratec.h2.grupo2.enuns;

public enum StatusPedido {
	AGUARDANDO_PAGAMENTO,
	PAGO,
	ENVIADO,
	ENTREGUE,
	CANCELADO,
	DEVOLUCAO;



public static StatusPedido statusPeloNome(String nome) {
    for (StatusPedido status : StatusPedido.values()) {
        if (status.name().equalsIgnoreCase(nome)) { // Ignora maiúsculas/minúsculas
            return status;
        }
    }
    throw new IllegalArgumentException("Digite um enum válido!");
 }
}