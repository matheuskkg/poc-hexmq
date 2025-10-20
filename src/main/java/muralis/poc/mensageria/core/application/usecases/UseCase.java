package muralis.poc.mensageria.core.application.usecases;

public interface UseCase <R, T> {

    R execute(T entidade);

}
