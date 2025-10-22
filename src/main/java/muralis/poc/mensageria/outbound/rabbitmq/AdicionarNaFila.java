package muralis.poc.mensageria.outbound.rabbitmq;

public interface AdicionarNaFila {

    void adicionarNaFila(Object object, String exchange, String routingKey);

}
