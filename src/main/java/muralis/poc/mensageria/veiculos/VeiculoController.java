package muralis.poc.mensageria.veiculos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoSender sender;

    @PostMapping("/batch")
    public ResponseEntity salvarVeiculos(@RequestBody List<Veiculo> request) {
        sender.send(request);

        return ResponseEntity.ok().build();
    }

}
