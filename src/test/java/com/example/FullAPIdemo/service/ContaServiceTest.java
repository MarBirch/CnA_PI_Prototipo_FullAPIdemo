package com.example.FullAPIdemo.service;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import com.example.FullAPIdemo.model.entity.*;
import com.example.FullAPIdemo.model.dto.web.EnderecoRequest;
import com.example.FullAPIdemo.repository.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class ContaServiceTest {
 @Test void companyAddressUsesCallerAccountAndSynchronizesLinkedLead(){var accounts=mock(MarmiteriaRepository.class);var leads=mock(LeadComercialRepository.class);Marmiteria m=new Marmiteria();m.setId(7L);m.setSenha("unchanged");LeadComercial l=new LeadComercial();when(accounts.findById(7L)).thenReturn(Optional.of(m));when(leads.findByMarmiteriaId(7L)).thenReturn(Optional.of(l));var result=new ContaService(accounts,leads).atualizarEndereco(7L,new EnderecoRequest("01310-100","42","Paulista","Bela Vista","Sao Paulo","sp"));assertEquals(7L,result.marmiteriaId());assertEquals("01310100",result.cep());assertEquals("Paulista",l.getRua());assertEquals("42",l.getNumero());assertEquals("SP",l.getUf());assertEquals("unchanged",m.getSenha());verify(accounts).save(m);verify(leads).save(l);verify(accounts,never()).findById(8L);}
}
