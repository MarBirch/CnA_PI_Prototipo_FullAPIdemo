package com.example.FullAPIdemo.service;
import com.example.FullAPIdemo.model.dto.web.ContaResponse;
import com.example.FullAPIdemo.model.entity.LeadComercial;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.LeadComercialRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class ContaService {
 private final MarmiteriaRepository marmiterias; private final LeadComercialRepository leads;
 public ContaService(MarmiteriaRepository marmiterias,LeadComercialRepository leads){this.marmiterias=marmiterias;this.leads=leads;}
 @org.springframework.transaction.annotation.Transactional
 public ContaResponse atualizarEndereco(Long id,com.example.FullAPIdemo.model.dto.web.EnderecoRequest r){
  Marmiteria m=marmiterias.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Conta nao encontrada."));
  m.setCep(r.cep()==null?null:r.cep().replaceAll("\\D",""));m.setNumero(r.numero());m.setRua(r.rua());m.setBairro(r.bairro());m.setCidade(r.cidade());m.setUf(r.uf()==null?null:r.uf().toUpperCase(java.util.Locale.ROOT));
  marmiterias.save(m);
  leads.findByMarmiteriaId(id).ifPresent(l->{l.setCep(m.getCep());l.setNumero(m.getNumero());l.setRua(m.getRua());l.setBairro(m.getBairro());l.setCidade(m.getCidade());l.setUf(m.getUf());leads.save(l);});
  return minhaConta(id);
 }
 /** Account identity comes exclusively from the authenticated token. */
 public ContaResponse minhaConta(Long marmiteriaId){
  Marmiteria m=marmiterias.findById(marmiteriaId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Conta nao encontrada."));
  LeadComercial l=leads.findByMarmiteriaId(marmiteriaId).orElse(null);
  return new ContaResponse(m.getId(),m.getNome(),m.getEmail(),m.getTelefone(),m.getCnpj(),m.getCep(),m.getRua(),m.getNumero(),m.getBairro(),m.getCidade(),m.getUf(),l!=null?l.getResponsavel():null,l!=null?l.getStatusComercial().name():null,l!=null?l.getStatusPagamento().name():null,m.getStatusAcesso()!=null?m.getStatusAcesso().name():"ATIVO");
 }
}
