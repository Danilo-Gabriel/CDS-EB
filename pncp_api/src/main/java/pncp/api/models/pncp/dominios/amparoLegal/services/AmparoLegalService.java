package pncp.api.models.pncp.dominios.amparoLegal.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import pncp.api.models.pncp.dominios.amparoLegal.dto.DadosAmparoLegalDTO;
import pncp.api.models.pncp.dominios.amparoLegal.dto.ListarAmparoLegalDTO;
import pncp.api.models.pncp.dominios.amparoLegal.repository.AmparaLegalRepository;
import pncp.api.models.pncp.dominios.amparoLegal.repository.AmparoLegal;

@Service
public class AmparoLegalService {
    
    @Autowired
    private AmparaLegalRepository repository;

    @Autowired
    private AppConfig restTamplate;

    //@Value("")
    String apiUrl = "https://treina.pncp.gov.br/api/pncp/v1/amparos-legais";




     public void recuperarDadosPncpClassService(Boolean statusAtivo){
          
            String url = apiUrl + "?statusAtivo=" + statusAtivo;
            
            ResponseEntity<DadosAmparoLegalDTO[]> resposta = restTamplate.restTemplate().getForEntity(url, DadosAmparoLegalDTO[].class);

            List<DadosAmparoLegalDTO> dados = Arrays.asList(resposta.getBody());
           
		if(dados.isEmpty()){
			System.out.println("Nenhum dado encontrado");
		}
		else{

			for (DadosAmparoLegalDTO lista : dados){
     
				this.registrarAmparosLegaisClassService(lista);
			
			}
			
			System.out.println("Dados Recuperado");

			
		}
		
      
    }

    public void registrarAmparosLegaisClassService(DadosAmparoLegalDTO dados) {


         AmparoLegal newDados = new AmparoLegal(dados);
         this.repository.save(newDados);
 
     }

     public List<ListarAmparoLegalDTO> listarAmparoLegaisClassService(){

        return repository.findAll().stream().map(ListarAmparoLegalDTO::new).toList();
     }

    

}
