package com.example.FullAPIdemo.service;

import com.example.FullAPIdemo.model.StatusComercial;
import com.example.FullAPIdemo.model.StatusPagamento;
import com.example.FullAPIdemo.model.dto.web.AtualizarStatusRequest;
import com.example.FullAPIdemo.model.dto.web.EmpresaResponse;
import com.example.FullAPIdemo.model.dto.web.LeadRequest;
import com.example.FullAPIdemo.model.entity.LeadComercial;
import com.example.FullAPIdemo.model.entity.Marmiteria;
import com.example.FullAPIdemo.repository.*;
import com.example.FullAPIdemo.security.PasswordService;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LeadServiceTest {
    private final LeadComercialRepository leads = mock(LeadComercialRepository.class);
    private final LeadHistoricoRepository historicos = mock(LeadHistoricoRepository.class);
    private final MarmiteriaRepository marmiterias = mock(MarmiteriaRepository.class);
    private final PasswordService passwords = mock(PasswordService.class);
    private final EmailService emails = mock(EmailService.class);
    private final LeadService service = new LeadService(leads, historicos, marmiterias, passwords, emails, "test@example.com");

    private LeadRequest request(String telefone, String cep) {
        return new LeadRequest("Restaurante Teste", "", "teste@example.com", telefone, "Responsavel", cep, "123", "");
    }

    @Test void registrationStoresCepNumberAndFormattedTelephone() throws Exception {
        when(leads.findFirstByEmailIgnoreCaseOrderByCreatedAtDesc(anyString())).thenReturn(Optional.empty());
        when(leads.save(any())).thenAnswer(inv -> { LeadComercial l = inv.getArgument(0); Field id = LeadComercial.class.getDeclaredField("id"); id.setAccessible(true); id.set(l, 1L); return l; });
        service.registrar(request("11987654321", "01310-100"));
        ArgumentCaptor<LeadComercial> captor = ArgumentCaptor.forClass(LeadComercial.class);
        verify(leads).save(captor.capture());
        assertEquals("(11) 98765-4321", captor.getValue().getTelefone());
        assertEquals("01310100", captor.getValue().getCep());
        assertEquals("123", captor.getValue().getNumero());
    }

    @Test void liberationCopiesCepAndNumberToMarmiteria() {
        LeadComercial lead = new LeadComercial();
        lead.setEmail("teste@example.com"); lead.setNomeLoja("Restaurante Teste");
        lead.setCep("01310100"); lead.setNumero("123");
        lead.setStatusComercial(StatusComercial.VENDIDO); lead.setStatusPagamento(StatusPagamento.CONFIRMADO);
        when(leads.findById(1L)).thenReturn(Optional.of(lead));
        when(marmiterias.findFirstByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(passwords.hash(anyString())).thenReturn("hashed");
        when(historicos.findByLeadIdOrderByCreatedAtDesc(1L)).thenReturn(List.of());
        service.atualizarAcesso(1L, new AtualizarStatusRequest("ATIVO", null), "admin@example.com");
        assertEquals("01310100", lead.getMarmiteria().getCep());
        assertEquals("123", lead.getMarmiteria().getNumero());
        assertEquals("01310100", EmpresaResponse.of(lead, List.of()).cep());
        assertEquals("123", EmpresaResponse.of(lead, List.of()).numero());
    }

    @Test void validatesCepAndTelephoneWithDdd() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertTrue(validator.validate(request("(11) 98765-4321", "01310-100")).isEmpty());
            assertFalse(validator.validate(request("987654321", "01310100")).isEmpty());
            assertFalse(validator.validate(request("11987654321", "01310")).isEmpty());
        }
    }

    @Test void commercialResponseDoesNotExposeEstablishmentType() {
        assertTrue(java.util.Arrays.stream(EmpresaResponse.class.getRecordComponents()).noneMatch(c -> c.getName().equals("tipoEstabelecimento")));
    }
}
