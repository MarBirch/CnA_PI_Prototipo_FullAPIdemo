package com.example.FullAPIdemo.service;
import com.example.FullAPIdemo.model.StatusAcesso;
import com.example.FullAPIdemo.model.StatusComercial;
import com.example.FullAPIdemo.model.StatusPagamento;
import com.example.FullAPIdemo.model.dto.web.*;
import com.example.FullAPIdemo.model.entity.LeadComercial;
import com.example.FullAPIdemo.model.entity.LeadHistorico;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.LeadComercialRepository;
import com.example.FullAPIdemo.repository.LeadHistoricoRepository;
import com.example.FullAPIdemo.repository.MarmiteriaRepository;
import com.example.FullAPIdemo.security.PasswordService;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
@Service
public class LeadService {
 private static final Logger log=LoggerFactory.getLogger(LeadService.class);
 private static final String ALFABETO="ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
 private final SecureRandom random=new SecureRandom();
 private final LeadComercialRepository leads; private final LeadHistoricoRepository historicos;
 private final MarmiteriaRepository marmiterias; private final PasswordService passwords;
 private final EmailService emailService; private final String emailDestino;
 public LeadService(LeadComercialRepository leads,LeadHistoricoRepository historicos,MarmiteriaRepository marmiterias,PasswordService passwords,EmailService emailService,@Value("${app.comercial.email-destino}") String emailDestino){this.leads=leads;this.historicos=historicos;this.marmiterias=marmiterias;this.passwords=passwords;this.emailService=emailService;this.emailDestino=emailDestino;}
 @Transactional public Map<String,Object> registrar(LeadRequest r){
  String email=r.email().trim().toLowerCase();
  LeadComercial l=leads.findFirstByEmailIgnoreCaseOrderByCreatedAtDesc(email).filter(x->x.getStatusComercial()!=StatusComercial.PERDIDO&&x.getStatusComercial()!=StatusComercial.CANCELADO).orElseGet(LeadComercial::new);
  if(l.getMarmiteria()!=null||marmiterias.findFirstByEmailIgnoreCase(email).isPresent())throw new ResponseStatusException(HttpStatus.CONFLICT,"Esta empresa ja possui cadastro. Entre na sua conta ou contate a equipe.");
  boolean novo=l.getId()==null;
  l.setNomeLoja(r.nomeLoja().trim());l.setCnpj(blankToNull(r.cnpj()));l.setEmail(email);l.setTelefone(formatTelefone(r.telefone()));l.setResponsavel(r.responsavel().trim());
  l.setCep(r.cep()==null||r.cep().isBlank()?null:r.cep().replaceAll("\\D",""));l.setNumero(blankToNull(r.numero()));l.setRua(blankToNull(r.rua()));l.setBairro(blankToNull(r.bairro()));l.setCidade(blankToNull(r.cidade()));l.setUf(r.uf()==null?null:blankToNull(r.uf().toUpperCase(Locale.ROOT)));l.setMensagem(blankToNull(r.mensagem()));
  leads.save(l);historicos.save(new LeadHistorico(l,novo?"CADASTRO_RECEBIDO":"CADASTRO_ATUALIZADO","Formulario comercial enviado pelo site.",null));notificar(l);
  return Map.of("id",l.getId(),"statusComercial",l.getStatusComercial().name());
 }
 private void notificar(LeadComercial l){try{String body="Novo cadastro de restaurante:\n\n"+"Nome da Loja: "+l.getNomeLoja()+"\nResponsavel: "+l.getResponsavel()+"\nCNPJ: "+Objects.toString(l.getCnpj(),"-")+"\nEmail: "+l.getEmail()+"\nTelefone: "+l.getTelefone()+"\nCEP: "+Objects.toString(l.getCep(),"-")+"\nNumero do restaurante: "+Objects.toString(l.getNumero(),"-");emailService.sendEmail(emailDestino,"Novo Cadastro de Restaurante",body);}catch(Exception e){log.warn("Falha ao enviar e-mail de notificacao do lead {}",l.getId());}}
 public DashboardResponse dashboard(){List<LeadComercial> todos=leads.findAll();List<EmpresaResponse> recentes=leads.findAll(PageRequest.of(0,5,Sort.by(Sort.Direction.DESC,"createdAt"))).map(l->EmpresaResponse.of(l,List.of())).getContent();return new DashboardResponse(todos.size(),contar(StatusComercial.values(),todos,l->l.getStatusComercial().name()),contar(StatusPagamento.values(),todos,l->l.getStatusPagamento().name()),contar(StatusAcesso.values(),todos,l->l.getStatusAcesso().name()),recentes);}
 private <E extends Enum<E>> Map<String,Long> contar(E[] valores,List<LeadComercial> todos,Function<LeadComercial,String> f){Map<String,Long> m=new LinkedHashMap<>();for(E v:valores)m.put(v.name(),0L);todos.forEach(l->m.merge(f.apply(l),1L,Long::sum));return m;}
 public PageResponse<EmpresaResponse> listar(String busca,String statusComercial,String statusPagamento,String statusAcesso,int pagina,int tamanho){
  StatusComercial sc=parse(StatusComercial.class,statusComercial);StatusPagamento sp=parse(StatusPagamento.class,statusPagamento);StatusAcesso sa=parse(StatusAcesso.class,statusAcesso);String termo=busca==null?null:busca.trim().toLowerCase();
  Specification<LeadComercial> spec=(root,q,cb)->{List<Predicate> ps=new ArrayList<>();if(termo!=null&&!termo.isEmpty()){String like="%"+termo.replace("%","").replace("_","")+"%";ps.add(cb.or(cb.like(cb.lower(root.get("nomeLoja")),like),cb.like(cb.lower(root.get("email")),like),cb.like(cb.lower(root.get("responsavel")),like),cb.like(root.get("cnpj"),like)));}if(sc!=null)ps.add(cb.equal(root.get("statusComercial"),sc));if(sp!=null)ps.add(cb.equal(root.get("statusPagamento"),sp));if(sa!=null)ps.add(cb.equal(root.get("statusAcesso"),sa));return cb.and(ps.toArray(new Predicate[0]));};
  int size=Math.min(Math.max(tamanho,1),100);Page<LeadComercial> page=leads.findAll(spec,PageRequest.of(Math.max(pagina,0),size,Sort.by(Sort.Direction.DESC,"createdAt")));return new PageResponse<>(page.map(l->EmpresaResponse.of(l,List.of())).getContent(),page.getNumber(),page.getSize(),page.getTotalElements(),page.getTotalPages());
 }
 public EmpresaResponse detalhe(Long id){return EmpresaResponse.of(buscar(id),historico(id));}
 @Transactional public EmpresaResponse atualizarComercial(Long id,AtualizarStatusRequest r,String autor){LeadComercial l=buscar(id);StatusComercial novo=parseObrigatorio(StatusComercial.class,r.status());l.setStatusComercial(novo);if(novo==StatusComercial.VENDIDO&&l.getStatusPagamento()==StatusPagamento.NAO_INICIADO)l.setStatusPagamento(StatusPagamento.PENDENTE);if(novo==StatusComercial.PERDIDO||novo==StatusComercial.CANCELADO){if(l.getStatusPagamento()!=StatusPagamento.CONFIRMADO)l.setStatusPagamento(StatusPagamento.CANCELADO);aplicarAcesso(l,StatusAcesso.CANCELADO);}leads.save(l);registrarHistorico(l,"STATUS_COMERCIAL",novo.name(),r.observacao(),autor);return detalhe(id);}
 @Transactional public EmpresaResponse atualizarPagamento(Long id,AtualizarStatusRequest r,String autor){LeadComercial l=buscar(id);StatusPagamento novo=parseObrigatorio(StatusPagamento.class,r.status());if(novo==StatusPagamento.CONFIRMADO&&l.getStatusComercial()!=StatusComercial.VENDIDO)throw new ResponseStatusException(HttpStatus.CONFLICT,"Pagamento so pode ser confirmado apos a venda.");l.setStatusPagamento(novo);leads.save(l);registrarHistorico(l,"STATUS_PAGAMENTO",novo.name(),r.observacao(),autor);return detalhe(id);}
 /** New access requires a sold contract and confirmed payment. Existing legacy access is preserved. */
 @Transactional public LiberacaoResponse atualizarAcesso(Long id,AtualizarStatusRequest r,String autor){LeadComercial l=buscar(id);StatusAcesso novo=parseObrigatorio(StatusAcesso.class,r.status());String senhaProvisoria=null;if(novo==StatusAcesso.ATIVO){if(l.getStatusComercial()!=StatusComercial.VENDIDO||l.getStatusPagamento()!=StatusPagamento.CONFIRMADO)throw new ResponseStatusException(HttpStatus.CONFLICT,"Libere o acesso apenas apos venda e pagamento confirmado.");if(l.getMarmiteria()==null)senhaProvisoria=criarMarmiteria(l);}aplicarAcesso(l,novo);leads.save(l);registrarHistorico(l,"STATUS_ACESSO",novo.name(),r.observacao(),autor);return new LiberacaoResponse(detalhe(id),senhaProvisoria!=null?l.getEmail():null,senhaProvisoria);}
 @Transactional public EmpresaResponse adicionarHistorico(Long id,HistoricoRequest r,String autor){historicos.save(new LeadHistorico(buscar(id),"ANOTACAO",r.descricao().trim(),autor));return detalhe(id);}
 private String criarMarmiteria(LeadComercial l){Optional<Marmiteria> existente=marmiterias.findFirstByEmailIgnoreCase(l.getEmail());if(existente.isPresent()){var vinculada=leads.findByMarmiteriaId(existente.get().getId());if(vinculada.isPresent()&&!Objects.equals(vinculada.get().getId(),l.getId()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Esta conta ja esta vinculada a outra empresa.");l.setMarmiteria(existente.get());return null;}String senha=gerarSenha();Marmiteria m=new Marmiteria();m.setNome(l.getNomeLoja());m.setEmail(l.getEmail());m.setTelefone(l.getTelefone());m.setCnpj(l.getCnpj());m.setCep(l.getCep());m.setNumero(l.getNumero());m.setRua(l.getRua());m.setBairro(l.getBairro());m.setCidade(l.getCidade());m.setUf(l.getUf());m.setStatus(false);m.setSenha(passwords.hash(senha));m.setStatusAcesso(StatusAcesso.ATIVO);marmiterias.save(m);l.setMarmiteria(m);return senha;}
 private String gerarSenha(){StringBuilder sb=new StringBuilder();for(int i=0;i<14;i++)sb.append(ALFABETO.charAt(random.nextInt(ALFABETO.length())));return sb.toString();}
 @Transactional public LiberacaoResponse redefinirSenha(Long id,String autor){LeadComercial l=buscar(id);Marmiteria m=l.getMarmiteria();if(m==null)throw new ResponseStatusException(HttpStatus.CONFLICT,"Libere a conta antes de redefinir a senha.");String senha=gerarSenha();m.setSenha(passwords.hash(senha));marmiterias.save(m);historicos.save(new LeadHistorico(l,"SENHA_REDEFINIDA","Senha provisoria redefinida pelo administrador.",autor));return new LiberacaoResponse(detalhe(id),m.getEmail(),senha);}
 @Transactional public EmpresaResponse atualizarEndereco(Long id,EnderecoRequest r,String autor){LeadComercial l=buscar(id);l.setCep(r.cep()==null?null:blankToNull(r.cep().replaceAll("\\D","")));l.setNumero(blankToNull(r.numero()));l.setRua(blankToNull(r.rua()));l.setBairro(blankToNull(r.bairro()));l.setCidade(blankToNull(r.cidade()));l.setUf(r.uf()==null?null:blankToNull(r.uf().toUpperCase(Locale.ROOT)));if(l.getMarmiteria()!=null){Marmiteria m=l.getMarmiteria();m.setCep(l.getCep());m.setNumero(l.getNumero());m.setRua(l.getRua());m.setBairro(l.getBairro());m.setCidade(l.getCidade());m.setUf(l.getUf());marmiterias.save(m);}leads.save(l);historicos.save(new LeadHistorico(l,"ENDERECO_ATUALIZADO","Endereco atualizado.",autor));return detalhe(id);}
 /** Reconciliation happens at startup or by an explicit admin command, not during GET. */
 @Transactional public Map<String,Integer> sincronizarMarmiterias(){int importadas=0;for(Marmiteria m:marmiterias.findAll()){if(leads.findByMarmiteriaId(m.getId()).isPresent())continue;List<LeadComercial> candidatos=m.getEmail()==null?List.of():leads.findByEmailIgnoreCase(m.getEmail());LeadComercial l=candidatos.size()==1&&candidatos.get(0).getMarmiteria()==null?candidatos.get(0):new LeadComercial();boolean novo=l.getId()==null;l.setMarmiteria(m);if(novo){l.setNomeLoja(Objects.toString(m.getNome(),""));l.setEmail(Objects.toString(m.getEmail(),""));l.setCnpj(m.getCnpj());l.setTelefone(m.getTelefone());l.setStatusComercial(StatusComercial.CADASTRO_EXISTENTE);l.setStatusPagamento(StatusPagamento.NAO_INFORMADO);}l.setStatusAcesso(m.getStatusAcesso()==null?StatusAcesso.ATIVO:m.getStatusAcesso());l.setCep(m.getCep());l.setNumero(m.getNumero());l.setRua(m.getRua());l.setBairro(m.getBairro());l.setCidade(m.getCidade());l.setUf(m.getUf());leads.save(l);historicos.save(new LeadHistorico(l,"CADASTRO_EXISTENTE","Conta existente vinculada ao painel; pagamento nao presumido e senha preservada.",null));importadas++;}return Map.of("importadas",importadas);}
 private void aplicarAcesso(LeadComercial l,StatusAcesso s){l.setStatusAcesso(s);if(l.getMarmiteria()!=null){l.getMarmiteria().setStatusAcesso(s);marmiterias.save(l.getMarmiteria());}}
 private void registrarHistorico(LeadComercial l,String tipo,String valor,String obs,String autor){historicos.save(new LeadHistorico(l,tipo,valor+(obs==null||obs.isBlank()?"":" - "+obs.trim()),autor));}
 private List<HistoricoResponse> historico(Long id){return historicos.findByLeadIdOrderByCreatedAtDesc(id).stream().map(HistoricoResponse::of).toList();}
 private LeadComercial buscar(Long id){return leads.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Empresa nao encontrada."));}
 private static String formatTelefone(String value){String digits=value.replaceAll("\\D","");int split=digits.length()==11?7:6;return "("+digits.substring(0,2)+") "+digits.substring(2,split)+"-"+digits.substring(split);}
 private static String blankToNull(String s){return s==null||s.isBlank()?null:s.trim();}
 private static <E extends Enum<E>> E parse(Class<E> c,String v){return v==null||v.isBlank()?null:parseObrigatorio(c,v);}
 private static <E extends Enum<E>> E parseObrigatorio(Class<E> c,String v){try{return Enum.valueOf(c,v.trim().toUpperCase());}catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status invalido.");}}
}
