# API architecture
- Keep commercial request/response DTOs explicit and map address fields to the existing Marmiteria entity when provisioning accounts, preventing mass assignment and lost registration data.
- Apply sequential MySQL scripts under src/main/resources/db manually before startup because JPA schema generation is disabled; preserve historical address columns when changing the public contract.
- Keep Web authentication in Spring Security and never expose password fields through commercial responses.
- Reconcile existing Marmiteria accounts into commercial dashboard records at startup and via an explicit admin action, preserving credential/access state and marking unknown commercial/payment history without inventing a sale.
- Reset passwords only through the ADMIN-protected endpoint, persist BCrypt in the shared Marmiteria account and return the temporary password once with no-store; audit events never contain credentials.
- Address updates accept only explicit address DTO fields and company updates derive account identity from the authenticated principal.
