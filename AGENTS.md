# API architecture
- Keep commercial request/response DTOs explicit and map address fields to the existing Marmiteria entity when provisioning accounts, preventing mass assignment and lost registration data.
- Apply sequential MySQL scripts under src/main/resources/db manually before startup because JPA schema generation is disabled; preserve historical address columns when changing the public contract.
- Keep Web authentication in Spring Security and never expose password fields through commercial responses.
