package com.example.FullAPIdemo.controller;
import com.example.FullAPIdemo.model.dto.web.*;
import com.example.FullAPIdemo.security.CurrentUser;
import com.example.FullAPIdemo.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/** All routes require ROLE_ADMIN in SecurityConfig. */
@RestController @RequestMapping("/api/admin")
public class AdminController {
 private final LeadService leads;
 public AdminController(LeadService leads){this.leads=leads;}
 @GetMapping("/dashboard") public DashboardResponse dashboard(){return leads.dashboard();}
 @GetMapping("/empresas") public PageResponse<EmpresaResponse> listar(@RequestParam(required=false) String busca,@RequestParam(required=false) String statusComercial,@RequestParam(required=false) String statusPagamento,@RequestParam(required=false) String statusAcesso,@RequestParam(defaultValue="0") int pagina,@RequestParam(defaultValue="20") int tamanho){return leads.listar(busca,statusComercial,statusPagamento,statusAcesso,pagina,tamanho);}
 @GetMapping("/empresas/{id}") public EmpresaResponse detalhe(@PathVariable Long id){return leads.detalhe(id);}
 @PatchMapping("/empresas/{id}/status-comercial") public EmpresaResponse comercial(@PathVariable Long id,@RequestBody @Valid AtualizarStatusRequest r){return leads.atualizarComercial(id,r,CurrentUser.get().email());}
 @PatchMapping("/empresas/{id}/pagamento") public EmpresaResponse pagamento(@PathVariable Long id,@RequestBody @Valid AtualizarStatusRequest r){return leads.atualizarPagamento(id,r,CurrentUser.get().email());}
 @PatchMapping("/empresas/{id}/acesso") public LiberacaoResponse acesso(@PathVariable Long id,@RequestBody @Valid AtualizarStatusRequest r){return leads.atualizarAcesso(id,r,CurrentUser.get().email());}
 @PostMapping("/empresas/sincronizar") public java.util.Map<String,Integer> sincronizar(){return leads.sincronizarMarmiterias();}
 @PostMapping("/empresas/{id}/redefinir-senha") public org.springframework.http.ResponseEntity<LiberacaoResponse> senha(@PathVariable Long id){return org.springframework.http.ResponseEntity.ok().cacheControl(org.springframework.http.CacheControl.noStore()).body(leads.redefinirSenha(id,CurrentUser.get().email()));}
 @PatchMapping("/empresas/{id}/endereco") public EmpresaResponse endereco(@PathVariable Long id,@RequestBody @Valid EnderecoRequest r){return leads.atualizarEndereco(id,r,CurrentUser.get().email());}
 @PostMapping("/empresas/{id}/historico") public EmpresaResponse historico(@PathVariable Long id,@RequestBody @Valid HistoricoRequest r){return leads.adicionarHistorico(id,r,CurrentUser.get().email());}
}
